package ru.kredwi.chathook.post.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.gson.Gson;

import ru.kredwi.chathook.post.model.RequestData;

public class SendRequest {
	
	private Plugin plugin;
	private Gson gson = new Gson();
	
	private static final String REQUEST_SEND_MESSAGE = "STARTING SENDING A REQUEST TO THE SERVER (%s)";
	
	public SendRequest(Plugin plugin) {
		this.plugin = plugin;
	}
	public void send(Player player, RequestData data) {
		boolean debug = plugin.getConfig().getBoolean("debug");
		RequestTask request = new RequestTask(plugin, player, gson, data);
		if (plugin.getConfig().getBoolean("async")) {
			if (debug) Bukkit.getLogger().info(String.format(REQUEST_SEND_MESSAGE, "ASYNC"));
			Bukkit.getScheduler().runTaskAsynchronously(plugin, request);
		} else {
			if (debug) Bukkit.getLogger().info(String.format(REQUEST_SEND_MESSAGE, "SYNC"));
			request.run();
		}
	}
}
