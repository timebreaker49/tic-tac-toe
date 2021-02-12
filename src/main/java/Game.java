package main.java;

import java.util.Scanner;

public class Game {
    static Board setupGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you ready to play?!?! Enter 'y' or 'n'");
        String s = scanner.nextLine();
        while(!s.equals("y")) {
            System.out.println("How bout now?!?! Enter 'y' or 'n'");
            s = scanner.nextLine();
        }

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
                boolean turnSuccess = Board.doTurn(selection);
                // game over!
                if (turnSuccess && Board.isGameOver == 1) {
                    System.out.println("Congratulations, we have a winner!! Good job player "
                            + Board.currentPlayer + "\n\nHere's the final board");
                    gameBoard.print();
                    break;
                } else if (!turnSuccess) { // spot taken, try again
                    System.out.println("Sorry, that spot is taken! Please pick a different spot\n");
                } else { // turn success and game continues
                    System.out.println("Turn successful! " + Board.currentPlayer + ", you're up next!\n");
                }
            } else {
                System.out.println("Please select an valid number from 0-9");
            }
            gameBoard.print();
        }
        if(Board.isGameOver == 0) // no more spots available on the board, game over
            System.out.println("It looks like we have a draw! Try playing again!");
    }
}
