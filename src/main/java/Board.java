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
    static int moveCounter;
    int boardSize;
    static String currentPlayer;
    static String[] players;
    static int isGameOver;
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

        moveCounter = 0;
        boardSize = board.length * board[0].length;
        currentPlayer = Math.random() < 0.5 ? players[0] : players[1];
        isGameOver = 0;
    }

    private static int[] positionLookup(int key) {
        return position.get(key);
    }

    static boolean doTurn(int index) {
        // digit check
        String digitCheck = "\\d+";
        String validationString = String.valueOf(index);
        if(!validationString.matches(digitCheck)) return false;

        // board check
        boolean turnSuccess = false;
        int[] pos = positionLookup(index);
        int r = pos[0], c = pos[1];

        // if board position is available, mark board
        if (board[r][c].matches(digitCheck)) {
            moveCounter++; // marks where we are in the game
            board[r][c] = currentPlayer; // updates the board
            boolean winCheck = checkWinner(currentPlayer, r, c);
            if(winCheck) isGameOver = 1;
            if (isGameOver != 1) currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0]; // updates current player
            turnSuccess = true;
        }

//        if (counter == boardSize) {
            // game is finished!
            // replay?!
//        }

        return turnSuccess; // request a different input somehow
    }

    private static boolean checkWinner(String playerString, int row, int column) {
        // check row for winner
        boolean rowWinner = checkRow(playerString, row);
        // check column for winner
        boolean columnWinner = checkColumn(playerString, column);
        // check diagonal for winner
        boolean diagonalWinner = checkDiagonal(playerString);

        return (rowWinner || columnWinner || diagonalWinner);
    }

    private static boolean checkRow(String playerString, int row) {
        int j = 0;
        while (j < board[0].length) {
            String checkedValue = board[row][j++];
            if (!checkedValue.equals(playerString)) return false;
        }
        return true;
    }

    private static boolean checkColumn(String playerString, int column) {
        int i = 0;
        while (i < board.length) {
            String checkedValue = board[i++][column];
            if (!checkedValue.equals(playerString)) return false;
        }
        return true;
    }

    private static boolean checkDiagonal(String playerString) {
        // check to see if it's upper left diagonal or upper right diagonal
        boolean upperLeftDiag = false;
        boolean upperRightDiag = false;
        int rowLength = board[0].length;

        // checks for diagonal from top left to bottom right
        int i = 0;
        int j = 0;
        int counterOne = 0;
        while (i < rowLength) {
            if (board[i++][j++].equals(playerString)) counterOne++;
            if (counterOne == rowLength) upperLeftDiag = true;
        }
        // checks for diagonal from top right to bottom left
        i = 0;
        j = rowLength - 1;
        int counterTwo = 0;
        while (j >= 0) {
            if (board[i++][j--].equals(playerString)) counterTwo++;
            if (counterTwo == rowLength) upperRightDiag = true;
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
