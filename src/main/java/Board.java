package main.java;

import java.util.*;

/*
 * [
 * [(0, 0), (0, 1), (0, 2)],
 * [(1, 0), (1, 1), (1, 2)],
 * [(2, 0), (2, 1), (2, 2)]
 * ]
 * */

class Board {
    static String[][] board; // in the future, would like user to be able to create variable sized board
    static int counter;
    int boardSize;
    static String currentPlayer;
    static String[] players;
    boolean isGameOver;
    static Map<Integer, int[]> position;

    public Board() {
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

        counter = 0;
        boardSize = board.length * board[0].length;
        currentPlayer = Math.random() < 0.5 ? players[0] : players[1];
        isGameOver = false;
    }

    private static int[] positionLookup(int key) {
        return position.get(key);
    }

    static boolean doTurn(int index) {
        boolean turnSuccess = false;

        int[] pos = positionLookup(index);
        int r = pos[0], c = pos[1];

        if (Arrays.stream(players).noneMatch(currentPlayer::equals)) {
            counter++;
            board[r][c] = currentPlayer;
            checkWinner(currentPlayer, r, c); // if not false, WE HAVE A WINNER
            turnSuccess = true;
        }

//        if (counter == boardSize) {
            // game is finished!
            // replay?!
//        }

        // changes the player character
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];

        return turnSuccess; // request a different input somehow
    }

    private static boolean checkWinner(String playerString, int row, int column) {
        // check row for winner
        boolean rowWinner = checkRow(playerString, row);
        // check column for winner
        boolean columnWinner = checkColumn(playerString, column);
        // check diagonal for winner
        boolean diagonalWinner = checkDiagonal(playerString, row, column);

        return (rowWinner || columnWinner || diagonalWinner);
    }

    private static boolean checkRow(String playerString, int row) {
        String startValue = playerString;
        int j = 0;
        String checkedValue = board[row][j];
        while (j++ < board[0].length) {
            if (checkedValue != startValue) return false;
        }
        return true;
    }

    private static boolean checkColumn(String playerString, int column) {
        String startValue = playerString;
        int i = 0;
        String checkedValue = board[i][column];
        while (i++ < board.length) {
            if (checkedValue != startValue) return false;
        }
        return true;
    }

    private static boolean checkDiagonal(String playerString, int row, int column) {
        // check to see if it's upper left diagonal or upper right diagonal
        boolean upperLeftDiag = false;
        boolean upperRightDiag = false;
        String startValue = playerString;
        int rowLength = board[0].length;

        // checks for diagonal from top left to bottom right
        int i = 0;
        int j = 0;
        int counterOne = 0;
        while (i < rowLength) {
            if (board[i++][j++] == playerString) counterOne++;
            if (counterOne == rowLength - 1) upperLeftDiag = true;
        }
        // checks for diagonal from top right to bottom left
        i = 0;
        j = rowLength;
        int counterTwo = 0;
        while (j > 0) {
            if (board[i++][j--] == playerString) counterTwo++;
            if (counterTwo == rowLength - 1) upperRightDiag = true;
        }

        // check to see if either upperLeft or upperRight are true
        return (upperLeftDiag || upperRightDiag);
    }

    public void print() {
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
