package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Iterator;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;

public class PlayerLobby {
	
	private HashMap<String, Player> players = new HashMap<>();
	private HashMap<String, CheckersGame> currentGame = new HashMap<>();
	
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

	public Player returnPlayer(String p){ return players.get(p); }

	public CheckersGame getGame(Player p){ return currentGame.get(p.getName()); }

	public boolean playerHasGame(Player p){
		if(currentGame.get(p.getName()) != null){
			return true;
		}
		return false;
	}
	
	// TODO methods to start a game with a player. Should return a CheckersGame on success, and store the game in a map of username String to CheckersGame instance, for later in GetHomeRoute when checking if a player is already in a game.
    public boolean startGame(Player player, Player opponent){
		if(currentGame.containsKey(player.getName())){
			return false;
		}
		else if(currentGame.containsKey(opponent.getName())){
			return false;
		}
		else{
			CheckersGame game = new CheckersGame(player, opponent);
			currentGame.put(player.getName(), game);
			currentGame.put(opponent.getName(), game);
			return true;
		}
	}
}
