package com.webcheckers.model;

import java.util.Objects;

public class Move {
	
	private final Position start;
	private final Position end;
	private final Player player;
	
	public Move(Player player, Position start, Position end) {
		Objects.requireNonNull(player, "Move player cannot be null");
		Objects.requireNonNull(start, "Move start pos cannot be null");
		Objects.requireNonNull(end, "Move end pos cannot be null");
		this.player = player;
		this.start = start;
		this.end = end;
	}
	
	public Position getStart() {
		return start;
	}
	
	public Position getEnd() {
		return end;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Move)) return false;
		Move move = (Move) o;
		return start.equals(move.start) &&
			end.equals(move.end) &&
			player.equals(move.player);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(start, end, player);
	}
}
