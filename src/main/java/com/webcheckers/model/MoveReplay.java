package com.webcheckers.model;

import static com.webcheckers.model.game.AbstractGame.setCell;

public class MoveReplay extends Move {
	
	private final Piece startPiece;
	private final Piece endPiece;
	private final Piece jumped;
	
	public MoveReplay(Position start, Position end, Piece original, Piece result, Piece jumped) {
		super(start, end);
		this.jumped = jumped;
		this.startPiece = original;
		this.endPiece = result;
	}
	
	public void applyMove(Piece[][] board) {
		setCell(getStart(), board, null);
		setCell(getEnd(), board, endPiece);
		if(isJump())
			setCell(getJumpPos(), board, null);
	}
	
	public void undoMove(Piece[][] board) {
		setCell(getEnd(), board, null);
		setCell(getStart(), board, startPiece);
		if(isJump())
			setCell(getJumpPos(), board, jumped);
	}
}
