package com.minederp.community.games.communities.content;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.minederp.community.CommunityPlugin;
import com.minederp.community.games.GameLogic;
import com.minederp.community.games.communities.TownPlayerCacher;
import com.minederp.community.orm.Town;
import com.minederp.community.orm.TownPlayer;
import com.minederp.community.orm.TownPlot;
import com.minederp.community.util.Helper;
import com.minederp.community.util.Polygon;
import com.minederp.community.util.PolygonBuilder;
import com.sk89q.minecraft.util.commands.CommandContext;

public class TownContent {
	public Town myTown;
	public PolygonBuilder townPolygon;
	private List<Player> playersInTheArea;
	private final CommunityPlugin plugin;
	private final GameLogic logic;
	public TownPlayerCacher townPlayers;

	private List<TownPlotContent> townPlots;
	private Player settingHeart;
	

	public TownContent(Town t, CommunityPlugin plugin, GameLogic logic) {
		this.plugin = plugin;
		this.logic = logic;
		myTown = t;
		load();
	}

	public TownContent(String name, TownPlayer governor,  CommunityPlugin plugin, GameLogic logic) {
		this.plugin = plugin;
		this.logic = logic;

		Town t = new Town();
		t.setGovernorID(governor.getTownPlayerID());
		t.setTownName(name); 
		t.insert();
		myTown = Town.getFirstByTownName(name);

		addPlayerToTown(governor);
		load();
	}

	private void addPlayerToTown(TownPlayer player) {
		player.setTownID(myTown.getTownID());
		player.update();
	}

	private void loadPlayers() {
		townPlayers.empty();
		for (TownPlayer kp : TownPlayer.getAllByTownID(myTown.getTownID())) {
			townPlayers.add(kp, plugin.getServer().getPlayer(kp.getPlayerName()));
			// getplayer returns null if logged off
		}
	}

	public void playerJoin(Player player) {
		loadPlayers();
	}

	public void playerQuit(Player player) {
		loadPlayers();
	}

	private void load() {
		playersInTheArea = new ArrayList<Player>();

		townPolygon = new PolygonBuilder(Polygon.deserialize(myTown.getTownPolygon()), logic, new PolygonChecker() {
			@Override
			public void save(Polygon polygon) {
				myTown.setTownPolygon(polygon.serialize());
				myTown.update();
			}

			@Override
			public boolean collides(Polygon polygon) {
				// todo: collides with other town
				return false;
			}
		});
		townPlayers = new TownPlayerCacher();
		loadPlayers();

		townPlots = new ArrayList<TownPlotContent>();

		for (TownPlot tp : TownPlot.getAllByTownID(myTown.getTownID())) {
			townPlots.add(new TownPlotContent(this, tp, plugin, logic));
			// getplayer returns null if logged off
		}

	}

	public boolean playerMove(Player movingPlayer, Location to) {

		if (townPolygon.polygon.contains(to.getBlockX(), to.getBlockZ())) {

			for (TownPlotContent tp : townPlots) {
				if (tp.playerMove(movingPlayer, to)) {
					return true;
				}
			}

			if (Helper.containsPlayers(playersInTheArea, movingPlayer)) {
				return true;
			}
			movingPlayer.sendMessage(ChatColor.AQUA + " You have entered the town.");
			playersInTheArea.add(movingPlayer);
			return true;
		}

		playersInTheArea.remove(movingPlayer);
		return false;
	}

	public void save() {
		myTown.update();
	}

	public void buildHeart(Block b) {

		myTown.setTownHeart(b.getLocation().getBlockX() + "," + b.getLocation().getBlockY() + "," + b.getLocation().getBlockZ());
		save();

		b.setType(Material.GOLD_BLOCK);
		buildRect(b = b.getRelative(-1, -1, -1), 3, Material.OBSIDIAN);
		buildRect(b = b.getRelative(-1, -1, -1), 5, Material.WOOD);
		buildRect(b = b.getRelative(-1, -1, -1), 7, Material.DIAMOND_ORE);
		buildRect(b = b.getRelative(-1, -1, -1), 9, Material.WOOL);

	}

