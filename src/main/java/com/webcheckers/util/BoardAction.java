package com.webcheckers.util;

import com.webcheckers.model.Piece;

@FunctionalInterface
public interface BoardAction {
	
	void fillBoard(Piece[][] board);
	
}
