package ru.kredwi.chathook;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CheckDepends {
	
	private String[] dependsList = new String[] { "PlaceholderAPI" };
	private PluginManager pluginManager = Bukkit.getPluginManager();
	private Logger logger = Bukkit.getLogger();
	private Plugin plugin = null;
	
	protected CheckDepends(Plugin plugin) {
		this.plugin = plugin;
	}
	
	private boolean check(String pluginName) {
		return pluginManager.getPlugin(pluginName) != null;
	}
	protected void runCheckDepends() {
		for (String pluginName : dependsList) {
			// checking is plugin is enable
			if (this.check(pluginName)) {
				// execute succes method
				this.dependSucces(pluginName);
			} else {
				// execute error method (print error and disable plugin)
				this.dependError(pluginName);
				return;
			}
		}
	}
	private void dependError(String pluginName) {
		logger.warning(String.format("Could not find %s! This plugin is required.", pluginName));
		pluginManager.disablePlugin(plugin); // disable plugin
	}
	private void dependSucces(String pluginName) {
		logger.info(String.format("%s is found", pluginName));
	}
}
