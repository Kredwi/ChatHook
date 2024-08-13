package ru.kredwi.chathook.post.model;

import java.util.UUID;

public class RequestData {
	
	public UUID uuid; // Player UUID
	public String name; // Player player_name
	public String content; // Player message (what this name content? Asking discord)
	public String worldName; // Player world
	
	public RequestData(UUID uuid, String name, String content, String worldName) {
		this.uuid = uuid;
		this.name = name;
		this.content = content;
		this.worldName = worldName;
	}
}
