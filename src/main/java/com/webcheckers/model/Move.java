package com.webcheckers.model;

import java.util.Objects;

public class Move {
	
	private final Position start;
	private final Position end;
	
	public Move(Position start, Position end) {
		Objects.requireNonNull(start, "Move start pos cannot be null");
		Objects.requireNonNull(end, "Move end pos cannot be null");
		this.start = start;
		this.end = end;
	}
	
	public Position getStart() {
		return start;
	}
	
	public Position getEnd() {
		return end;
	}
	
	public boolean isValid() {
		return Math.abs(getRowDelta()) == Math.abs(getColumnDelta())
			&& Math.abs(getRowDelta()) > 0 && Math.abs(getRowDelta()) <= 2;
	}
	
	public boolean isJump() {
		// could use row or column; technically they should always be the same magnitude
		return isValid() && Math.abs(getRowDelta()) == 2;
	}
	
	// if this is a jump move then this returns the position that was jumped.
	public Position getJumpPos() {
		if(!isJump()) return null;
		return new Position(start.getRow() + getRowDelta()/2, start.getCell() + getColumnDelta()/2);
	}
	
	public int getRowDelta() {
		return end.getRow() - start.getRow();
	}
	
	public int getColumnDelta() {
		return end.getCell() - start.getCell();
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Move)) return false;
		Move move = (Move) o;
		return start.equals(move.start) &&
			end.equals(move.end);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}
}
