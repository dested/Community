package com.minederp.community.games.communities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;

import com.minederp.community.orm.TownPlayer;
public class PlayerCacher {
	public PlayerCacher(TownPlayer kp, Player player) {
		townPlayer = kp;
		this.player = player;
	}

	public TownPlayer townPlayer;
	public Player player;

}