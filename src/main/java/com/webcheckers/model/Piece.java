package com.webcheckers.model;

import static com.webcheckers.model.Type.*;

public class Piece {
	
	private Type type;
	private Color color;
	
	public Piece(Type type, Color color) {
		this.type = type;
		this.color = color;
	}
	
	public Type getType() {
		return type;
	}
	
	public Color getColor() {
		return color;
	}

	public void promotion() { this.type = KING; }
}
