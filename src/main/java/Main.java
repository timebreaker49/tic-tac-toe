package main.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Board board = null;
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
                + "Here is our starting player!! : " + board.currentPlayer
                + "\nPlayer 1 (" + board.currentPlayer + "), pick a number"
                + "\nfrom 0 to " + (board.boardSize - 1)  + " to make your mark!");

        while(!board.isGameOver) {
            int selection = scanner.nextInt();
            Board.doTurn(selection);
            board.print();
        }
    }
}
