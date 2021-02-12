package main.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Board board;
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

        board = new Board();
        board.print();
        System.out.println("\n"
                + "Here is our starting player!! : " + Board.currentPlayer
                + "\nPlayer 1 (" + Board.currentPlayer + "), pick a number"
                + "\nfrom 0 to " + (board.boardSize - 1)  + " to make your mark!");

        while(Board.isGameOver < 1 && Board.moveCounter < board.boardSize) {
            int selection = scanner.nextInt();
            boolean turnSuccess = Board.doTurn(selection);
            if(turnSuccess && Board.isGameOver == 1) { // game over!
                System.out.println("Congratulations, we have a winner!! Good job player " + Board.currentPlayer
                + "\n\nHere's the final board");
                board.print();
                break;
            } else if (!turnSuccess) { // spot taken, try again
                System.out.println("Sorry, that spot is taken! Please pick a different spot\n");
            } else { // turn success and game continues
                System.out.println("Turn successful! " + Board.currentPlayer + ", you're up next!\n");
            }
            board.print();
        }

        if(Board.isGameOver == 0)
            System.out.println("It looks like we have a draw! Try playing again!");
    }
}
