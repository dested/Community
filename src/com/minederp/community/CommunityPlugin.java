// $Id$
/*
 * Kingdoms
 * Copyright (C) 2011 dested <>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.

 */
package com.minederp.community;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.minederp.community.commands.*;
import com.minederp.community.games.GameLogic;
import com.minederp.community.games.advancedWar.AdvancedWarGame;
import com.minederp.community.games.communities.TownsGame;
import com.minederp.community.games.construction.ConstructionGame;
import com.minederp.community.games.ctf.CaptureTheFlagGame;
import com.minederp.community.games.zombies.ZombiesGame;
import com.minederp.community.listeners.KingdomsBlockListener;
import com.minederp.community.listeners.KingdomsEntityListener;
import com.minederp.community.listeners.KingdomsPlayerListener;
import com.minederp.community.listeners.KingdomsVehicleListener;
import com.minederp.community.util.InventoryStasher;

import com.minederp.mysql.mysqlWrapper;
import com.sk89q.bukkit.migration.PermissionsResolverManager;
import com.sk89q.bukkit.migration.PermissionsResolverServerListener;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

/**
 * Base plugin class for Kingdoms.
 * 
 * @author dested
 */
public class CommunityPlugin extends JavaPlugin {

	protected static final Logger logger = Logger.getLogger("Minecraft.Community");

	public GameLogic gameLogic = new GameLogic();

	private PermissionsResolverManager perms;
	protected CommandsManager<CommandSender> commands;

	public static mysqlWrapper wrapper = new mysqlWrapper();

	public InventoryStasher inventoryStasher = new InventoryStasher(this);

	private Listener blockListener = new KingdomsBlockListener(this);
	private Listener entityListener = new KingdomsEntityListener(this);
	private Listener playerListener = new KingdomsPlayerListener(this);
//	private Listener inventoryListener = new KingdomsInventoryListener(this);
//	private Listener mapListener = new KingdomsMapListener(this);
	private Listener vehicleListener = new KingdomsVehicleListener(this);

	public TownsGame townGame;

	/**
	 * Called when the plugin is enabled. This is where configuration is loaded,
	 * and the plugin is setup.
	 */
	public void onEnable() {

		logger.info(getDescription().getName() + " " + getDescription().getVersion() + " enabled.");

		// Make the data folder for the plugin where configuration files
		// and other data files will be stored
		getDataFolder().mkdirs();

		createDefaultConfiguration("config.yml");

		// Load configuration
		populateConfiguration();

		try {
			wrapper.connectDatabase();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Mysql cannot be connected");
			e.printStackTrace();
		}

		// Prepare permissions
		perms = new PermissionsResolverManager(getConfiguration(), getServer(), getDescription().getName(), logger);
		perms.load();

		// Register the commands that we want to use
		final CommunityPlugin plugin = this;
		commands = new CommandsManager<CommandSender>() {
			@Override
			public boolean hasPermission(CommandSender player, String perm) {
				return plugin.hasPermission(player, perm);
			}
		};
		commands.register(CTFCommands.class);
		commands.register(ZombiesCommands.class);
		commands.register(TownCommands.class);
		commands.register(PlotCommands.class);
		commands.register(ConstructionCommands.class);
		commands.register(AdvancedWarCommands.class);

		// commands.register(GeneralCommands.class);

		// Register events
		registerEvents();

		gameLogic.addGame(new CaptureTheFlagGame(this));
		gameLogic.addGame(new ZombiesGame(this));
		gameLogic.addGame(townGame = new TownsGame(this)); 

		// gameLogic.addGame(new VehicleGame(this));
		for (org.bukkit.World w : getServer().getWorlds()) {
			gameLogic.addGame(new AdvancedWarGame(this,w));
			gameLogic.addGame(new ConstructionGame(this, w));
		}

		// The permissions resolver has some hooks of its own
		(new PermissionsResolverServerListener(perms)).register(this);
	}

