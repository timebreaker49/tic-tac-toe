package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * [
 * [(0, 0), (0, 1), (0, 2)],
 * [(1, 0), (1, 1), (1, 2)],
 * [(2, 0), (2, 1), (2, 2)]
 * ]
 * */

class Board {
    String[][] board; // in the future, would like user to be able to create variable sized board
    int counter;
    int boardSize;
    String startString;
    boolean isGameOver;

    public Board() {
        board = new String[3][3];
        int next = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = String.valueOf(next++);
            }
        }
        counter = 0;
        boardSize = board.length * board[0].length;
        startString = Math.random() < 0.5 ? "x" : "o";
        isGameOver = false;
    }

    public boolean doTurn(String playerString, int r, int c) {
        boolean turnSuccess = false;
        List<String> markers = new ArrayList(Arrays.asList("o", "x"));

        if (!markers.contains(playerString)) return false;

        if (board[r][c] != ".") {
            counter++;
            board[r][c] = playerString;
            checkWinner(playerString, r, c); // if not false, WE HAVE A WINNER
            turnSuccess = true;
        }

        if (counter == boardSize) {
            // game is finished!
            // replay?!
        }

        return turnSuccess; // request a different input somehow
    }

    public boolean checkWinner(String playerString, int row, int column) {
        // check row for winner
        boolean rowWinner = checkRow(playerString, row);
        // check column for winner
        boolean columnWinner = checkColumn(playerString, column);
        // check diagonal for winner
        boolean diagonalWinner = checkDiagonal(playerString, row, column);

        return (rowWinner || columnWinner || diagonalWinner);
    }

    private boolean checkRow(String playerString, int row) {
        String startValue = playerString;
        int j = 0;
        String checkedValue = board[row][j];
        while (j++ < board[0].length) {
            if (checkedValue != startValue) return false;
        }
        return true;
    }

    private boolean checkColumn(String playerString, int column) {
        String startValue = playerString;
        int i = 0;
        String checkedValue = board[i][column];
        while (i++ < board.length) {
            if (checkedValue != startValue) return false;
        }
        return true;
    }

    private boolean checkDiagonal(String playerString, int row, int column) {
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
