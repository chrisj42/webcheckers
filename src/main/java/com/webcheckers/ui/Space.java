package com.webcheckers.ui;

import com.webcheckers.util.Color;
import com.webcheckers.util.Piece;
import com.webcheckers.util.Type;

public class Space {
	
	private int idx;
	private Piece piece;
	
	Space(int idx) {
		this.idx = idx;
		this.piece = (idx + BoardView.SIZE / 2) % 2 == 0 ? null : new Piece(Type.KING, Color.RED);
	}
	
	public int getCellIdx() { return idx; }
	
	public boolean isValid() { return true; }
	
	public Piece getPiece() {
		return piece;
	}
}
