package com.example.locjava;
import java.util.ArrayList;

public class Computer extends Player{


    public Computer() {
        System.out.println("Computer Created");
        human = false;
        score = 0;
        wins = 0;
    }

    /* *********************************************
    Source Code for the Computer to play
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: play
     * Executes strategies to determine the best move in the current state of the game and then plays that move.
     *
     * @param argBoard A Board object. The board is used to assess valid positions and is not modified within this function.
     * @param argCurrentPlayer A Player object used to validate potential strategies without changing the player state.
     * @param argNextPlayer A Player object that provides context for strategy selection, helping to determine the move based on future turns.
     *
     * <p>Algorithm:
     * 1. Run strategies to determine the best move in the current state of the game.
     * 2. From the strategies found, play the best one.
     *
     * <p>Note: This function does not return any value as its purpose is to execute a game move directly.
     */

    public void play(Board argBoard, Player argCurrentPlayer, Player argNextPlayer) {
        System.out.println("Player Computer (" + p_color + ") turn:");
        runStrategies(argBoard, argCurrentPlayer, argNextPlayer);
        playStrategy(argBoard, argCurrentPlayer);
    }

    /**
     * Name: playStrategy
     * Finds and executes the best strategy on the board. This function assesses the current game state to determine the most effective move for
     * the current player, then applies that strategy by moving pieces on the board.
     *
     * @param argBoard A Board object, representing the game board. This variable is not changed within this function; its purpose is to facilitate movement of pieces based on the chosen strategy.
     * @param argCurrentPlayer A Player object used to validate if the piece can legally move to the proposed spot.
     *
     * <p>Algorithm:
     * 1. Pick the best strategy based on the current game state.
     * 2. Parse line to extract the current piece and the position to move.
     * 3. Move the piece based on the best strategy found.
     * 4. Display the strategy being executed.
     *
     * <p>Note: This function does not return any value as its purpose is to execute game moves directly.
     */

    public void playStrategy(Board argBoard, Player argCurrentPlayer) {
        String line = pickStrategies();

        int colonPos = line.indexOf(':');
        int dashPos = line.indexOf('-', colonPos); // Find the dash after the colon

        String currentPiece = line.substring(colonPos + 1, dashPos);
        String positionToMove = line.substring(dashPos + 1); // Extract position to move

        argBoard.movePiece(currentPiece, positionToMove, argCurrentPlayer.getColor(), false);

        System.out.println("Computer thinking of move\n");

        System.out.println(line);
    }

}



