package main.java;

import java.util.*;

public class Game {
    Scanner scanner;
    Board board;
    int numOfGames;
    int[] playerWins;
    Boolean onePlayerMode;
    String currentPlayer;

    public void initializeGame() {
        scanner = new Scanner(System.in);
        System.out.println("Are you ready to play?!?! Enter 'y' or 'n'");
        String s = scanner.nextLine();
        while(!s.equals("y")) {
            System.out.println("How about now? Please enter 'y' or 'n'");
            s = scanner.nextLine();
        }
        isOnePlayerMode();
        selectNumberOfGames();
        createBoard(selectPlayerNames(), selectBoardSize());
        setInitialPlayer();
    }

    public void runGame() {
        while (board.isGameOver < 1 && board.moveCounter < board.boardSize) {
            processTurn();
            if(onePlayerMode && board.isGameOver != 1) computerTurn();
        }
    }

    private void createBoard(String[] players, int sizeOfBoard) {
        board = new Board(players, sizeOfBoard);
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
    }

    private void isOnePlayerMode() {
        System.out.println("Play against the computer? Enter 'y' for 1-player mode or 'n' for 2-player mode");
        String playAgainstComputer = validateYOrN(scanner.nextLine());
        onePlayerMode = playAgainstComputer.equals("y");
    }

    private String[] selectPlayerNames() {
        System.out.println("Would you like to select your player character? Enter 'y' or 'n'");
        String wantsToSelectPlayerNames = validateYOrN(scanner.nextLine());
        String[] players = new String[]{"", ""};

        if(wantsToSelectPlayerNames.equals("y")) {
            System.out.println("Player one, input the name you'd like for your character: ");
            players[0] = scanner.nextLine();
            System.out.println("\nPlayer one(aka " + players[0] + "), I hope you like that name, because you're stuck with it");
            System.out.println("Player two, you're up, select your name: ");
            players[1] = scanner.nextLine();
        } else {
            players[0] = "x";
            players[1] = "o";
        }

        return players;
    }

    private int selectBoardSize() {
        System.out.println("Would you like to select the size of the board? 'y' or n'");
        String wantsToSelectBoardSize = validateYOrN(scanner.nextLine());
        String boardSize;

        if (wantsToSelectBoardSize.equals("y")) {
            System.out.println("Select board size by choosing a number 3 to 10");
            boardSize = scanner.nextLine();
            String digitCheck = "\\d+";
            while(!boardSize.matches(digitCheck) && Integer.parseInt(boardSize) > 3) {
                System.out.println("Please select board size by choosing a number 3 to 10");
                boardSize = scanner.nextLine();
            }
        } else boardSize = "3";

        return Integer.parseInt(boardSize);
    }

    private void selectNumberOfGames() {
        System.out.println("Please enter how many games you'd like to play (by choosing a number 1 and 10)");
        String numGames = scanner.nextLine();
        String digitCheck = "\\d+";
        while(!numGames.matches(digitCheck)) {
            System.out.println("Please select board size by choosing a number 1 to 10");
            numGames = scanner.nextLine();
        }
        numOfGames = Integer.parseInt(numGames);
        playerWins = new int[] {0,0};
    }

    private void setInitialPlayer() {
        currentPlayer = Math.random() < 0.5 ? board.players[0] : board.players[1];
        System.out.println("\n"
                + "Here is our starting player!! : " + currentPlayer
                + "\nPlayer 1 (" + currentPlayer + "), pick a number"
                + "\nfrom 0 to " + (board.boardSize - 1)  + " to make your mark!");
    }

    private void processTurn() {
        String selection = scanner.nextLine();
        String digitCheck = "\\d+";
        // if valid digit, see if it can be placed on the board
        if (selection.matches(digitCheck) && board.position.containsKey(Integer.parseInt(selection))) {
            boolean turnSuccess = markBoard(selection);
            if (turnSuccess && board.isGameOver == 1) {
                System.out.println("Congratulations, we have a winner!! Good job player " + currentPlayer + "\n\nHere's the final board");
                if (currentPlayer.equals(board.players[0])) {
                    playerWins[0]++;
                } else {
                    playerWins[1]++;
                }
                board.print();
                handleReplay();
            } else if (!turnSuccess) { // spot taken, try again
                System.out.println("Sorry, that spot is taken! Please pick a different spot\n");
                processTurn();
            } else { // turn success and game continues
                System.out.println("Turn successful! " + currentPlayer + ", you're up next!\n");
            }
            if(board.isGameOver != 1 && board.moveCounter > 0 && !onePlayerMode) { // prints the game board while game is not over
                board.print();
            }
        } else { // invalid entry
            System.out.println("Please select an valid number from 0-" + board.boardSize);
            processTurn();
        }

        if (board.moveCounter == board.boardSize && board.isGameOver != 1) { // if the board is full and there's no winner
            System.out.println("\nLooks like there was a tie!");
            handleReplay();
        }
    }