	/**
	 * Register the events that are used.
	 */
	protected void registerEvents() {
		registerEvent(Event.Type.BLOCK_CANBUILD, blockListener);
		registerEvent(Event.Type.BLOCK_BREAK, blockListener);
		registerEvent(Event.Type.REDSTONE_CHANGE, blockListener);

		registerEvent(Event.Type.ENTITY_DEATH, entityListener);
		registerEvent(Event.Type.ENTITY_DAMAGE, entityListener);

		registerEvent(Event.Type.PLAYER_PRELOGIN, playerListener);

		registerEvent(Event.Type.PLAYER_MOVE, playerListener);
		registerEvent(Event.Type.PLAYER_INTERACT, playerListener);
		registerEvent(Event.Type.PLAYER_JOIN, playerListener);
		registerEvent(Event.Type.PLAYER_QUIT, playerListener);
		registerEvent(Event.Type.PLAYER_RESPAWN, playerListener);

		registerEvent(Event.Type.VEHICLE_EXIT, vehicleListener);
		registerEvent(Event.Type.VEHICLE_ENTER, vehicleListener);
		registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener);

		//registerEvent(Event.Type.INVENTORY_TRANSACTION, inventoryListener);
		//registerEvent(Event.Type.INVENTORY_OPEN, inventoryListener);

		registerEvent(Event.Type.CHUNK_LOAD, new WorldListener() {
			public void onChunkLoad(ChunkLoadEvent event) {

			}
		});

		// registerEvent(Event.Type.MAP_INITIALIZE, mapListener);
	}

	/**
	 * Called when the plugin is disabled. Shutdown and clearing of any
	 * temporary data occurs here.
	 */
	public void onDisable() {

		wrapper.close();
	}

	/**
	 * Called on a command. Boilerplate from sk89q Should not be modified or
	 * understood.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			commands.execute(cmd.getName(), args, sender, this, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}

		return true;
	}

	/**
	 * Register an event.
	 * 
	 * @param type
	 * @param listener
	 * @param priority
	 */
	protected void registerEvent(Event.Type type, Listener listener, Priority priority) {
		getServer().getPluginManager().registerEvent(type, listener, priority, this);
	}

	/**
	 * Register an event at normal priority.
	 * 
	 * @param type
	 * @param listener
	 */
	protected void registerEvent(Event.Type type, Listener listener) {
		getServer().getPluginManager().registerEvent(type, listener, Priority.Normal, this);
	}

	/**
	 * Loads the configuration.
	 */
	public void populateConfiguration() {
		Configuration config = getConfiguration();
		config.load();
	}

	/**
	 * Create a default configuration file from the .jar.
	 * 
	 * Boiler Plate
	 * 
	 * @param name
	 */
	protected void createDefaultConfiguration(String name) {
		File actual = new File(getDataFolder(), name);
		if (!actual.exists()) {

			InputStream input = this.getClass().getResourceAsStream("/defaults/" + name);
			if (input != null) {
				FileOutputStream output = null;

				try {
					output = new FileOutputStream(actual);
					byte[] buf = new byte[8192];
					int length = 0;
					while ((length = input.read(buf)) > 0) {
						output.write(buf, 0, length);
					}

					logger.info(getDescription().getName() + ": Default configuration file written: " + name);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (input != null)
							input.close();
					} catch (IOException e) {
					}

					try {
						if (output != null)
							output.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	/**
	 * Checks permissions.
	 * 
	 * @param sender
	 * @param perm
	 * @return
	 */
	public boolean hasPermission(CommandSender sender, String perm) {
		if (!(sender instanceof Player)) {
			return true;
		}

		if (sender.isOp()) {
			return true;
		}

		// Invoke the permissions resolver
		if (sender instanceof Player) {
			return perms.hasPermission(((Player) sender).getName(), perm);
		}

		return false;
	}

	/**
	 * Checks permissions and throws an exception if permission is not met.
	 * 
	 * @param sender
	 * @param perm
	 * @throws CommandPermissionsException
	 */
	public void checkPermission(CommandSender sender, String perm) throws CommandPermissionsException {
		if (!hasPermission(sender, perm)) {
			throw new CommandPermissionsException();
		}
	}

}
