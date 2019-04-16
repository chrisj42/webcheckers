package com.webcheckers.util;

import com.webcheckers.model.Color;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Type;

public enum TestMode {
    
    MULTJUMP(board -> {
        board[5][4] = new Piece(Type.KING, Color.RED);
        board[7][2] = new Piece(Type.KING, Color.RED);
        board[2][3] = new Piece(Type.KING, Color.WHITE);
        board[2][5] = new Piece(Type.KING, Color.WHITE);
        board[4][5] = new Piece(Type.KING, Color.WHITE);
    }),
    
    ENDGAME(board -> {
        board[3][4] = new Piece(Type.KING, Color.WHITE);
        board[4][3] = new Piece(Type.KING, Color.RED);
    });
    
    private final BoardAction action;
    
    TestMode(BoardAction action) {
        this.action = action;
    }
    
    public void fillBoard(Piece[][] board) {
        action.fillBoard(board);
    }
}

