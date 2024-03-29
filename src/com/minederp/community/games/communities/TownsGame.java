package com.minederp.community.games.communities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.minederp.community.CommunityPlugin;
import com.minederp.community.games.BlockReprintItem;
import com.minederp.community.games.Game;
import com.minederp.community.games.GameLogic;
import com.minederp.community.games.communities.content.TownContent;
import com.minederp.community.orm.Town;
import com.minederp.community.orm.TownPlayer;
import com.minederp.community.util.Helper;
import com.minederp.community.util.Polygon;
import com.minederp.community.util.PolygonPoint;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.worldedit.blocks.BlockType;

public class TownsGame extends Game {
	private final CommunityPlugin kingdomsPlugin;

	private List<Player> playersInWilderness;

	private GameLogic logic;
	private List<TownContent> towns;

	public TownsGame(CommunityPlugin kingdomsPlugin) {
		this.kingdomsPlugin = kingdomsPlugin;

	}

	@Override
	public boolean canPlayerJoin(Player player) {
		return true;
	}

	@Override
	public void onLoad(GameLogic logic) {
		this.logic = logic;

		playersInWilderness = new ArrayList<Player>();
		towns = new ArrayList<TownContent>();
		for (Town town : Town.getAll()) {
			towns.add(new TownContent(town, kingdomsPlugin, logic));
		}

	}

	public TownContent getTown(Town t) {
		for (TownContent town : towns) {
			if (town.myTown.equals(t))
				return town;
		}
		return null;
	}

	@Override
	public void updatePlayerGamePosition(Player movingPlayer, Location to) {

		for (TownContent town : towns) {
			if (town.playerMove(movingPlayer, to)) {
				playersInWilderness.remove(movingPlayer);
				return;
			}
		}

		if (Helper.containsPlayers(playersInWilderness, movingPlayer)) {
			return;
		}

		movingPlayer.sendMessage(ChatColor.AQUA + " You have entered wilderness...");
		playersInWilderness.add(movingPlayer);

	}

	@Override
	public void processCommand(String header, CommandContext args, Player player) {

		if (header.equals("t")) {
			if (args.argsLength() > 0 && Helper.argEquals(args.getString(0), "CreateTown")) {
				// deduct money
				TownPlayer kp = TownPlayer.getFirstByPlayerName(player.getName());
		 
				TownContent tc = new TownContent(args.getString(1), kp,  kingdomsPlugin, logic);
				towns.add(tc);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Town " + tc.myTown.getTownName() + " has been created.");
				return;
			}

			for (TownContent town : towns) {
				if (town.playerCommand(args, player)) {
					return;
				}
			}

			if (args.argsLength() == 0 || args.getString(0).toLowerCase().equals("help")) {
				StringBuilder sb = new StringBuilder();
				sb.append(ChatColor.LIGHT_PURPLE + "not in a town help");

				player.sendMessage(sb.toString());
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(ChatColor.RED + " Not a command");
			player.sendMessage(sb.toString());
		} else if (header.equals("p")) {

			TownContent tc = null;

			for (TownContent town : towns) {
				if (town.townPlayers.contains(player)) {
					tc = town;
					break;
				}
			}
			if (tc != null) {
				if (tc.plotCommand(args, player)) {
					return;
				}

			}

			if (args.argsLength() == 0 || args.getString(0).toLowerCase().equals("help")) {
				StringBuilder sb = new StringBuilder();
				sb.append(ChatColor.LIGHT_PURPLE + "not in a town plot help");
				player.sendMessage(sb.toString());
				return;

			}
			StringBuilder sb = new StringBuilder();
			sb.append(ChatColor.RED + " Not a command");
			player.sendMessage(sb.toString());
		}

	}

	@Override
	public boolean blockDestroyed(Block block, Player clickedPlayer) {
		return false;
	}

	@Override
	public void playerQuit(Player player) {
		for (TownContent town : towns) {
			town.playerQuit(player);
		}
	}

	@Override
	public void playerJoin(Player player) {
		for (TownContent town : towns) {
			town.playerJoin(player);
		}
	}

	@Override
	public boolean blockClick(BlockFace face, Block clickedBlock, Player player) {

		for (TownContent town : towns) {
			if (town.blockClick(face, clickedBlock, player)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean blockPlaced(BlockFace face, Block clickedBlock, Player player) {
		for (TownContent town : towns) {
			if (town.blockPlaced(face, clickedBlock, player)) {
				return true;
			}

		}

		return false;
	}

	@Override
	public void playerDied(final Player player) {

	}

	@Override
	public void playerRespawn(final Player player) {

	}

	@Override
	public boolean playerFight(Player damagee, Player damager) {
		return true;

	}

	@Override
	public void playerDying(Player entity) {

	}

	@Override
	public void entityDied(Entity entity, EntityDeathEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityHurt(Entity entity, EntityDamageEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void joinGame(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveGame(Player player) {
		// TODO Auto-generated method stub

	}

}