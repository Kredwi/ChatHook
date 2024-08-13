package ru.kredwi.chathook;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ru.kredwi.chathook.commands.MainCommand;
import ru.kredwi.chathook.events.ChatPlayerEvent;

public class Main extends JavaPlugin {
	
	private FileConfiguration config;
	
	@Override
	public void onLoad() {
		try {
			File configFile = new File(getDataFolder(), "config.yml");
			if (!configFile.exists()) {
				// create plugin dirs (server/plugins/chathook)
				configFile.getParentFile().mkdirs();
				// save config file in file
				saveResource("config.yml", false);
			}
			config = YamlConfiguration.loadConfiguration(configFile);
			config.save(configFile);

			// check if url is empty returned Exception IllegalArgumentException
			this.checkValidURL(config.getString("request_url"));
			
		} catch(MalformedURLException e) {
			Bukkit.getLogger().severe(String.format("URL IS ERROR: %s", e.getMessage()));
			if (config.getBoolean("debug")) { // if debug is run
				e.printStackTrace(); // print other error info
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			getLogger().severe(e.getMessage());
			if (config.getBoolean("debug")) { // if debug is run
				e.printStackTrace(); // print other error info
			}
		}
	}
	@Override
	public void onEnable() {
		
		new CheckDepends(this).runCheckDepends(); // check all depends
		
		PluginCommand chatHook = getCommand("chathook");
		MainCommand mainCommand = new MainCommand(this);
		
		chatHook.setExecutor(mainCommand);
		chatHook.setTabCompleter(mainCommand);
		
		// register chat event
		getServer().getPluginManager().registerEvents(new ChatPlayerEvent(this), this);
	}
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this); // disable all plugin threads
	}
	private URL checkValidURL(String url) throws MalformedURLException {
		if (url == null || url.isEmpty()) {
			// return error IllegalArgumentException
			throw new IllegalArgumentException(String.format("REQUEST URL %s IS NULL OR EMPTY", url));
		}
		return new URL(url); // or return formated url
	}
}
