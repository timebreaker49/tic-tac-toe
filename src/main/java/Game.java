package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    static Scanner scanner;

    static Board initializeGame() {
        scanner = new Scanner(System.in);
        System.out.println("Are you ready to play?!?! Enter 'y' or 'n'");
        String s = scanner.nextLine();
        while(!s.equals("y")) {
            System.out.println("How bout now?!?! Enter 'y' or 'n'");
            s = scanner.nextLine();
        }

        return createBoard();
    }

    static Board createBoard() {
        List<String> yOrN = new ArrayList<>(Arrays.asList("y", "n"));

        System.out.println("Would you like to select the size of the board? 'y' or n'");
        String wantsToSelectBoardSize = scanner.nextLine();

        while(!yOrN.contains(wantsToSelectBoardSize)) {
            System.out.println("Please enter 'y' or 'n'");
            wantsToSelectBoardSize = scanner.nextLine();
        }

        int sizeOfBoard = (!wantsToSelectBoardSize.equals("y")) ? 3 : selectBoardSize();

        System.out.println("Would you like to select your player character? Enter 'y' or 'n'");
        String wantsToSelectPlayerNames = scanner.nextLine();

        while(!yOrN.contains(wantsToSelectPlayerNames)) {
            System.out.println("Please enter 'y' or 'n'");
            wantsToSelectPlayerNames = scanner.nextLine();
        }
        Board board = (wantsToSelectPlayerNames.equals("y")) ? new Board(selectPlayerNames(), sizeOfBoard) : new Board(sizeOfBoard);
        System.out.println("----------------"
                + "\nProducing Board!..."
                + "\n----------------");
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        board.print();
        System.out.println("\n"
                + "Here is our starting player!! : " + Board.currentPlayer
                + "\nPlayer 1 (" + Board.currentPlayer + "), pick a number"
                + "\nfrom 0 to " + (board.boardSize - 1)  + " to make your mark!");
        return board;
    }

    private static String[] selectPlayerNames() {
        System.out.println("Player one, input the name you'd like for your character: ");
        String playerOne = scanner.nextLine();
        System.out.println("Player one(aka " + playerOne + "), I hope you like that name, because you're stuck with it");
        System.out.println("Player two, you're up, select your name: ");
        String playerTwo = scanner.nextLine();

        return new String[] {playerOne, playerTwo};
    }

    private static int selectBoardSize() {
        System.out.println("Select board size by choosing a number 3 to 10");
        String boardSize = scanner.nextLine();
        String digitCheck = "\\d+";
        while(!boardSize.matches(digitCheck) && Integer.parseInt(boardSize) > 3) {
            System.out.println("Please select board size by choosing a number 3 to 10");
            boardSize = scanner.nextLine();
        }
        return Integer.parseInt(boardSize);
    }

    static void runGame(Board gameBoard) {
        while (Board.isGameOver < 1 && Board.moveCounter < gameBoard.boardSize) {
            processTurn(gameBoard);
        }
    }

    static void processTurn(Board gameBoard) {
        String selection = scanner.nextLine();
        String digitCheck = "\\d+";
        // if valid digit, see if it can be placed on the board
        if (selection.matches(digitCheck) && Board.position.containsKey(Integer.parseInt(selection))) {
            boolean turnSuccess = markBoard(selection);
            if (turnSuccess && Board.isGameOver == 1) {
                System.out.println("Congratulations, we have a winner!! Good job player " + Board.currentPlayer + "\n\nHere's the final board");
                gameBoard.print();
                gameBoard= handleReplay(gameBoard);
            } else if (!turnSuccess) { // spot taken, try again
                System.out.println("Sorry, that spot is taken! Please pick a different spot\n");
            } else { // turn success and game continues
                System.out.println("Turn successful! " + Board.currentPlayer + ", you're up next!\n");
            }
            if(Board.isGameOver != 1 && Board.moveCounter > 0) { // prints the game board while game is not over
                gameBoard.print();
            }
        } else { // invalid entry
            System.out.println("Please select an valid number from 0-" + gameBoard.boardSize);
        }

        if (Board.moveCounter == gameBoard.boardSize && Board.isGameOver != 1) { // if the board is full and there's no winner
            handleReplay(gameBoard);
        }
    }

    private static Board handleReplay(Board gameBoard) {
        System.out.println("\nWould you like to play again? Please input 'y' or 'n'");
        if (scanner.nextLine().equals("y")) {
            gameBoard = createBoard();
        }
        return gameBoard;
    }

    static boolean markBoard(String index) {
        int[] pos = Board.positionLookup(Integer.parseInt(index));
        boolean turnSuccess = false;
        int r = pos[0], c = pos[1];
        String digitCheck = "\\d+";
        if (Board.board[r][c].trim().matches(digitCheck)) {
            Board.board[r][c] = (Board.currentPlayer.equals(Board.players[Board.longerString])
                    ? Board.currentPlayer : Board.adjustSpaces(Board.longerString, Board.currentPlayer));
            Board.moveCounter++;
            boolean winCheck = checkWinner(Board.currentPlayer, r, c);
            if(winCheck) Board.isGameOver = 1;
            if(Board.isGameOver != 1) setPlayer();
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
            String checkedValue = Board.board[row][j++].trim();
            if (!checkedValue.equals(playerString)) return false;
        }
        return true;
    }

    static boolean checkColumn(String playerString, int column) {
        int i = 0;
        while (i < Board.board.length) {
            String checkedValue = Board.board[i++][column].trim();
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
            if (Board.board[i++][j++].trim().equals(playerString)) counterOne++;
            if (counterOne == rowLength) upperLeftDiag = true;
        }
        // checks for diagonal from top right to bottom left
        i = 0;
        j = rowLength - 1;
        int counterTwo = 0;
        while (j >= 0) {
            if (Board.board[i++][j--].trim().equals(playerString)) counterTwo++;
            if (counterTwo == rowLength) upperRightDiag = true;
        }

        // check to see if either upperLeft or upperRight are true
        return (upperLeftDiag || upperRightDiag);
    }
}
