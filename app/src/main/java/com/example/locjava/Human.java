package com.example.locjava;
import android.util.Log;

import java.util.Scanner;


class Human extends Player{


    Human() {
        System.out.println("Human created " + p_ID);
        human = true;
        score = 0;
        wins = 0;
    }

    /**
     * Name: play
     * Validates user input for piece movement and executes the correct movement on the board.
     *
     * @param argBoard A Board object, which is not modified directly within this function. It is used to verify and execute moves.
     * @param argCurrentPlayer A Player object, used to determine if the current player's move is valid. This object is not edited.
     * @param argNextPlayer A Player object, provides additional context but is not modified.
     *
     * <p>Algorithm:
     * 1. Ask the human player for input or if necessary, they can press 1 to trigger specific actions.
     * 2. Validate that the piece selected for movement is legally allowed to move.
     * 3. Validate that the destination position is legal and the path is unobstructed.
     * 4. Execute the move on the board based on the validated input.
     *
     * <p>Note: This function does not return any value as its purpose is to execute board movements directly.
     */
    public void play(Board argBoard, Player argCurrentPlayer, Player argNextPlayer) {
        System.out.println("If you need help press 1");
        System.out.println("Player Human (" + p_color + ") turn:");
        Scanner scanner = new Scanner(System.in);
        String currentPos = scanner.nextLine();
        currentPos = inputValidation(currentPos, argBoard, argCurrentPlayer, argNextPlayer);
        System.out.println("Position to move: ");
        String newPos = scanner.nextLine();
        argBoard.movePiece(currentPos, newPos, p_color, human);
    }

    /* *********************************************
    Source Code human validation
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: sizeValidation
     * Validates the size of the coordinate input provided by the user.
     *
     * @param Coordinates A string representing the input coordinates from the user.
     * @return A boolean value. Returns true if the size of the Coordinates string is exactly 2; otherwise, returns false.
     *
     * <p>Algorithm:
     * 1. Check that the size of the Coordinates string is exactly 2.
     * 2. Return true if the condition is met; return false otherwise.
     */

    private boolean sizeValidation(String Coordinates) {
        return Coordinates.length() == 2;
    }

    /**
     * Name:letterValidation
     * Validates the letter part of the coordinate input provided by the user.
     *
     * @param Coordinates A string representing the user's input coordinates, where a letter is anticipated to be the initial character.
     * @return A boolean value. Returns true if the first character is between 'A' and 'H'; otherwise, returns false.
     *
     * <p>Algorithm:
     * 1. Extract the first character of the Coordinates string.
     * 2. Check if this letter falls within the range 'A' to 'H'.
     * 3. Return true if the letter lies within this range; return false otherwise.
     */

    private boolean letterValidation(String Coordinates) {
        char letter = Coordinates.charAt(0);
        return letter >= 'A' && letter <= 'H';
    }

    /**
     * Name: numberValidation
     * Validates the number part of the coordinate input by the user.
     *
     * @param Coordinates A string representing the input coordinates from the user.
     * @return A boolean value. Returns true if the second character corresponds to a valid board row number (1 to 8); returns false in any other case.
     *
     * <p>Algorithm:
     * 1. Convert the second character of the Coordinates to a number.
     * 2. Check if the number is within the range of 1 to 8.
     * 3. Return true if the number falls inside this range; otherwise, return false.
     */

    private boolean numberValidation(String Coordinates) {
        int number = Coordinates.charAt(1) - '0';
        return number >= 1 && number <= 8;
    }

    /**
     * Name: selectoinValidation
     * Validates whether the coordinates input by the user correspond to a piece of the current player's color on the board.
     *
     * @param Coordinates A string representing the coordinates input by the user, expected to specify a location on the board.
     * @param argBoard A Board object, which provides access to the game board for validation.
     * @return A boolean value. Returns true if the current player's color is at the chosen coordinate; returns false otherwise.
     *
     * <p>Algorithm:
     * 1. Convert the letter part of the Coordinates to a column index and the number part to its row index for the board.
     * 2. Get a copy of the board.
     * 3. Check if the converted coordinates match the current player's color.
     * 4. To prevent memory leaks, tidy up the temporary board. (Note: This step pertains to C++ and may not be necessary in Java.)
     * 5. Return true if the piece's color matches the player's; return false otherwise.
     */

    public boolean selectionValidation(String Coordinates, Board argBoard) {
        int convertedLetter = Coordinates.charAt(0) - 'A';
        int convertedNumber = 8 - (Coordinates.charAt(1) - '0');
        char[][] tempboard = argBoard.getCopyBoard();

        if (tempboard[convertedNumber][convertedLetter] == p_color) {
            System.out.println("Selected correct color " + p_color);
            return true;
        }
        return false;
    }

    /**
     * Name: caseValidation
     * Validates the human player's input coordinates through a series of checks to ensure the input is correct and valid for a move.
     *
     * @param Coordinates A string representing the coordinates that the user entered.
     * @param argBoard A Board object, which serves as the current game board for validating the move.
     * @return A boolean value. Returns true if all validation checks are successful and the input is found to be valid; returns false otherwise.
     *
     * <p>Algorithm:
     * 1. Check if input is "1" for help; immediately exit validation if so.
     * 2. Perform size validation to ensure the input length is correct.
     * 3. Perform letter validation to ensure the input starts with a proper letter.
     * 4. Perform number validation to ensure the input ends with a valid board number.
     * 5. Perform selection validation to ensure the piece's color matches the player's color.
     * 6. Return true if every check is successful; otherwise, provide feedback and ask the user to enter the correct information.
     */
    private boolean caseValidation(String Coordinates, Board argBoard) {
        if (Coordinates.equals("1")) {
            return false;
        }

        if (!sizeValidation(Coordinates)) {
            System.out.println("Enter proper coordinates EX (A1,H8)");
            return false;
        }

        if (!letterValidation(Coordinates)) {
            System.out.println("Enter letter from A - H");
            return false;
        }

        if (!numberValidation(Coordinates)) {
            System.out.println("Enter number from 1-8");
            return false;
        }

        if (!selectionValidation(Coordinates, argBoard)) {
            System.out.println("Select color coordinated piece");
            return false;
        }

        return true;
    }

    /**
     * Validates user input for coordinates and checks if the user requires assistance with their move.
     *
     * @param coordinates A string representing the input coordinates from the user.
     * @param argBoard A Board object that represents the game board, used to validate the input.
     * @param argCurrentplayer A Player object, used to access information needed for validating input.
     * @param argNextplayer A Player object, also used for accessing additional information for input validation.
     * @return The validated input string. If the user requests help by entering "1", it may trigger a different response.
     *
     * <p>Algorithm:
     * 1. Validate the input repeatedly until all validity checks are passed.
     * 2. Provide assistance if the input is "1", with suggestions coming from a strategy function.
     * 3. If validation fails, prompt the user to re-enter the move.
     * 4. Break out of the loop and return the validated input as soon as valid input is received.
     */

    private String inputValidation(String coordinates, Board argBoard, Player argCurrentplayer, Player argNextplayer) {
        String input = coordinates;

        while (true) {
            if (caseValidation(input, argBoard)) {
                System.out.println("Player entered proper input: " + coordinates);
                break;
            } else {
                if (input.equals("1")) {
                    runStrategies(argBoard, argCurrentplayer, argNextplayer);
                    System.out.println("Suggested move: " + pickStrategies());
                    System.out.println("Enter a piece to move: ");
                } else {
                    System.out.println("Enter move: ");
                }
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
            }
        }
        return input;
    }


}



