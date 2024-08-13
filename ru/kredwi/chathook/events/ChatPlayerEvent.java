package ru.kredwi.chathook.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import ru.kredwi.chathook.post.model.RequestData;
import ru.kredwi.chathook.post.task.SendRequest;

public class ChatPlayerEvent implements Listener {
	
	private SendRequest request;
	private List<String> disabledPlayers = null;
	private Plugin plugin = null;
	
	public ChatPlayerEvent(Plugin plugin) {
		this.request = new SendRequest(plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer(); // player simple player
		boolean debug = plugin.getConfig().getBoolean("debug"); // set beluga mode
		// get disable usernames
		this.disabledPlayers = plugin.getConfig().getStringList("disabled_players");
		
		if (!disabledPlayers.contains(player.getName())) {
			request.send(event.getPlayer(), new RequestData(player.getUniqueId(), player.getName(), event.getMessage(), player.getWorld().getName()));	
		} else {
			if (debug) {
				Bukkit.getLogger().info(String.format("%s it's name is disabled in config.yml", player.getName()));
			}
		}
	}
}