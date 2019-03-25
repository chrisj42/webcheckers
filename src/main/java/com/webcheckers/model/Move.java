package com.webcheckers.model;

import java.util.Objects;

public class Move {
	
	private final Position start;
	private final Position end;
	
	public Move(Position start, Position end) {
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
		return Objects.equals(start, move.start) &&
			Objects.equals(end, move.end);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}
}
