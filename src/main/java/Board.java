package main.java;

import java.util.*;

class Board {
    String[][] board;
    int moveCounter;
    int boardSize;
    static String currentPlayer;
    static String[] players;
    int isGameOver;
    static Map<Integer, int[]> position;
    int longerPlayerString;

    public Board(int size) { // traditional board, x and o
        board = new String[size][size];
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

    public Board(String[] names, int size) { // board where users select player names
        board = new String[size][size];
        players = new String[] {names[0], names[1]};
        int positionIndex = 0;
        position = new HashMap<>();
        longerPlayerString = players[0].length() > players[1].length() ? 0 : 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                position.put(positionIndex, new int[] {i, j});
                board[i][j] = adjustSpaces(longerPlayerString, String.valueOf(positionIndex++));
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

    static String adjustSpaces(int longerPlayerString, String entry) {
        int longestString = players[longerPlayerString].length();
        int currentEntryLength = entry.length();

        int leftHalfSpaces = (longestString - currentEntryLength) / 2;
        int rightHalfSpaces = longestString - currentEntryLength - leftHalfSpaces;

        String leftPrintedSpaces = printSpaces(leftHalfSpaces);
        String rightPrintedSpaces = printSpaces(rightHalfSpaces);
        return leftPrintedSpaces + entry + rightPrintedSpaces;
    }

    private static String printSpaces(int numSpaces) {
        int spaceCounter = 0;
        StringBuilder spaces = new StringBuilder();
        while(spaceCounter < numSpaces) {
            spaces.append(" ");
            spaceCounter++;
        }
        return spaces.toString();
    }

    public void print() {
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
