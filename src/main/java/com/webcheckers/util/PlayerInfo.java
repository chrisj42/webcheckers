package com.webcheckers.util;

public class PlayerInfo {
	
	private String name;
	private String status;
	
	public PlayerInfo(String name, String status) {
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStatus() {
		return status;
	}
}
