package com.minederp.community.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minederp.community.CommunityPlugin;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;

public class CTFCommands {
	@Command(aliases = { "ctf", "capture" }, usage = "", desc = "Capture the flag commands", min = 0, max = 3, flags = "")
	public static void ctf(CommandContext args, CommunityPlugin plugin,
			CommandSender sender) throws CommandException {

		plugin.gameLogic.processCommand("ctf",args, ((Player) sender));

	}

}