package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.util.PlayerInfo;
import com.webcheckers.util.ViewMode;

public class PlayerLobby {
	
	public static final String NO_ERROR = null;
	
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
	
	public Iterator<PlayerInfo> iterator() {
		LinkedList<PlayerInfo> info = new LinkedList<>();
		for(Player p: players.values()) {
			AbstractGame game = getCurrentGame(p);
			String status;
			if(game == null)
				status = "Available";
			else {
				ViewMode viewMode = game.getViewMode(p);
				if(viewMode == ViewMode.PLAY)
					status = "Playing against "+game.getOpponent(p);
				else if(viewMode == ViewMode.SPECTATOR)
					status = "Spectating "+game.getRedPlayer()+" vs "+game.getWhitePlayer();
				// else if(viewMode == ViewMode.REPLAY)
				// 	status = "Replaying "+game.getRedPlayer()+" vs "+game.getWhitePlayer();
				else
					status = "";
			}
			info.add(new PlayerInfo(p.getName(), status));
		}
		
		return info.iterator();
	}
	
	public boolean hasGame(Player p) { return p != null && playerGames.containsKey(p.getName()); }
	
	public AbstractGame getCurrentGame(Player p) { return p == null ? null : playerGames.get(p.getName()); }
	
	// returns non-null only on error; the string in this case holds the error message.
	public String tryStartGame(String player, String opponent) {
		Player p = players.get(player);
		Player o = players.get(opponent);
		if(p == null || o == null)
			return "Player/Opponent not found.";
		
		if(hasGame(p))
			return NO_ERROR; // will redirect to game
		
		AbstractGame existing = getCurrentGame(o);
		if(existing != null) {
			Player opp = existing.getOpponent(o);
			// check if opponent is a spectator, i.e. not part of the game
			if(opp == null)
				return "Opponent is spectating a game.";
			// check if the game is finished; no point in joining that, should wait for opponent to go to home
			// incidentally, this also takes care of replay mode, since all replayable games are finished.
			else if(existing.isGameOver())
				return "Opponent is reviewing a finished game.";
			else {
				// player is part of an unfinished game; spectate the game.
				// technically this just links the player to the game, so if the player is actually part
				// of the game then they'll join in play mode. But a player shouldn't be able to make
				// start game requests when they're part of a game, so it shouldn't matter.
				playerGames.put(player, existing);
				return NO_ERROR;
			}
		}
		
		// opponent is not in a game, create new one
		CheckersGame game = new CheckersGame(p, o);
		playerGames.put(player, game);
		playerGames.put(opponent, game);
		return NO_ERROR;
	}
	
	public void endGame(Player player) {
		if(player != null)
			playerGames.remove(player.getName());
	}
}
