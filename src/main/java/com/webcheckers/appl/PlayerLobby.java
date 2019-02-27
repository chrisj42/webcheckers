package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Set;

import com.webcheckers.model.Player;

public class PlayerLobby {
	
	private HashMap<String, Player> players = new HashMap<>();
	
	public PlayerLobby() {
		
	}
	
	public Player tryLoginPlayer(String username) {
		if(players.containsKey(username))
			return null;
		Player p = new Player(username);
		players.put(username, p);
		return p;
	}
	
	public void logoutPlayer(String username) {
		players.remove(username);
	}
	
}
