package com.minederp.community.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minederp.community.CommunityPlugin;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class ConstructionCommands {
	@Command(aliases = { "construction", "c" }, usage = "", desc = "Construction commands", min = 0, max = 4)
	public static void town(CommandContext args, CommunityPlugin plugin, CommandSender sender) throws CommandException {

		plugin.gameLogic.processCommand("c", args, ((Player) sender));
	}

}