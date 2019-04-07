package com.webcheckers.ui.board;

import java.util.ArrayList;
import java.util.Iterator;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;

public class BoardView implements Iterable<Row> {
	
	private final ArrayList<Row> list = new ArrayList<>(CheckersGame.BOARD_SIZE);
	
	public BoardView(Piece[][] board, boolean isPlayer1) {
		for(int i = 0; i < CheckersGame.BOARD_SIZE; i++) {
			int idx = isPlayer1 ? i : board.length - i - 1;
			list.add(new Row(idx, board[idx], isPlayer1));
		}
	}
	
	@Override
	public Iterator<Row> iterator() {
		return list.iterator();
	}
}
