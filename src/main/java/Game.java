package main.java;

import java.util.Scanner;

public class Game {
    static Board initializeGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you ready to play?!?! Enter 'y' or 'n'");
        String s = scanner.nextLine();
        while(!s.equals("y")) {
            System.out.println("How bout now?!?! Enter 'y' or 'n'");
            s = scanner.nextLine();
        }
        return createBoard();
    }

    static Board createBoard() {
        System.out.println("----------------"
                + "\nProducing Board!..."
                + "\n----------------");
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        Board board = new Board();
        board.print();
        System.out.println("\n"
                + "Here is our starting player!! : " + Board.currentPlayer
                + "\nPlayer 1 (" + Board.currentPlayer + "), pick a number"
                + "\nfrom 0 to " + (board.boardSize - 1)  + " to make your mark!");
        return board;
    }

    static void runGame(Board gameBoard) {
        Scanner scanner = new Scanner(System.in);
        while (Board.isGameOver < 1 && Board.moveCounter < gameBoard.boardSize) {
            String selection = scanner.nextLine();
            String digitCheck = "\\d+";
            if (selection.matches(digitCheck)
                && Board.position.containsKey(Integer.parseInt(selection))) {
                // process player input
                boolean turnSuccess = markBoard(selection);
                // game over!
                if (turnSuccess && Board.isGameOver == 1) {
                    System.out.println("Congratulations, we have a winner!! Good job player "
                            + Board.currentPlayer + "\n\nHere's the final board");
                    gameBoard.print();
                    System.out.println("Would you like to play again? Please input 'y' or 'n'");
                    if (scanner.nextLine().equals("y")) {
                        gameBoard = createBoard();
                        System.out.println("Board moves~: " + Board.moveCounter);
                    } else {
                        break;
                    }
                } else if (!turnSuccess) { // spot taken, try again
                    System.out.println("Sorry, that spot is taken! Please pick a different spot\n");
                } else { // turn success and game continues
                    System.out.println("Turn successful! " + Board.currentPlayer + ", you're up next!\n");
                }
            } else {
                System.out.println("Please select an valid number from 0-8");
            }
            gameBoard.print();
        }
        if(Board.isGameOver == 0) // no more spots available on the board, game over
            System.out.println("It looks like we have a draw! Try playing again!");
    }

    static boolean markBoard(String index) {
        // get board position, mark board (if position available)
        int[] pos = Board.positionLookup(Integer.parseInt(index));
        return markPosition(pos);
    }

    static boolean markPosition(int [] pos) {
    // checks valid input, updates game board, checks winner
        boolean turnSuccess = false;
        int r = pos[0], c = pos[1];
        String digitCheck = "\\d+";
        if (Board.board[r][c].matches(digitCheck)) {
            Board.board[r][c] = Board.currentPlayer; // updates the board
            Board.moveCounter++;
            boolean winCheck = checkWinner(Board.currentPlayer, r, c);
            if(winCheck) Board.isGameOver = 1;
            setPlayer();
            turnSuccess = true;
        }
        return turnSuccess;
    }

    static void setPlayer() {
        Board.currentPlayer = Board.currentPlayer.equals(Board.players[0])
            ? Board.players[1] : Board.players[0]; // updates current player
    }

    static boolean checkWinner(String playerString, int row, int column) {
        // check row for winner
        boolean rowWinner = checkRow(playerString, row);
        // check column for winner
        boolean columnWinner = checkColumn(playerString, column);
        // check diagonal for winner
        boolean diagonalWinner = checkDiagonal(playerString);

        return (rowWinner || columnWinner || diagonalWinner);
    }

    static boolean checkRow(String playerString, int row) {
        int j = 0;
        while (j < Board.board[0].length) {
            String checkedValue = Board.board[row][j++];
            if (!checkedValue.equals(playerString)) return false;
        }
        return true;
    }

    static boolean checkColumn(String playerString, int column) {
        int i = 0;
        while (i < Board.board.length) {
            String checkedValue = Board.board[i++][column];
            if (!checkedValue.equals(playerString)) return false;
        }
        return true;
    }

    static boolean checkDiagonal(String playerString) {
        // check to see if it's upper left diagonal or upper right diagonal
        boolean upperLeftDiag = false;
        boolean upperRightDiag = false;
        int rowLength = Board.board[0].length;

        // checks for diagonal from top left to bottom right
        int i = 0;
        int j = 0;
        int counterOne = 0;
        while (i < rowLength) {
            if (Board.board[i++][j++].equals(playerString)) counterOne++;
            if (counterOne == rowLength) upperLeftDiag = true;
        }
        // checks for diagonal from top right to bottom left
        i = 0;
        j = rowLength - 1;
        int counterTwo = 0;
        while (j >= 0) {
            if (Board.board[i++][j--].equals(playerString)) counterTwo++;
            if (counterTwo == rowLength) upperRightDiag = true;
        }

        // check to see if either upperLeft or upperRight are true
        return (upperLeftDiag || upperRightDiag);
    }
}
