package com.webcheckers.ui;

import com.webcheckers.model.Piece;

public class Space {
	
	private int row;
	private int col;
	private Piece piece;
	
	Space(int row, int column, Piece piece) {
		this.row = row;
		this.col = column;
		// 0,0 = top left = white
		// this.piece = isValid() && (row < 2 || row >= BoardView.SIZE-2) ? new Piece(Type.SINGLE, Color.RED) : null;
		this.piece = piece;
	}
	
	public int getCellIdx() { return col; }
	
	public boolean isValid() { return (row+col) % 2 == 1 && piece == null; }
	
	public Piece getPiece() {
		return piece;
	}
}
