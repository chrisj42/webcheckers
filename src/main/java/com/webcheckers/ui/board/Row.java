package com.webcheckers.ui.board;

import java.util.ArrayList;
import java.util.Iterator;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;

public class Row implements Iterable<Space> {
	
	private int idx;
	private final ArrayList<Space> list = new ArrayList<>(CheckersGame.BOARD_SIZE);
	
	Row(int idx, Piece[] pieces, boolean isPlayer1) {
		this.idx = idx;
		
		for(int i = 0; i < CheckersGame.BOARD_SIZE; i++) {
			int col = isPlayer1?i:pieces.length-i-1;
			list.add(new Space(idx, col, pieces[col]));
		}
	}
	
	public int getIndex() { return idx; }
	
	@Override
	public Iterator<Space> iterator() {
		return list.iterator();
	}
}
