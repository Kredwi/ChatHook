package ru.kredwi.chathook.post.model;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import me.clip.placeholderapi.PlaceholderAPI;

public class PostBodyBuilder {
	public String getBody(Plugin plugin, Player player, Gson gson, RequestData data) {
		JsonObject object = new JsonObject();
		
		Map<String, Object> addJSON = plugin.getConfig().getConfigurationSection("other_information").getValues(true); // so big
		
		for (Map.Entry<String, Object> entry : addJSON.entrySet()) {
			// PlaceholdersAPI is required :(
			String value = PlaceholderAPI.setPlaceholders(player, (String) entry.getValue());
			
			JsonElement jsonValue = new JsonPrimitive(value);
			object.add(entry.getKey(), jsonValue);
		}
		JsonObject dataObject = gson.toJsonTree(data).getAsJsonObject();
		dataObject.entrySet().forEach(entry -> object.add(entry.getKey(), entry.getValue()));
		
		return gson.toJson(object);
	}
}
