package src;
import java.util.Scanner;

import lib.PieceType;
import lib.Player;
import lib.IllegalCommandException;
import lib.NoSuchLocationException;
import static lib.Player.*;

public class Game {

    private static Board board;
    private static Player currPlayer;
    private static Scanner scanner;
    
    private static String rollRegex = "(\\s*roll\\s*)";
    private static String positionRegex = "(\\s*[0-9]+\\s+[0-9]+\\s*)";
    private static String endRegex = "(\\s*end\\s*)";
    
    private static void initiateTurn() {
        
        Piece selectedPiece;
        String command;
        int roll;
        while (!gameHasEnded()) {
            selectedPiece = null;
            command = "";
            roll = -1;
            while (!command.matches(endRegex)) {
                try {
                    board.dump();
    
                    command = scanner.nextLine();
                    if (command.matches(positionRegex)) {
                        selectedPiece = processPositionCommand(command, selectedPiece, roll);
                    } else if (command.matches(rollRegex)) {
                        roll = processRollCommand(selectedPiece, roll);
                    } else if (!command.matches(endRegex)) {
                        throw new IllegalCommandException("Invalid command");
                    }
                } catch (IllegalCommandException ime) {
                    handleGameException(ime);
                } catch (NoSuchLocationException nsle) {
                    handleGameException(nsle);
                }
            }
            
            endTurn();
        }
    }
    
    private static Piece processPositionCommand(String command, Piece selectedPiece, int roll) throws IllegalCommandException, NoSuchLocationException {
        int[] coordinates = convertToPosition(command);
        int row = coordinates[0];
        int column = coordinates[1];
        Piece newSelectedPiece = board.getPiece(row, column);
        if (selectedPiece == null || (newSelectedPiece != null && selectedPiece.getController() == newSelectedPiece.getController())) {
            if (newSelectedPiece == null) {
                throw new IllegalCommandException("You must first select one of your pieces.");
            }
            validatePieceController(newSelectedPiece);
            selectedPiece = newSelectedPiece;
        } else {
            handleInteraction(selectedPiece, row, column, roll);
            selectedPiece = null;
        }
        return selectedPiece;
    }
    
    private static int processRollCommand(Piece currPiece, int roll) throws IllegalCommandException {
        if (currPiece == null) {
            throw new IllegalCommandException("You must select a piece before you can roll.");
        } else if (roll != -1) {
            throw new IllegalCommandException("You cannot reroll on the same turn.");
        }
        int[] rolls = Dice.simluateRolls();
        int movement = rolls[0] + rolls[1];
        System.out.println("You must move " + movement + " blocks.");
        return movement;
    }
    
    private static void handleInteraction(Piece selectedPiece, int targetRow, int targetColumn, int roll) throws NoSuchLocationException, IllegalCommandException {
        Piece targetPiece = board.getPiece(targetRow, targetColumn);
        if (targetPiece == null) {
            if (roll == -1) {
                throw new IllegalCommandException("You must roll the dice before moving your piece.");
            }
            selectedPiece.move(targetRow, targetColumn, roll);
        } else {
            handleAttack(selectedPiece, targetPiece);
        }
    }
    
    private static void handleAttack(Piece attackerPiece, Piece targetPiece) throws IllegalCommandException, NoSuchLocationException {
        PieceType targetPieceType = targetPiece.getType();
        switch (targetPieceType) {
        case CHARACTER:
            attackerPiece.attack(targetPiece);
            break;
        default:
            throw new IllegalCommandException("You cannot attack this piece.");
        }
    }
    
    private static int[] convertToPosition(String positionString) {
        positionString = positionString.trim();
        String[] positionStringArr = positionString.split("\\s+");
        int[] position = {Integer.parseInt(positionStringArr[0]), Integer.parseInt(positionStringArr[1])};
        return position;
    }
    
    private static void validatePieceController(Piece selectedPiece) throws IllegalCommandException, NoSuchLocationException {
        if (currPlayer != selectedPiece.getController()) {
            throw new IllegalCommandException("You cannot interact with a piece controlled by another player.");
        }
    }
    
    private static void endTurn() {
        currPlayer.endTurn();
        if (currPlayer == PLAYER1) {
            currPlayer = PLAYER2;
        } else {
            currPlayer = PLAYER1;
        }
    }
    
    private static void handleGameException(Exception e) {
        System.out.println(e.getMessage());
    }
    
    private static void setupBoard() {
        try {
            new CharacterPiece(PLAYER1, 1, 4, board);
            new CharacterPiece(PLAYER1, 0, 0, board);
            new CharacterPiece(PLAYER1, 0, 9, board);
            new CharacterPiece(PLAYER2, 8, 5, board);
            new CharacterPiece(PLAYER2, 9, 0, board);
            new CharacterPiece(PLAYER2, 9, 9, board);
        } catch (NoSuchLocationException nsle) {
            System.out.println("Could not successfully set up the board.");
        }
    }
    
    private static boolean gameHasEnded() {
        if (PLAYER1.isAlive() && PLAYER2.isAlive()) {
            return false;
        }
        return true;
    }
    
    private static void announceWinner() {
        if (PLAYER1.isAlive()) {
            System.out.println("PLAYER 1 wins!");
        } else if (PLAYER2.isAlive()) {
            System.out.println("PLAYER 2 wins!");
        } else {
            System.out.println("Draw!");
        }
    }
    
    public static void main(String[] args) {
        board = new Board(10);
        scanner = new Scanner(System.in);

        setupBoard();
        currPlayer = PLAYER1;
        
        while (!gameHasEnded()) {
            initiateTurn();
        }
        announceWinner();
    }
    
}
