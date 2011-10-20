package com.minederp.community.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minederp.community.CommunityPlugin;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class TetrisCommands {
	@Command(aliases = { "tet", "tetris" }, usage = "", desc = "Tetris commands", min = 0, max = 2)
	public static void town(CommandContext args, CommunityPlugin plugin, CommandSender sender) throws CommandException {

		plugin.gameLogic.processCommand("tet", args, ((Player) sender));
	}

}