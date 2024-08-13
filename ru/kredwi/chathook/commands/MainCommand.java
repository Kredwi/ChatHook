package ru.kredwi.chathook.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class MainCommand implements CommandExecutor, TabCompleter {

	private Plugin plugin = null;
	private ConfigurationSection messages = null;
	
	public MainCommand(Plugin plugin) {
		this.plugin = plugin;
		this.messages = plugin.getConfig().getConfigurationSection("message_sector");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		this.messages = plugin.getConfig().getConfigurationSection("message_sector");
		if (!sender.hasPermission("chathook.use")) {
			sender.sendMessage(messages.getString("no_permissions"));
			return true;
		} else if (args.length < 1) {
			sender.sendMessage(messages.getString("no_arguments"));
			return true;
		}
		switch (args[0].trim().toLowerCase()) {
			case "reload": {
				plugin.reloadConfig();
				sender.sendMessage(messages.getString("config_is_reloaded"));
				return true;
			}
			default: {
				sender.sendMessage(messages.getString("error_invalid_command"));
				return true;
			}
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			list.add("reload");
		}
		return list.stream()
				.filter(s -> s.startsWith(args[args.length - 1]))
				.collect(Collectors.toList());
	}

}
