package ru.kredwi.chathook.post.task;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class RequestUtil {
	
	private URL url = null;
	private int timeout = 5000; // time in ms
	private boolean debug = false;
	private Plugin plugin;
	
	protected RequestUtil(Plugin plugin) {
			this.timeout = plugin.getConfig().getInt("timeout", 5000);
			this.plugin = plugin;
			this.debug = plugin.getConfig().getBoolean("debug");
			this.createURL();
	}
	
	private void createURL() {
		try {
			this.url = new URL(plugin.getConfig().getString("request_url"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Bukkit.getLogger().warning(e.getMessage());
			if (debug) e.printStackTrace(); // if debug mode is enabled print other error info
		}
	}
	
	protected void checkSuccess(int responseCode, String responseMessage) {
		debug = plugin.getConfig().getBoolean("debug"); // set debug mode
		if (
				responseCode != HttpURLConnection.HTTP_OK && // if connect != ok and
				responseCode != HttpURLConnection.HTTP_NO_CONTENT // its error is not "no content"
			) { // execute this code
			Bukkit.getLogger().warning(String.format("Send POST WARN: (%s) %s", responseCode, responseMessage));
			Bukkit.getLogger().info(String.format("If the problem is not with the link, or the closed ports, or the wrong IP, please email me @Kredwi on github or another service"));
		} else {
			if (debug) {
				Bukkit.getLogger().info(String.format("THE POST REQUEST HAS BEEN SUCCESSFULLY SENT TO THE ADDRESS: %S", url.toString()));
			}
		}
	}
	
	protected HttpURLConnection formatConnection() throws ProtocolException, IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST"); // set req method
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(timeout); // set timeout
		connection.setDoOutput(true);
		return connection;
	}
}
