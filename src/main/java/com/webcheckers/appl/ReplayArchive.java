package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.Iterator;

import com.webcheckers.model.game.GameReplay;
import com.webcheckers.model.game.GameReplayData;
import com.webcheckers.util.ReplayInfo;

public class ReplayArchive {
	
	private final ArrayList<GameReplayData> finishedGames;
	
	public ReplayArchive() {
		finishedGames = new ArrayList<>();
	}
	
	public void addGame(GameReplayData replay) {
		finishedGames.add(replay);
	}
	
	public int getGameCount() {
		return finishedGames.size();
	}
	
	public Iterator<ReplayInfo> iterator() {
		ArrayList<ReplayInfo> info = new ArrayList<>(finishedGames.size());
		
		for(int i = 0; i < finishedGames.size(); i++) {
			GameReplayData replay = finishedGames.get(i);
			
			String title = replay.getRedPlayer()+" vs. "+replay.getWhitePlayer();
			
			info.add(new ReplayInfo(i, title));
		}
		
		return info.iterator();
	}
	
	public GameReplay createReplay(int replayID) {
		if(replayID < 0 || replayID >= finishedGames.size())
			return null;
		
		return new GameReplay(finishedGames.get(replayID));
	}
}