	private void buildRect(Block start, int size, Material type) {

		Block b = start;
		Block bWall1 = start;

		for (int y = 0; y < size; y++) {
			for (int z = 0; z < size; z++) {
				b.setType(type);
				b=	b.getRelative(0, 0, 1);
			}
			b.setType(type);
			b = bWall1 = bWall1.getRelative(0, 1, 0);
		}

		b = start;
		bWall1 = start;

		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				b.setType(type);
				b=b.getRelative(1, 0, 0);
			}
			b.setType(type);
			b = bWall1 = bWall1.getRelative(0, 0, 1);
		}
		b = start;
		bWall1 = start;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				b.setType(type);
				b=b.getRelative(0, 1, 0);
			}
			b.setType(type);
			b = bWall1 = bWall1.getRelative(1, 0, 0);
		}

		start = start.getRelative(size, size, size);
		b = start;
		bWall1 = start;

		for (int y = 0; y < size; y++) {
			for (int z = 0; z < size; z++) {
				b.setType(type);
				b=b.getRelative(0, 0, -1);
			}
			b.setType(type);
			b = bWall1 = bWall1.getRelative(0, -1, 0);
		}

		b = start;
		bWall1 = start;

		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				b.setType(type);
				b=b.getRelative(-1, 0, 0);
			}
			b.setType(type);
			b = bWall1 = bWall1.getRelative(0, 0, -1);
		}
		b = start;
		bWall1 = start;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				b.setType(type);
				b=	b.getRelative(0, -1, 0);
			}
			b.setType(type);
			b = bWall1 = bWall1.getRelative(-1, 0, 0);
		}
	}

	public boolean playerCommand(CommandContext args, Player player) {

		if (!townPlayers.contains(player))
			return false;

		if (args.argsLength() == 0 || Helper.argEquals(args.getString(0), "Help")) {
			StringBuilder sb = new StringBuilder();
			sb.append(ChatColor.LIGHT_PURPLE + "Towns");

			player.sendMessage(sb.toString());
			return true;
		}

		if (Helper.argEquals(args.getString(0), "ShowWalls")) {

			if (args.length() == 3) {
				if (Helper.argEquals(args.getString(1), "True")) {
					townPolygon.showWalls(player.getWorld(), true);
				} else if (Helper.argEquals(args.getString(1), "False")) {
					townPolygon.showWalls(player.getWorld(), false);
				}
			} else {
				townPolygon.showWalls(player.getWorld());
			}
			return true;
		}

		if (!myTown.getGovernor().getPlayerName().equals(player.getName())) {
			player.sendMessage(ChatColor.RED + "You do not have the permission  ");
			return true;
		}

		if (Helper.argEquals(args.getString(0), "SetHeart")) {

			player.sendMessage("The next block you click will be the center of the heart.");

			settingHeart = player;

			return true;
		}
		if (Helper.argEquals(args.getString(0), "AddPlayer")) {

			TownPlayer kp = TownPlayer.getFirstByPlayerName(args.getString(1));
			if (kp == null) {
				player.sendMessage("This player does not exist");
				return true;
			}
			if (kp.getTownID() != 0) {
				player.sendMessage("This player already has a town");
				return true;
			}

			kp.setTownID(myTown.getTownID());
			kp.update();
			loadPlayers();
			player.sendMessage("This player has been added to your town.");

			Player pl = plugin.getServer().getPlayer(kp.getPlayerName());
			if (pl != null) {
				pl.sendMessage("You are now part of " + myTown.getTownName());
			}

			return true;
		}

		if (Helper.argEquals(args.getString(0), "AllowBuild")) {

			TownPlayer kp = townPlayers.getPlayer(args.getString(1));

			if (kp != null) {

				if (TownPlot.getFirstByOwnerID(kp.getTownPlayerID()) != null) {
					player.sendMessage("This player already has a plot to build on.");
					return true;
				}

				TownPlot tp = new TownPlot();
				tp.setOwnerID(kp.getTownPlayerID());
				tp.setTownID(kp.getTownID());
				tp.insert();
				tp = TownPlot.getFirstByOwnerID(kp.getTownPlayerID());

				loadPlayers();
				player.sendMessage("You have added this player to the town.");

				Player pl = plugin.getServer().getPlayer(kp.getPlayerName());
				if (pl != null) {
					pl.sendMessage("You can now build a plot.");
				}
			} else {
				player.sendMessage("This player is not part of the town.");
			}

			return true;
		}
		if (Helper.argEquals(args.getString(0), "SetPolygon")) {

			townPolygon.startPolygon(player);
			return true;
		}
		return true;
	}

	public boolean plotCommand(CommandContext args, Player player) {

		boolean isGovernor = false;
		TownPlayer kp = townPlayers.getPlayer(player);
		Point govPoint = null;
		if (myTown.getGovernor().equals(kp)) {
			isGovernor = true;
			govPoint = new Point(player.getLocation().getBlockX(), player.getLocation().getBlockZ());
		}

		for (TownPlotContent tpc : townPlots) {
			if (isGovernor) {
				if (tpc.plotPolygon.polygon.contains(govPoint)) {
					if (tpc.playerCommand(args, player))
						return true;
				}
			}
			if (tpc.myTownPlot.getOwner().getPlayerName().equals(player.getName())) {
				if (tpc.playerCommand(args, player))
					return true;
			}
		}

		if (args.argsLength() == 0 || Helper.argEquals(args.getString(0), "Help")) {
			StringBuilder sb = new StringBuilder();
			sb.append(ChatColor.LIGHT_PURPLE + "Plot");

			player.sendMessage(sb.toString());
			return true;
		}

		return false;
	}

	public boolean blockClick(BlockFace face, Block clickedBlock, Player player) {
		if (townPolygon.blockClick(face, clickedBlock, player))
			return true;

		if (townPolygon.polygon.contains(clickedBlock.getX(), clickedBlock.getZ())) {

			if (settingHeart != null && settingHeart.equals(player)) {
				buildHeart(clickedBlock);
				player.teleport(player.getLocation().getBlock().getRelative(-3, 1, -3).getLocation());
				player.sendMessage("The heart has been set.");
				settingHeart = null;
				return true;
			}

			for (TownPlotContent tp : townPlots) {
				if (tp.blockClick(face, clickedBlock, player)) {
					return true;
				}
			}

			if (!townPlayers.contains(player))
				return true;

			if (myTown.getGovernor().getPlayerName().equals(player.getName())) {
				return false;
			}

			return true;

		}

		return false;
	}

	public boolean blockPlaced(BlockFace face, Block clickedBlock, Player player) {
		if (townPolygon.blockPlaced(face, clickedBlock, player))
			return true;

		if (townPolygon.polygon.contains(clickedBlock.getX(), clickedBlock.getZ())) {

			if (!townPlayers.contains(player))
				return true;

			for (TownPlotContent tp : townPlots) {
				if (tp.blockPlaced(face, clickedBlock, player)) {
					return true;
				}
			}
			if (myTown.getGovernor().getPlayerName().equals(player.getName())) {
				return false;
			}
			return true;
		}

		return false;
	}

	public boolean checkPlotCollision(TownPlotContent me, Polygon polygon) {
		for (TownPlotContent tpc : townPlots) {
			if (!tpc.equals(me) && tpc.plotPolygon.polygon.collides(polygon))
				return true;
		}
		return false;

	}

	public boolean isInTown(Location location) {

		return townPolygon.contains(location);

	}

}
