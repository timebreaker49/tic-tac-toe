package main.java;

import java.util.*;

class Board {
    static String[][] board; // in the future, would like user to be able to create variable sized board
    static int moveCounter;
    int boardSize;
    static String currentPlayer;
    static String[] players;
    static int isGameOver;
    static Map<Integer, int[]> position;

    public Board() { // traditional board, x and o
        board = new String[3][3];
        players = new String[] {"x", "o"};
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

    public Board(String[] names) { // board where users select player names
        board = new String[3][3];
        players = new String[] {names[0], names[1]};
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

    public void print() { // TODO add padding to make board print evenly (based on length of player character string)
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
