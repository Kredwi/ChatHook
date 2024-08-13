package ru.kredwi.chathook.post.task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.gson.Gson;

import ru.kredwi.chathook.post.model.PostBodyBuilder;
import ru.kredwi.chathook.post.model.RequestData;

public class RequestTask implements Runnable {
	
	private Plugin plugin = null;
	private Player player = null;
	private Gson gson = null;
	private boolean debug = false;
	private RequestData data = null;
	private PostBodyBuilder dataBody = new PostBodyBuilder();
	private RequestUtil requestUtil = null;
	
	public RequestTask(Plugin plugin, Player player, Gson gson, RequestData data) {
		this.plugin = plugin;
		this.player = player;
		this.gson = gson;
		this.data = data;
		this.requestUtil = new RequestUtil(plugin);
	}
	
	@Override
	public void run() {
		try {
			debug = plugin.getConfig().getBoolean("debug"); // set debug mode
			String AuthToken = plugin.getConfig().getString("authorization"); // get auth token
			String body = dataBody.getBody(plugin, player, gson, data); // get json body
			byte[] input = body.getBytes(); // get string bytes
			
			HttpURLConnection connection = requestUtil.formatConnection(); // get formated connect
			
			connection.setRequestProperty("Content-Length", String.valueOf(input.length));
			if (!AuthToken.isEmpty()) { // if athorization not empty
				connection.setRequestProperty("Authorization", AuthToken);
			}
			
			try (OutputStream os = connection.getOutputStream()) {
				os.write(input, 0, input.length);
			}
			connection.connect();
			
			requestUtil.checkSuccess(connection.getResponseCode(), connection.getResponseMessage());
			
			connection.disconnect(); // disable connect
		} catch (UnknownHostException e) {
			Bukkit.getLogger().severe(String.format("Unknown Host:", e.getMessage()));
		} catch (ProtocolException e) {
			Bukkit.getLogger().severe(String.format("Unknown protocol:", e.getMessage()));
			Bukkit.getLogger().info("Maybe use http?");
		} catch (IOException e) {
			Bukkit.getLogger().severe(e.getMessage());
			if (debug) {
				e.printStackTrace();
			}
		}
	}
}