    private void computerTurn() {
        System.out.println("Computer turn, please wait!\n");
        board.print();
        int computerTurn = generateComputerTurnIndex();
        boolean turnSuccess = markBoard(String.valueOf(computerTurn));
        if (turnSuccess && board.isGameOver == 1) {
            System.out.println("Congratulations, we have a winner!! Good job player " + currentPlayer + "\n\nHere's the final board");
            if (currentPlayer.equals(board.players[0])) {
                playerWins[0]++;
            } else {
                playerWins[1]++;
            }
            board.print();
            handleReplay();
        } else { // turn success and game continues
            System.out.println("\nComputer turn successful! " + currentPlayer + ", you're up next!\n");
        }
        if(board.isGameOver != 1 && board.moveCounter > 0) { // prints the game board while game is not over
            board.print();
        }
    }

    private int generateComputerTurnIndex() {
        Random random = new Random();
        int index = random.nextInt(board.availablePosition.size());
        boolean containsIndex = board.availablePosition.contains(index);
        while(!containsIndex) {
            index = random.nextInt(board.availablePosition.size());
            containsIndex = board.availablePosition.contains(index);
        }

        return index;
    }

    private void handleReplay() {
        int numGamesForWin = numOfGames / 2 + 1;
        if (playerWins[0] == numGamesForWin || playerWins[1] == numGamesForWin) {
            System.out.println("\nThe game is over! Would you like to play again? Please input 'y' or 'n'");
            if (validateYOrN(scanner.nextLine()).equals("y")) {
                initializeGame();
            }
        } else {
            System.out.println("\nReady for the next round?!?!");
            createBoard(board.players, (int) Math.sqrt(board.boardSize));
            setPlayer();
            System.out.println("\nNext player up! Go on, player " + currentPlayer);
        }
    }

    private boolean markBoard(String index) {
        int[] pos = board.positionLookup(Integer.parseInt(index));
        boolean turnSuccess = false;
        int r = pos[0], c = pos[1];
        String digitCheck = "\\d+";
        if (board.board[r][c].trim().matches(digitCheck)) {
            board.board[r][c] = (currentPlayer.equals(board.players[board.longerPlayerString])
                    ? currentPlayer : board.adjustSpaces(board.longerPlayerString, currentPlayer));
            board.availablePosition.remove(Integer.parseInt(index));
            board.moveCounter++;
            boolean winCheck = checkWinner(currentPlayer, r, c);
            if(winCheck) board.isGameOver = 1;
            if(board.isGameOver != 1) setPlayer();
            turnSuccess = true;
        }
        return turnSuccess;
    }

    private void setPlayer() {
        currentPlayer = currentPlayer.equals(board.players[0])
            ? board.players[1] : board.players[0]; // updates current player
    }

    private boolean checkWinner(String playerString, int row, int column) {
        // check row for winner
        boolean rowWinner = checkRow(playerString, row);
        // check column for winner
        boolean columnWinner = checkColumn(playerString, column);
        // check diagonal for winner
        boolean diagonalWinner = checkDiagonal(playerString);

        return (rowWinner || columnWinner || diagonalWinner);
    }

    private boolean checkRow(String playerString, int row) {
        int j = 0;
        while (j < board.board[0].length) {
            String checkedValue = board.board[row][j++].trim();
            if (!checkedValue.equals(playerString)) return false;
        }
        return true;
    }

    private boolean checkColumn(String playerString, int column) {
        int i = 0;
        while (i < board.board.length) {
            String checkedValue = board.board[i++][column].trim();
            if (!checkedValue.equals(playerString)) return false;
        }
        return true;
    }

    private boolean checkDiagonal(String playerString) {
        // check to see if it's upper left diagonal or upper right diagonal
        boolean upperLeftDiag = false;
        boolean upperRightDiag = false;
        int rowLength = board.board[0].length;

        // checks for diagonal from top left to bottom right
        int i = 0;
        int j = 0;
        int counterOne = 0;
        while (i < rowLength) {
            if (board.board[i++][j++].trim().equals(playerString)) counterOne++;
            if (counterOne == rowLength) upperLeftDiag = true;
        }
        // checks for diagonal from top right to bottom left
        i = 0;
        j = rowLength - 1;
        int counterTwo = 0;
        while (j >= 0) {
            if (board.board[i++][j--].trim().equals(playerString)) counterTwo++;
            if (counterTwo == rowLength) upperRightDiag = true;
        }

        // check to see if either upperLeft or upperRight are true
        return (upperLeftDiag || upperRightDiag);
    }

    private String validateYOrN(String answer) {
        List<String> yOrN = new ArrayList<>(Arrays.asList("y", "n"));
        while(!yOrN.contains(answer)) {
            System.out.println("Please enter 'y' or 'n'");
            answer = scanner.nextLine();
        }
        return answer;
    }
}
