package com.minederp.community.games.communities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;

import com.minederp.community.orm.TownPlayer;

public class TownPlayerCacher implements Iterable<PlayerCacher> {
	private List<PlayerCacher> players;

	public TownPlayerCacher() {
		players = new ArrayList<PlayerCacher>();
	}

	public void add(TownPlayer kp, Player p) {
		players.add(new PlayerCacher(kp, p));
	}

	public void empty() {
		players.clear();

	}

	public boolean contains(Player player) {

		for (PlayerCacher p : players) {
			if (player.equals(p.player))
				return true;
		}
		return false;
	}	
	public TownPlayer getPlayer(Player player) {

		for (PlayerCacher p : players) {
			if (player.equals(p.player))
				return p.townPlayer;
		}
		return null;
	}

	public boolean contains(String name) {
		name = name.toLowerCase();

		for (PlayerCacher p : players) {
			if (p.townPlayer.getPlayerName().toLowerCase().equals(name))
				return true;
		}
		return false;
	}

	public TownPlayer getPlayer(String name) {
		name = name.toLowerCase();

		for (PlayerCacher p : players) {
			if (p.townPlayer.getPlayerName().toLowerCase().equals(name))
				return p.townPlayer;
		}
		return null;
	}

	@Override
	public Iterator<PlayerCacher> iterator() {
return players.iterator();
	}

}

