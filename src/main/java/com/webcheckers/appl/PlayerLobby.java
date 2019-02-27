package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Iterator;

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
	
	public boolean hasPlayers() { return players.size() > 1; }
	
	public Iterator<String> getPlayers() { return players.keySet().iterator(); }
	
}
