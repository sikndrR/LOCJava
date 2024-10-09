package com.example.locjava;
import android.util.Log;

import java.util.Random;
import java.util.Scanner;

public class Round {
    private int blackScore;
    private int blackWins;
    private int whiteScore;
    private int whiteWins;

    public Round() {
        blackScore = 0;
        blackWins = 0;
        whiteScore = 0;
        whiteWins = 0;
    }

    /**
     * Name: newSelectionValidation
     * Validating if the coordinate of a selected piece is that of a currentplayer can use
     *
     * @param Coordinates A string the input from the user to validate.
     * @param argBoard A board object representing the current board.
     * @param currentPlayer A player object representing the currentplayer
     * @return boolean, if the piece is valid or not
     *
     * <p>Algorithm
     * 1. Convert the Coordinates into a information the board can use
     * 2. get copy of board
     * 3. check if the position of the coordinates on the board match the players color
     * 4. return if the coordinates is a valid piece or not.
     */
    public boolean newSelectionValidation(String Coordinates, Board argBoard,Player currentPlayer){

        int convertedLetter = Coordinates.charAt(0) - 'A';
        int convertedNumber = 8 - (Coordinates.charAt(1) - '0');
        char[][] tempboard = argBoard.getCopyBoard();

        Log.d("convertLet",Integer.toString(convertedLetter));
        Log.d("convertNum",Integer.toString(convertedNumber));


        if(tempboard[convertedNumber][convertedLetter] == currentPlayer.getColor()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Name: newCoinflip
     * Helps decide which player is going to be the current player for the round through a coin flip,
     * either at the start of the game or if the wins are tied between players.
     *
     * @param player1 Player object, this player will be assigned the starting color black
     *                   if they win the coin flip; otherwise, they are assigned white.
     * @param player2 Player object Similar to argPlayer1, this player will be assigned
     *                   the starting color black if they win the coin flip; otherwise, they are assigned white.
     * @return player class either argPlayer1 or argPlayer2, depending on the outcome of the coin flip. Decided not to create
     *         a player class inside the function since a good amount is happening.
     *
     * <p>Algorithm:
     * 1. A random number is assigned from 0 to 9.
     * 2. A menu is printed, asking the user to select 1 for Heads and 0 for Tails.
     * 3. User validation occurs to ensure 1 or 0 is input.
     * 4. If the random number is <= 4, it is assigned tails; otherwise, the coin lands on heads.
     *    Setting either Heads or Tails to be true from the result.
     * 5. Check to see if the user entered the correct input as tails or heads.
     * 6. If the user input was a correct guess, the currentPlayer will be human and assigned black;
     *    otherwise, the computer will be assigned black and be the currentPlayer.
     */

    public Player newCoinflip(Player player1, Player player2, int input){

        // coin = 1, heads
        // coin = 0, tails
        // Heads >= 4
        // Tails <= 5

        // Providing a seed value
        Random random = new Random();
        // Get a random number 0 - 9
        int randomNumber = random.nextInt(10);
        //Holding values for which way the coinlanded
        boolean landedTails;

        //Random number if generated is less than or eqaul to 4 is tails, otherwise its heads
        if (randomNumber <= 4) {
            landedTails = true;
        } else {
            landedTails = false;
        }

        if(input == 0 && landedTails){ //Guessed tails Correctly
            player1.setColor('B');
            player2.setColor('W');
            return player1;
        }
        else if (input == 1 && !landedTails) { //Guessed heads Correctly
            player1.setColor('B');
            player2.setColor('W');
            return player1;
        }
        else{                           //Incorrect Guess
            player1.setColor('W');
            player2.setColor('B');
            return player2;
        }

    }
}



