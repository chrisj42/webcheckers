package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Iterator;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;

public class PlayerLobby {
	
	private HashMap<String, Player> players = new HashMap<>();
	private HashMap<String, AbstractGame> playerGames = new HashMap<>();
	
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
	
	public boolean hasGame(Player p) { return p != null && playerGames.containsKey(p.getName()); }
	
	public AbstractGame getCurrentGame(Player p) { return p == null ? null : playerGames.get(p.getName()); }
	
	public boolean tryStartGame(String player, String opponent) {
		Player p = players.get(player);
		Player o = players.get(opponent);
		if(p == null || o == null)
			return false;
		
		if(hasGame(p))
			return true; // will redirect to game
		if(hasGame(o))
			return false; // already game in progress with another player, since we aren't already in the game
		
		CheckersGame game = new CheckersGame(p, o);
		playerGames.put(player, game);
		playerGames.put(opponent, game);
		return true;
	}
	
	public void endGame(Player player) {
		if(player != null)
			playerGames.remove(player.getName());
	}
}
