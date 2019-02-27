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
	
	public int getPlayerCount() { return players.size(); }
	
	public Iterator<Player> iterator() { return players.values().iterator(); }
	
	// TODO methods to start a game with a player. Should return a CheckersGame on success, and store the game in a map of username String to CheckersGame instance, for later in GetHomeRoute when checking if a player is already in a game.
}
