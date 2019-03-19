package com.webcheckers.ui.board;

import com.webcheckers.model.Piece;

public class Space {
	
	private int row;
	private int col;
	private Piece piece;
	
	Space(int row, int column, Piece piece) {
		this.row = row;
		this.col = column;
		this.piece = piece;
	}
	
	public int getCellIdx() { return col; }
	
	public boolean isValid() { return (row+col) % 2 == 1 && piece == null; }
	
	public Piece getPiece() {
		return piece;
	}
}
