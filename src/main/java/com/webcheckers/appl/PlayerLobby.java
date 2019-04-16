package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

import com.webcheckers.model.game.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.game.AbstractGame;
import com.webcheckers.model.game.GameReplay;
import com.webcheckers.util.PlayerInfo;
import com.webcheckers.util.ViewMode;

public class PlayerLobby {
	
	public static final String NO_ERROR = null;
	
	private final HashMap<String, Player> players = new HashMap<>();
	private final HashMap<String, AbstractGame> playerGames = new HashMap<>();
	
	private final ReplayArchive archive;
	
	public PlayerLobby(ReplayArchive archive) {
		Objects.requireNonNull(archive, "ReplayArchive cannot be null");
		this.archive = archive;
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
	public String tryStartGame(Player player, String opponent) {
		Player o = players.get(opponent);
		if(o == null)
			return "Opponent not found.";
		
		if(hasGame(player))
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
				playerGames.put(player.getName(), existing);
				return NO_ERROR;
			}
		}
		
		// opponent is not in a game, create new one
		CheckersGame game = new CheckersGame(player, o);
		playerGames.put(player.getName(), game);
		playerGames.put(opponent, game);
		return NO_ERROR;
	}
	
	// same deal as tryStartGame with the return values.
	public String tryStartReplay(Player player, int replayID) {
		AbstractGame curGame = getCurrentGame(player);
		
		if(curGame != null) {
			if(curGame.getViewMode(player) != ViewMode.REPLAY) // happens if a game is started with the player and then the player clicks a replay before the page refreshes.
				return "Resign your current game to start a replay."; // should end up redirecting to that existing game, causing this message to never actually be seen.
			else
				return NO_ERROR; // will redirect to current replay.
		}
		
		// no current game, attempt to start replay
		GameReplay replay = archive.createReplay(replayID);
		if(replay == null)
			return "Game "+replayID+" is not a valid, replayable game.";
		
		// replay creation was a success
		playerGames.put(player.getName(), replay);
		return NO_ERROR;
	}
	
	/**
	 * Used to request that a player be de-associated from a game for which they have the specified view mode.
	 * Note that they will not be removed if the view mode does not match.
	 * @param player the player to remove from the game map
	 * @param viewMode the view mode the player must have to successfully be removed
	 */
	public void endGame(Player player, ViewMode viewMode) {
		if(player != null) {
			AbstractGame game = playerGames.get(player.getName());
			if(game != null && game.getViewMode(player) != viewMode)
				return; // view mode must match
			playerGames.remove(player.getName());
		}
	}
}
