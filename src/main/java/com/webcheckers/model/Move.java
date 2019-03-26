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
