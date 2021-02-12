package main.java;

import java.util.*;

// TODO Move this to game logic or get rid of it altogether
/*
 * [
 * [(0, 0), (0, 1), (0, 2)],
 * [(1, 0), (1, 1), (1, 2)],
 * [(2, 0), (2, 1), (2, 2)]
 * ]
 * */

class Board {
    static String[][] board; // in the future, would like user to be able to create variable sized board
    static int moveCounter;
    int boardSize;
    static String currentPlayer;
    static String[] players;
    static int isGameOver;
    static Map<Integer, int[]> position;

    public Board() {
        board = new String[3][3];
        players = new String[] {"x", "o"}; // TODO Implement player character choice
        int positionIndex = 0;
        position = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                position.put(positionIndex, new int[] {i, j});
                board[i][j] = String.valueOf(positionIndex++);
            }
        }

        moveCounter = 0;
        boardSize = board.length * board[0].length;
        currentPlayer = Math.random() < 0.5 ? players[0] : players[1];
        isGameOver = 0;
    }

    static int[] positionLookup(int key) {
        return position.get(key);
    }

    public void print() {
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
