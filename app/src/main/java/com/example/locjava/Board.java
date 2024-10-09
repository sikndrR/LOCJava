package com.example.locjava;
import java.util.ArrayList;
import java.util.List;


public class Board {
    private char[][] m_board;
    private List<String> visitedPieces = new ArrayList<>();

    /* *********************************************
    Source Code to for the boards, copying and making.
    ********************************************* */
    // List all the relevant functions here

    public Board() {
        // Initialize the board with your desired setup
        char[][] initialSetup = {
                {'.', 'B', 'B', 'B', 'B', 'B', 'B', '.'},
                {'W', '.', '.', '.', '.', '.', '.', 'W'},
                {'W', '.', '.', '.', '.', '.', '.', 'W'},
                {'W', '.', '.', '.', '.', '.', '.', 'W'},
                {'W', '.', '.', '.', '.', '.', '.', 'W'},
                {'W', '.', '.', '.', '.', '.', '.', 'W'},
                {'W', '.', '.', '.', '.', '.', '.', 'W'},
                {'.', 'B', 'B', 'B', 'B', 'B', 'B', '.'}
        };

        // Copy the initial setup to the board
        m_board = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                m_board[i][j] = initialSetup[i][j];
            }
        }
    }
    /**
     * Name: getCopyBoard
     * Creates a copy of the original board.
     *
     * @return A deep copy of the board represented as a 2D array of type char.
     *
     * <p>Algorithm:
     * 1. Allocate memory to store the copied board.
     * 2. Copy every element from the original game board to the new 2D array.
     * 3. Return the copy of the 2D array.
     *
     */

    public char[][] getCopyBoard() {
        char[][] array2D = new char[8][8];

        for (int h = 0; h < 8; h++) {
            for (int w = 0; w < 8; w++) {
                // Copying existing board array to return
                array2D[h][w] = m_board[h][w];
            }
        }
        return array2D;
    }

    /**
     * Name: serializedBoard
     * Repopulates the board based on information from a serialization file.
     *
     * @param currentRow An integer indicating the row that should be populated.
     * @param line A string containing the information that needs to repopulate the board.
     *
     * <p>Algorithm:
     * 1. Go over the string continually, looking for piece identifiers ('W', 'B', 'w', 'b', 'x') two characters at a time.
     * 2. Use the characters 'x' for vacant spaces, 'W'/'w' for white pieces, and 'B'/'b' for black pieces to update the board array.
     * 3. Convert lowercase letters 'b' and 'w' to uppercase for consistency in the piece representation.
     *
     * <p>Note: This function does not return a value; it modifies the board's state directly based on the serialized data.
     */

    public void serializeBoard(int currentRow, String line) {
        int j = 0;
        for (int i = 0; i < 15; i++) {
            if (line.charAt(i) == 'x') {
                m_board[currentRow][j] = '.';
                j++;
            } else if (line.charAt(i) == 'W' || line.charAt(i) == 'B' || line.charAt(i) == 'b' || line.charAt(i) == 'w') {
                m_board[currentRow][j] = line.charAt(i);
                if (line.charAt(i) == 'b') {
                    m_board[currentRow][j] = 'B';
                } else if (line.charAt(i) == 'w') {
                    m_board[currentRow][j] = 'W';
                }
                j++;
            }
        }
    }

    /**
     * Name: updateBoard
     * Updates the board's state if a movement is validated as valid.
     *
     * @param argCurrentPiece A string representing the selected piece a player wants to move.
     * @param argNewPosition A string representing the new position to which the player wants to move the piece.
     *
     * <p>Algorithm:
     * 1. Transform the new and current positions into numeric coordinates the board can interpret.
     * 2. Move the selected piece to the given new position.
     * 3. Change the original spot on the board to be empty.
     *
     * <p>Note: This function does not return a value; it modifies the board's state directly.
     */

    private void updateBoard(String argCurrentPiece, String argNewPosition) {
        String convertOld = convertCoordinates(argCurrentPiece);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;

        String convertNew = convertCoordinates(argNewPosition);
        int newRow = Integer.parseInt(convertNew) / 10 % 10;
        int newColumn = Integer.parseInt(convertNew) % 10;

        m_board[newRow][newColumn] = m_board[oldRow][oldColumn];
        m_board[oldRow][oldColumn] = '.';
    }

    /**
     * Name: copyBoard
     * Copies the state of one board into another, effectively duplicating the board's state.
     *
     * @param argOriginalBoard Board object from which the state is to be copied.
     *
     * <p>Algorithm:
     * 1. Copy the 2D array representing the original board's state.
     * 2. Copy each position from the original board to the current board.
     *
     * <p>Note: This function does not return a value; it modifies the state of the current board directly.
     */

    public void copyBoard(Board argOriginalBoard) {
        char[][] copiedBoard = argOriginalBoard.getCopyBoard();

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                m_board[i][j] = copiedBoard[i][j];
            }
        }
    }


    /* *********************************************
    Source Code to related to pieces. Converting coordinates or finding pieces
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: findPieces
     * Finds and records the coordinates of all pieces that match a specified color on the board.
     *
     * @param argPieces A vector of strings that will be used to store the coordinates of the pieces.
     * @param argColor A char indicating the current player's color to determine which pieces belong to them.
     *
     * <p>Algorithm:
     * 1. Go through the entire board.
     * 2. Check each position on the board to see if it matches the player's color.
     * 3. If the color matches, add the coordinates to the vector.
     *
     * <p>Note: This function does not return a value; it modifies the vector of strings directly.
     */

    public void findPieces(List<String> argPieces, char argColor) {

        char positionPiece;

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                positionPiece = m_board[j][i];
                if (positionPiece == argColor) {
                    String coordinate = intToString(j, i);
                    argPieces.add(coordinate);
                }
            }
        }


    }

    /**
     * Name: getPieces
     * Calculates the total number of pieces of a specified color on the board. T
     *
     * @param color A char representing the color of the pieces to count on the board.
     * @return The total number of pieces of the given color on the board, expressed as an integer.
     *
     * <p>Algorithm:
     * 1. Declare and initialize a counter for the total pieces.
     * 2. Go through the entire board.
     * 3. Check each position on the board to see if it matches the specified color. If valid, increment the total pieces counter.
     * 4. Once the loop over the board is finished, return the total pieces counted.
     */

    public int getPieces(char color) {
        int totalPieces = 0;

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (m_board[i][j] == color) {
                    totalPieces++;
                }
            }
        }

        return totalPieces;
    }

    /**
     * Name: convertCoordinates
     * Converts chessboard-style coordinates (e.g., "A1") into a numeric string representation.
     *
     * @param argCurrentPiece A string representing the coordinates from the board.
     * @return A string holding the numeric representation of coordinates.
     *
     * <p>Algorithm:
     * 1. Convert a letter (e.g., 'A') to a column index starting at 0 by subtracting 'A' from the first character of the string.
     * 2. To align the number with the board's row indexing, which typically runs from bottom (7) to top (0), subtract the digit from '8' and convert the result to an integer.
     * 3. Combine these two values into one string that represents the numeric coordinates.
     */

    private String convertCoordinates(String argCurrentPiece) {
        //String to hold coordinates for the computer to read
        String convertedCoordinates = "";

        //Convert first character of the coordinates to numbers
        switch (argCurrentPiece.charAt(1)) {
            case '1':
                convertedCoordinates += "7";
                break;
            case '2':
                convertedCoordinates += "6";
                break;
            case '3':
                convertedCoordinates += "5";
                break;
            case '4':
                convertedCoordinates += "4";
                break;
            case '5':
                convertedCoordinates += "3";
                break;
            case '6':
                convertedCoordinates += "2";
                break;
            case '7':
                convertedCoordinates += "1";
                break;
            case '8':
                convertedCoordinates += "0";
                break;
        }

        //Convert Second character of the coordinates to numbers
        switch (argCurrentPiece.charAt(0)) {
            case 'A':
                convertedCoordinates += "0";
                break;
            case 'B':
                convertedCoordinates += "1";
                break;
            case 'C':
                convertedCoordinates += "2";
                break;
            case 'D':
                convertedCoordinates += "3";
                break;
            case 'E':
                convertedCoordinates += "4";
                break;
            case 'F':
                convertedCoordinates += "5";
                break;
            case 'G':
                convertedCoordinates += "6";
                break;
            case 'H':
                convertedCoordinates += "7";
                break;
        }

        return convertedCoordinates;
    }

    /**
     * Name: intToString
     * Converts coordinates into a single string representation.
     *
     * @param x An integer representing the column index.
     * @param y An integer representing the row index.
     * @return A String that combines the column and row indices.
     *
     * <p>Algorithm:
     * 1. Combine the column and row indices into a single string, using a StringBuilder or similar method.
     * 2. Return the combined string.
     */

    public String intToString(int x, int y) {
        String coordinate = "";

        //Convert first character of the coordinates to numbers
        switch (y) {
            case 0:
                coordinate += "A";
                break;
            case 1:
                coordinate += "B";
                break;
            case 2:
                coordinate += "C";
                break;
            case 3:
                coordinate += "D";
                break;
            case 4:
                coordinate += "E";
                break;
            case 5:
                coordinate += "F";
                break;
            case 6:
                coordinate += "G";
                break;
            case 7:
                coordinate += "H";
                break;
        }

        //Convert Second character of the coordinates to numbers
        switch (x) {
            case 0:
                coordinate += "8";
                break;
            case 1:
                coordinate += "7";
                break;
            case 2:
                coordinate += "6";
                break;
            case 3:
                coordinate += "5";
                break;
            case 4:
                coordinate += "4";
                break;
            case 5:
                coordinate += "3";
                break;
            case 6:
                coordinate += "2";
                break;
            case 7:
                coordinate += "1";
                break;
        }


        return coordinate;
    }

    /* *********************************************
    Source Code for board movement/validation for both computer,help, and human input
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: horizontalValidation
     * Validates whether the player is able to make a move along the right diagonal.
     *
     * @param argCurrentPiece A string representing the current piece selected by the human to move.
     * @param argNewPosition A string representing the new position the human wants to potentially move the piece to.
     * @param argPlayer A char indicating the player's color to determine if the board path is blocked by the opposite color.
     * @return A boolean determining if the right diagonal move is possible (true if the move is valid, false otherwise).
     *
     * <p>Algorithm:
     * 1. Convert the current piece to readable information for the board.
     * 2. Get total spaces allowed to move along the horizontal line.
     * 3. Verify that the move stays within the allowed spaces and that no pieces of the opposing color are blocking the path.
     * 4. Check if the piece is able to move along the horizontal line.
     * 5. When a move is valid, update the board and return true. Return false if no moves are found to be valid.
     */
    public boolean horizontalValidation(String argCurrentPiece, String argNewPosition, char argPlayer) {
        // Checking to see if horizontal move is valid
        boolean validation = false;
        // Getting current piece and converting it to numbers the board can read
        String convertOld = convertCoordinates(argCurrentPiece);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;

        //Checking the amount of pieces you can move along this line
        int availableSpaces = 0;

        //Counting all pieces to see spaces avaliable
        for (int i = 0; i <= 7; i++) {
            if (m_board[i][oldColumn] == 'B' || m_board[i][oldColumn] == 'W') {
                availableSpaces++;
            }
        }

        //Converting position wanting to go to
        String convertNew = convertCoordinates(argNewPosition);
        int newRow = Integer.parseInt(convertNew) / 10 % 10;
        int newColumn = Integer.parseInt(convertNew) % 10;

        //Checking Right, going along the right side availablespaces counted.
        for (int i = 1; i <= availableSpaces; i++) {

            //Boundary of the board
            if(oldRow + i > 7) {

                break;

            }

            else {
                //Checking to see if piece is being blocked
                //Currently piece is still able to go along line
                if (i < availableSpaces && (m_board[oldRow + i][oldColumn] == '.' || m_board[oldRow + i][oldColumn] == argPlayer)) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color
                else if (availableSpaces == i && (oldRow + availableSpaces) == newRow && oldColumn == newColumn
                        && (m_board[oldRow + i][oldColumn] == '.' || m_board[oldRow + i][oldColumn] != argPlayer)) {
                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }
                //Block happened
                else {
                    validation = false;
                    break;
                }

            }

        }

        //Piece was valid to be places, entire horizontal is not done yet. Checking left side next.
        if (validation == true) {
            return validation;
        }

        //Checking Left, going along the right side availablespaces counted.
        for (int i = 1; i <= availableSpaces; i++) {

            //Boundary of the board
            if(oldRow - i < 0) {

                break;

            }
            else {
                //Checking to see if piece is being blocked
                if (i < availableSpaces && m_board[oldRow - i][oldColumn] == '.' || m_board[oldRow - i][oldColumn] == argPlayer) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color
                else if (availableSpaces == i && (oldRow - availableSpaces) == newRow && oldColumn == newColumn
                        && (m_board[oldRow - i][oldColumn] == '.' || m_board[oldRow - i][oldColumn] != argPlayer)) {
                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }
                //Block happened
                else {
                    validation = false;
                    break;
                }
            }

        }

        //Left side had legal move.
        if (validation == true) {
            return validation;
        }

        return validation;
    }
    /**
     * Name: verticalValidation
     * Validates whether the player is able to make a move along the right diagonal.
     *
     * @param argCurrentPiece A string representing the current piece selected by the human to move.
     * @param argNewPosition A string representing the new position the human wants to potentially move the piece to.
     * @param argPlayer A char indicating the player's color to determine if the board path is blocked by the opposite color.
     * @return A boolean determining if the right diagonal move is possible (true if the move is valid, false otherwise).
     *
     * <p>Algorithm:
     * 1. Convert the current piece to readable information for the board.
     * 2. Get total spaces allowed to move along the vertical line.
     * 3. Verify that the move stays within the allowed spaces and that no pieces of the opposing color are blocking the path.
     * 4. Check if the piece is able to move along the vertical line.
     * 5. When a move is valid, update the board and return true. Return false if no moves are found to be valid.
     */
    public boolean verticalValidation(String argCurrentPiece, String argNewPosition, char argPlayer) {

        // Checking to see if horizontal move is valid
        boolean validation = false;
        //Converting the current piece into readable information the board can assess
        String convertOld = convertCoordinates(argCurrentPiece);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;

        //The total spaces the piece can move is initialized and declared for error detection.
        int availableSpaces = 0;

        //Getting the availabe spaces the piece is allowed to move
        for (int i = 0; i <= 7; i++) {
            if (m_board[oldRow][i] == 'B' || m_board[oldRow][i] == 'W') {
                availableSpaces++;
            }
        }

        //Converting the new position into information the board is able to read and assess.
        String convertNew = convertCoordinates(argNewPosition);
        int newRow = Integer.parseInt(convertNew) / 10 % 10;
        int newColumn = Integer.parseInt(convertNew) % 10;

        //Checking downwards
        //Checking boundary of the board
        for (int i = 1; i <= availableSpaces; i++) {

            if(oldColumn + i > 7) {

                break;

            }

            else {

                //Checking to see if piece is being blocked.
                if (i < availableSpaces && (m_board[oldRow][oldColumn + i] == '.' || m_board[oldRow][oldColumn + i] == argPlayer)) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color
                else if (availableSpaces == i && (oldColumn + availableSpaces) == newColumn && oldRow == newRow
                        && (m_board[oldRow][oldColumn + i] == '.' || m_board[oldRow][oldColumn + i] != argPlayer)) {
                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }
                //Block happened
                else {
                    validation = false;
                    break;
                }
            }

        }

        //Piece was allowed to move horizontally downwards.
        if (validation == true) {
            return validation;
        }

        //Checking upwards
        //Checking for boundary of the board
        for (int i = 1; i <= availableSpaces; i++) {

            if(oldColumn - i < 0) {

                break;

            }

            else {
                //Checking to see if piece is being blocked
                if (i < availableSpaces && m_board[oldRow][oldColumn - i] == '.' || m_board[oldRow][oldColumn - i] == argPlayer) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color
                else if (availableSpaces == i && (oldColumn - availableSpaces) == newColumn && oldRow == newRow
                        && (m_board[oldRow][oldColumn - i] == '.' || m_board[oldRow][oldColumn - i] != argPlayer)) {
                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }
                //Block happened
                else {
                    validation = false;
                    break;
                }
            }

        }

        return validation;
    }
    /**
     * Name: rightDiagonalValidation
     * Validates whether the player is able to make a move along the right diagonal.
     *
     * @param argCurrentPiece A string representing the current piece selected by the human to move.
     * @param argNewPosition A string representing the new position the human wants to potentially move the piece to.
     * @param argPlayer A char indicating the player's color to determine if the board path is blocked by the opposite color.
     * @return A boolean determining if the right diagonal move is possible (true if the move is valid, false otherwise).
     *
     * <p>Algorithm:
     * 1. Convert the current piece to readable information for the board.
     * 2. Get total spaces allowed to move along the right diagonal.
     * 3. Verify that the move stays within the allowed spaces and that no pieces of the opposing color are blocking the path.
     * 4. Check if the piece is able to move along the right diagonal.
     * 5. When a move is valid, update the board and return true. Return false if no moves are found to be valid.
     */

    public boolean rightDiagonalValidation(String argCurrentPiece, String argNewPosition, char argPlayer) {
        //Checking to see if move is valid
        boolean validation = false;
        //Converting current piece location into readable board information for assessment.
        String convertOld = convertCoordinates(argCurrentPiece);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;

        //The total spaces the piece can move is initialized and declared for error detection.
        int availableSpaces = 0;

        //Getting spaces able to move

        //Spaces from top right
        //Finding all colored pieces, if found increment availableSpaces to move.
        for (int i = 0; i <= 7; i++) {
            if ((oldColumn - i >= 0) && (oldRow + i <= 7)) {
                if (m_board[oldRow + i][oldColumn - i] == 'B' || m_board[oldRow + i][oldColumn - i] == 'W') {
                    availableSpaces++;
                }
            }
            else {
                break;
            }
        }

        //Spaces from bottom Left
        //Finding all colored pieces, if found increment availableSpaces to move.
        for (int i = 1; i <= 7; i++) {
            if ((oldRow - i >= 0) && (oldColumn + i <=7 )) {
                if (m_board[oldRow - i][oldColumn + i] == 'B' || m_board[oldRow - i][oldColumn + i] == 'W') {
                    availableSpaces++;
                }
            }
            else {
                break;
            }
        }


        //Convert new position to move to into readable information the board can assess.
        String convertNew = convertCoordinates(argNewPosition);
        int newRow = Integer.parseInt(convertNew) / 10 % 10;
        int newColumn = Integer.parseInt(convertNew) % 10;

        //Checking top right
        for (int i = 1; i <= availableSpaces; i++) {

            //Check boundaries
            if ((oldColumn - i >= 0) && (oldRow + i <= 7)) {
                //Checking to see if piece is being blocked, if not continues through loop.
                if ((i < availableSpaces) && (m_board[oldRow + i][oldColumn - i] == '.' || m_board[oldRow + i][oldColumn - i] == argPlayer)) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color
                else if ((availableSpaces == i) && ((oldColumn - availableSpaces) == newColumn) && (oldRow + availableSpaces == newRow)
                        && (m_board[oldRow + i][oldColumn - i] == '.' || m_board[oldRow + i][oldColumn - i] != argPlayer)) {
                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }
                //Block happened
                else {
                    validation = false;
                    break;
                }
            }
            else {
                break;

            }

        }

        //Top right was valid to place piece.
        if (validation == true) {
            return validation;
        }

        //Checking bottom left
        for (int i = 1; i <= availableSpaces; i++) {

            //Check boundaries
            if ((oldRow - i >= 0) && (oldColumn + i <=7 )) {
                //Checking to see if piece is being blocked, if not continues through loop.
                if ((i < availableSpaces) && (m_board[oldRow - i][oldColumn + i] == '.' || m_board[oldRow - i][oldColumn + i] == argPlayer)) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color

                else if ((availableSpaces == i) && ((oldColumn + availableSpaces) == newColumn) && (oldRow - availableSpaces == newRow)
                        && (m_board[oldRow - i][oldColumn + i] == '.' || m_board[oldRow - i][oldColumn + i] != argPlayer)) {
                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }

                //Block happened
                else {
                    validation = false;
                    break;
                }
            }

            else{
                break;
            }
        }


        return validation;
    }

    /**
     * Name: leftDiagonalValidation
     * Validates whether the player is able to make a move along the left diagonal.
     *
     * @param argCurrentPiece A string representing the current piece selected by the human to move.
     * @param argNewPosition A string representing the new position the human wants to potentially move the piece to.
     * @param argPlayer A char indicating the player's color to determine if the board path is blocked by the opposite color.
     * @return A boolean determining if the left diagonal move is possible (true if the move is valid, false otherwise).
     *
     * <p>Algorithm:
     * 1. Convert the current piece to readable information for the board.
     * 2. Get total spaces allowed to move along the left diagonal.
     * 3. Verify that the move stays within the allowed spaces and that no pieces of the opposing color are blocking the path.
     * 4. Check if the piece is able to move along the left diagonal.
     * 5. When a move is valid, update the board and return true. Return false if no moves are found to be valid.
     */

    public boolean leftDiagonalValidation(String argCurrentPiece, String argNewPosition, char argPlayer) {
        //Validation flag to check if the piece iable to move along the left diagonal line.
        boolean validation = false;
        //Convert current piece coordinates into readable information for the board to assess.
        String convertOld = convertCoordinates(argCurrentPiece);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;

        //The total spaces the piece can move are initialized and declared for error detection.
        int availableSpaces = 0;

        //Spaces from top right
        //Counting all the colored pieces to increment available space.
        for (int i = 0; i <= 7; i++) {
            if ((oldColumn - i >= 0) && (oldRow - i >= 0)) {
                if (m_board[oldRow - i][oldColumn - i] == 'B' || m_board[oldRow - i][oldColumn - i] == 'W') {
                    availableSpaces++;
                }
            }
            else {
                break;
            }
        }

        //Spaces from bottom Left
        //Counting all the colored pieces to increment available space.
        for (int i = 1; i <= 7; i++) {
            if ((oldColumn + i <= 7) && (oldRow + i <= 7)) {
                if (m_board[oldRow + i][oldColumn + i] == 'B' || m_board[oldRow + i][oldColumn + i] == 'W') {
                    availableSpaces++;
                }
            }
            else {
                break;
            }
        }

        //Converting position to readable information the board can assess.
        String convertNew = convertCoordinates(argNewPosition);
        int newRow = Integer.parseInt(convertNew) / 10 % 10;
        int newColumn = Integer.parseInt(convertNew) % 10;

        //Checking top left

        for (int i = 1; i <= availableSpaces; i++) {
            //Checking to see if piece is being blocked
            if(((oldColumn - i >= 0) && (oldRow - i >= 0))) {

                if ((i < availableSpaces) && (m_board[oldRow - i][oldColumn - i] == '.' || m_board[oldRow - i][oldColumn - i] == argPlayer)) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color
                else if ((availableSpaces == i) && ((oldColumn - availableSpaces == newColumn) && (oldRow - availableSpaces == newRow))
                        && (m_board[oldRow - i][oldColumn - i] == '.' || m_board[oldRow - i][oldColumn - i] != argPlayer)) {

                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }

                else {
                    //Block happened
                    validation = false;
                    break;
                }
            }
            else {
                break;
            }


        }

        //Checking bottom right
        if (validation) {
            return validation;
        }

        for (int i = 1; i <= availableSpaces; i++) {

            //Check for boundaries in the board
            if ((oldColumn + i <= 7) && (oldRow + i <= 7)) {
                //Checking to see if piece is being blocked
                if ((i < availableSpaces) && (m_board[oldRow + i][oldColumn + i] == '.' || m_board[oldRow + i][oldColumn + i] == argPlayer)) {

                }
                //Check max space to see if the piece is placeable. Check to see max loop is met,
                //the piece of current position matches the position of the new position, And sees
                //if the final piece is empty or able to remove oppsite color
                else if ((availableSpaces == i) && ((oldColumn + availableSpaces) == newColumn) && (oldRow + availableSpaces == newRow)
                        && (m_board[oldRow + i][oldColumn + i] == '.' || m_board[oldRow + i][oldColumn + i] != argPlayer)) {

                    updateBoard(argCurrentPiece, argNewPosition);
                    validation = true;
                }
                //Block happened
                else {

                    validation = false;
                    break;
                }
            }
            else {

                break;

            }
        }

        return validation;
    }

    /**
     * Name: winCondition
     * Checks if the current player has won by determining if all pieces of their color are connected.
     *
     * @param colorPlayer A char representing the player's color to identify similar pieces on the board.
     * @return A boolean indicating whether all pieces of the player's color are connected (true if connected, false otherwise).
     *
     * <p>Algorithm:
     * 1. Locate a piece on the board that is similar in color to the player's color.
     * 2. From this piece, use a recursive search algorithm to count all connected pieces of the same color.
     * 3. Compare the total number of pieces of that color on the board to the total number of connected pieces.
     * 4. Return true if every piece is connected; return false if not.
     * 5. After verifying, clear the visited parts tracker to prepare for another win condition check.
     */
    public boolean winCondition(char colorPlayer) {
        // Getting total number of pieces for the current player
        int numPieces = getPieces(colorPlayer);
        // Storing row the similar colored piece was found
        int foundRow = 0;
        // Storing column the similar colored piece was found
        int foundCol = 0;
        // Looking for piece for similar colored piece
        boolean foundPiece = false;

        // Find similar colored piece on board
        for (int i = 7; i >= 0; i--) {
            for (int j = 7; j >= 0; j--) {
                if (m_board[j][i] == colorPlayer) {
                    foundRow = j;
                    foundCol = i;
                    foundPiece = true;
                    break;
                }
            }
            // Piece found
            if (foundPiece) {
                break;
            }
        }

        // Algo to find if all total pieces make one group
        if (checkAroundPiece(foundRow, foundCol)) {
            return true;
        } else {
            visitedPieces.clear();
            return false;
        }
    }
    /**
     * Name: checkAroundPiece
     * Aids in determining the win condition by recursively checking around a given piece to see if all pieces of the same color are connected.
     *
     * @param col An integer representing the column where the current player's colored piece is found.
     * @param row An integer representing the row where the current player's colored piece is found.
     * @return A boolean value that indicates whether or not all the current player's pieces are connected (true if they are, false otherwise).
     *
     * <p>Algorithm:
     * 1. Assign the color of the current piece.
     * 2. Perform a 3x3 grid search around the current piece to locate more pieces of the same color.
     * 3. For each piece found, check if it has been visited; if not, mark it as visited and recursively check around it.
     * 4. Compare the number of connected pieces counted to the total number of pieces of that color on the board.
     * 5. Return true if all the total and counted pieces are the same; if not, return false.
     */

    boolean checkAroundPiece(int col, int row) {
        // Assigning current color of player
        char currentColor = m_board[col][row];
        // Assigning total pieces of player
        int totalPieces = getPieces(currentColor);

        // Making a 3x3 grid around the current piece selected
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((col + j <= 7) && (col + j >= 0) && (row + i <= 7) && (row + i >= 0)) {
                    if (m_board[col + j][row + i] == currentColor && checkIfVisited(col + j, row + i)) {
                        // Check next piece found
                        checkAroundPiece(col + j, row + i);
                    }
                }
            }
        }

        // Total pieces of current color is equal to counted neighboring pieces, meaning win condition was true
        if (totalPieces == visitedPieces.size()) {
            return true;
        }

        return false;
    }
    /**
     * Name: checkIfVisited
     * Helps in determining the winning condition by checking if a piece at a given position has already been visited in the current group.
     *
     * @param col An integer representing the column of the current piece being checked.
     * @param row An integer representing the row of the current piece being checked.
     * @return A boolean value representing whether the piece has been visited (true if visited, false otherwise).
     *
     * <p>Algorithm:
     * 1. Convert the coordinates of the current piece into a string.
     * 2. Check if the coordinate has already been visited.
     * 3. Increase the countedPieces and add the piece to the visited list if it hasn't been visited yet.
     * 4. Return true if the piece was not visited; return false if it was.
     */

    boolean checkIfVisited(int col, int row) {
        // Converting coordinates into string
        String check = intToString(col, row);

        for (String visitedPiece : visitedPieces) {
            // Did the current piece found around been found before? if so return false
            if (check.equals(visitedPiece)) {
                return false;
            }
        }
        // Current piece selected was found before, add to visited pieces and increment countedPieces
        visitedPieces.add(check);
        return true;
    }
    /**
     * Name: movePiece
     * Moves a piece on the board for both human and computer players.
     *
     * @param currentPiece A string representing the current piece that is to be moved.
     * @param newPosition A string indicating the desired new position for the piece.
     * @param argPlayer A char representing the color of the player, which determines if the piece matches the player's color.
     * @param humanCheck A boolean indicating whether the player is human or a computer.
     *
     * <p>Algorithm:
     * 1. Validate the horizontal, vertical, and diagonal moves. If a move is not possible, and if the player is human, ask for correct input.
     * 2. If any validation is successful, end the loop and move the piece.
     * 3. If the player is human and none of the validations succeeded, ask for a new position and repeat the validation procedure.
     *
     * <p>Note: This function does not return any value as its purpose is to execute a move and potentially interact with the player for input.
     */
    public void movePiece(String currentPiece, String newPosition, char argPlayer, boolean humanCheck) {

        //Logic to see if the selected Position is allowable
        do {
            if (horizontalValidation(currentPiece, newPosition, argPlayer)) {
                break;
            } else if (verticalValidation(currentPiece, newPosition, argPlayer)) {
                break;
            } else if (rightDiagonalValidation(currentPiece, newPosition, argPlayer)) {
                break;
            } else if (leftDiagonalValidation(currentPiece, newPosition, argPlayer)) {
                break;
            }

            if (humanCheck) {
                System.out.print("Enter a proper new position: ");
                newPosition = System.console().readLine();
            } else {
                break;
            }

        } while (true);
    }

    /**
     * Name: blockingVertical
     * Identifies all possible valid vertical positions for the computer's piece from its current position.
     *
     * @param argPieces A string representing the current position of the piece on the board.
     * @param argNewpos A vector of strings. This vector will be updated to include all coordinates where the piece can potentially move vertically.
     *
     * <p>Algorithm:
     * 1. Convert the piece's coordinates into readable board information to assess potential moves.
     * 2. Get the color of the current player to determine the movement direction and restrictions.
     * 3. Find available spaces a piece can move by counting all the colored pieces along the vertical line.
     * 4. Check if the piece is blocked. If not, add the position to the potential moves.
     * 5. For human readability, convert valid board indices back to alphanumeric coordinates and store them in argNewpos.
     *
     * <p>Note: This function does not return any value as its purpose is to modify the argNewpos vector directly.
     */

    public void blockingVertical(String argPieces, List<String> argNewpos) {
        // vertical move
        // Convert current piece into readable information the board can assess
        String convertOld = convertCoordinates(argPieces);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;
        // Getting color of piece
        char colorPlayer = m_board[oldRow][oldColumn];

        // The total spaces the piece can move are initialized and declared for error reduction.
        int availableSpaces = 0;

        // Checking for spaces to move
        // Finding all colored pieces, if found available spaces increments.
        for (int i = 0; i <= 7; i++) {
            if (m_board[oldRow][i] == 'B' || m_board[oldRow][i] == 'W') {
                availableSpaces++;
            }
        }

        // Checking downwards verically

        for (int i = 1; i <= availableSpaces; i++) {

            if(oldColumn + i > 7) {

                break;

            }

            else {

                if (i < availableSpaces && (m_board[oldRow][i + oldColumn] == '.' || m_board[oldRow][i + oldColumn] == colorPlayer)) {

                }
                // Comparing last piece from total available space. If the piece is empty or not its color it can land. Position is also set up with a limit to not exeed limit of board
                else if (i == availableSpaces && (m_board[oldRow][i + oldColumn] == '.' || m_board[oldRow][i + oldColumn] != colorPlayer)
                        && (i + oldColumn) <= 7) {
                    // Convert location back to readable information for humans.
                    String converttostring = intToString((oldRow), (oldColumn + i));
                    // Add position to list.
                    argNewpos.add(converttostring);
                }
                // Block happened
                else {

                    break;
                }
            }

        }

        for (int i = 1; i <= availableSpaces; i++) {

            if(oldColumn - i < 0) {

                break;

            }

            else {


                if (i < availableSpaces && (m_board[oldRow][oldColumn - i] == '.' || m_board[oldRow][oldColumn - i] == colorPlayer)) {

                }
                // Comparing last piece from total available space. If the piece is empty or not its color it can land. Position is also set up with a limit to not exeed limit of board
                else if (i == availableSpaces && (m_board[oldRow][oldColumn - i] == '.' || m_board[oldRow][oldColumn - i] != colorPlayer)
                        && (oldColumn - i) >= 0) {
                    String converttostring = intToString((oldRow), (oldColumn - i));
                    argNewpos.add(converttostring);
                }
                // Block happened
                else {

                    break;
                }
            }

        }

    }

    /**
     * Name: blockingVertical
     * Identifies all possible valid positions for the computer's piece to move horizontally from its current position.
     *
     * @param argPieces A string representing the current position of the piece on the board.
     * @param argNewpos A vector of strings. This vector will be updated to include all coordinates where the piece can potentially move horizontally.
     *
     * <p>Algorithm:
     * 1. Convert the piece's coordinates into readable board information to assess potential moves.
     * 2. Get the color of the current player to determine the movement direction and restrictions.
     * 3. Find available spaces a piece can move by counting all the colored pieces along the horizontal line.
     * 4. Check if the piece is blocked. If not, add the position to the potential moves.
     * 5. For human readability, convert valid board indices back to alphanumeric coordinates and store them in argNewpos.
     *
     * <p>Note: This function does not return any value as its purpose is to modify the argNewpos vector directly.
     */

    public void blockingHorizontal(String argPieces, List<String> argNewpos) {
        // horizontal move
        // Convert piece coordinates to readable information board can assess.
        String convertOld = convertCoordinates(argPieces);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;
        // Getting current players color through piece selected.
        char colorPlayer = m_board[oldRow][oldColumn];



        int availableSpaces = 0;


        for (int i = 0; i <= 7; i++) {
            if (m_board[i][oldColumn] == 'B' || m_board[i][oldColumn] == 'W') {
                availableSpaces++;
            }
        }

        for (int i = 1; i <= availableSpaces; i++) {
            if(oldRow + i > 7) {

                break;

            }
            else {
                if (i < availableSpaces && (m_board[oldRow + i][oldColumn] == '.' || m_board[oldRow + i][oldColumn] == colorPlayer)) {

                }

                else if (i == availableSpaces && (m_board[oldRow + i][oldColumn] == '.' || m_board[oldRow + i][oldColumn] != colorPlayer)
                        && (oldRow + i) <= 7) {

                    String converttostring = intToString((oldRow + i), oldColumn);
                    argNewpos.add(converttostring);
                }

                else {

                    break;
                }
            }

        }

        for (int i = 1; i <= availableSpaces; i++) {

            if(oldRow - i < 0) {

                break;

            }
            else {
                if (i < availableSpaces && (m_board[oldRow - i][oldColumn] == '.' || m_board[oldRow - i][oldColumn] == colorPlayer)) {

                }

                else if (i == availableSpaces && (m_board[oldRow - i][oldColumn] == '.' || m_board[oldRow - i][oldColumn] != colorPlayer)
                        && (oldRow - i) >= 0) {

                    String converttostring = intToString((oldRow - i), oldColumn);
                    argNewpos.add(converttostring);
                }
                // Block happened
                else {

                    break;
                }
            }

        }


    }
    /**
     * Name: blockLeftLine
     * Identifies all possible valid positions for the computer's piece to move along the left diagonal line from its current position.
     *
     * @param argPieces A string representing the current position of the piece on the board.
     * @param argNewpos A vector of strings. This vector will be updated to include all coordinates where the piece can potentially move along the left diagonal line.
     *
     * <p>Algorithm:
     * 1. Convert the piece's coordinates into readable board information to assess potential moves.
     * 2. Get the color of the current player to determine the movement direction and restrictions.
     * 3. Find available spaces a piece can move by counting all the colored pieces along the left diagonal line.
     * 4. Check if the piece is blocked. If not, add the position to the potential moves.
     * 5. For human readability, convert valid board indices back to alphanumeric coordinates and store them in argNewpos.
     *
     * <p>Note: This function does not return any value as its purpose is to modify the argNewpos vector directly.
     */

    public void blockingLeftLine(String argPieces, List<String> argNewpos) {
        // Converting current piece to readable information for the board to access
        String convertOld = convertCoordinates(argPieces);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;
        // Getting current color of the player.
        char colorPlayer = m_board[oldRow][oldColumn];

        // The total spaces the piece can move are initialized and declared for error reduction.
        int availableSpaces = 0;

        // Getting spaces able to move

        // Spaces from top right
        // Getting all colored pieces along the left line in order to see total amount of spaces piece can move
        for (int i = 0; i <= 7; i++) {
            if ((oldColumn - i >= 0) && (oldRow - i >= 0)) {
                if (m_board[oldRow - i][oldColumn - i] == 'B' || m_board[oldRow - i][oldColumn - i] == 'W') {
                    availableSpaces++;
                }
            } else {
                break;
            }
        }


        for (int i = 1; i <= 7; i++) {
            if ((oldColumn + i <= 7) && (oldRow + i <= 7)) {
                if (m_board[oldRow + i][oldColumn + i] == 'B' || m_board[oldRow + i][oldColumn + i] == 'W') {
                    availableSpaces++;
                }
            } else {
                break;
            }
        }

        // Checking top left

        for (int i = 1; i <= availableSpaces; i++) {
            // Checking to see if piece is being blocked

            if(((oldColumn - i >= 0) && (oldRow - i >= 0))) {

                if ((i < availableSpaces) && (m_board[oldRow - i][oldColumn - i] == '.' || m_board[oldRow - i][oldColumn - i] == colorPlayer)) {

                }

                else if ((availableSpaces == i) && (m_board[oldRow - i][oldColumn - i] == '.' || m_board[oldRow - i][oldColumn - i] != colorPlayer)
                        && (oldRow - i) >= 0 && (oldColumn - i) >= 0) {
                    // Converting position back into readable human text
                    String converttostring = intToString((oldRow - i), (oldColumn - i));
                    argNewpos.add(converttostring);
                }
                // Block happened
                else {

                    break;
                }
            }
            else {
                break;
            }

        }

        // Checking bottom right

        for (int i = 1; i <= availableSpaces; i++) {

            if ((oldColumn + i <= 7) && (oldRow + i <= 7)) {

                // Checking to see if piece is being blocked
                if ((i < availableSpaces) && (m_board[oldRow + i][oldColumn + i] == '.' || m_board[oldRow + i][oldColumn + i] == colorPlayer)) {

                }

                else if ((availableSpaces == i) && (m_board[oldRow + i][oldColumn + i] == '.' || m_board[oldRow + i][oldColumn + i] != colorPlayer)
                        && (oldRow + i) <= 7 && (oldColumn + i) <= 7) {
                    // Converting human text back into readable text.
                    String converttostring = intToString((oldRow + i), (oldColumn + i));
                    argNewpos.add(converttostring);

                }
                // Block happened
                else {

                    break;
                }
            }
            else {
                break;
            }

        }

    }

    /**
     * Name: blockingRightline
     * Identifies all possible valid positions for the computer's piece to move along the right diagonal line from its current position.
     *
     * @param argPieces A string representing the current position of the piece on the board.
     * @param argNewpos A vector of strings. This vector will be updated to include all coordinates where the piece can potentially move along the right diagonal line.
     *
     * <p>Algorithm:
     * 1. Convert the piece's coordinates into readable board information to assess potential moves.
     * 2. Get the color of the current player to determine the movement direction and restrictions.
     * 3. Find available spaces a piece can move by counting all the colored pieces along the right diagonal line.
     * 4. Check if the piece is blocked. If not, add the position to the potential moves.
     * 5. For human readability, convert valid board indices back to alphanumeric coordinates and store them in argNewpos.
     *
     * <p>Note: This function does not return any value as its purpose is to modify the argNewpos vector directly.
     */

    public void blockingRightline(String argPieces, List<String> argNewpos) {
        // Convert current position into readable information for the board to assess
        String convertOld = convertCoordinates(argPieces);
        int oldRow = Integer.parseInt(convertOld) / 10 % 10;
        int oldColumn = Integer.parseInt(convertOld) % 10;
        // Get current players color
        char colorPlayer = m_board[oldRow][oldColumn];

        // The total spaces the piece can move are initialized and declared for error reduction.
        int availableSpaces = 0;

        // Spaces from top right
        // Find all colored pieces, if found increment available spaces.
        for (int i = 0; i <= 7; i++) {
            if ((oldColumn - i >= 0) && (oldRow + i <= 7)) {
                if (m_board[oldRow + i][oldColumn - i] == 'B' || m_board[oldRow + i][oldColumn - i] == 'W') {
                    availableSpaces++;
                }
            } else {
                break;
            }
        }

        // Spaces from bottom Left
        // Find all colored pieces, if found increment available spaces.
        for (int i = 1; i <= 7; i++) {
            if ((oldRow - i >= 0) && (oldColumn + i <= 7)) {
                if (m_board[oldRow - i][oldColumn + i] == 'B' || m_board[oldRow - i][oldColumn + i] == 'W') {
                    availableSpaces++;
                }
            } else {
                break;
            }
        }

        // Checking top left

        for (int i = 1; i <= availableSpaces; i++) {


            if ((oldColumn - i >= 0) && (oldRow + i <= 7)) {
                // Checking to see if piece is being blocked
                if ((i < availableSpaces) && (m_board[oldRow + i][oldColumn - i] == '.' || m_board[oldRow + i][oldColumn - i] == colorPlayer)) {

                }
                // Check max space to see if the piece is placeable. Check to see max loop is met,
                // the piece of current position matches the position of the new position, And sees
                // if the final piece is empty or able to remove oppsite color
                else if ((availableSpaces == i) && (m_board[oldRow + i][oldColumn - i] == '.' || m_board[oldRow + i][oldColumn - i] != colorPlayer)
                        && (oldRow + i) <= 7 && (oldColumn - i) >= 0) {
                    // Converting position back into readable human text
                    String converttostring = intToString((oldRow + i), (oldColumn - i));
                    argNewpos.add(converttostring);
                }
                // Block happened
                else {

                    break;
                }
            }
            else {
                break;
            }

        }

        // Checking bottom right

        for (int i = 1; i <= availableSpaces; i++) {

            if ((oldRow - i >= 0) && (oldColumn + i <= 7)) {

                if ((i < availableSpaces) && (m_board[oldRow - i][oldColumn + i] == '.' || m_board[oldRow - i][oldColumn + i] == colorPlayer)) {

                }

                else if ((availableSpaces == i) && (m_board[oldRow - i][oldColumn + i] == '.' || m_board[oldRow - i][oldColumn + i] != colorPlayer)
                        && (oldRow - i) >= 0 && (oldColumn + i) <= 7) {
                    // Converting position back into readable human text
                    String converttostring = intToString((oldRow - i), (oldColumn + i));
                    argNewpos.add(converttostring);
                }
                // Block happened
                else {

                    break;
                }
            }
            else {
                break;
            }

        }

    }

    /**
     * Name: checkPosition
     * Gives all the possible positions for a given piece.
     *
     * @param argPieces A string representing the coordinates of the current piece selected.
     * @param argNewpos A vector of strings that will be used to store the new possible positions. This vector is not edited within the function.
     *
     * <p>Algorithm:
     * 1. Add all legal vertical moves to argNewpos by calling blockingVertical.
     * 2. Add all legal horizontal moves to argNewpos by calling blockingHorizontal.
     * 3. Add all legal left diagonal moves to argNewpos by calling blockingLeftLine.
     * 4. Add all legal right diagonal moves to argNewpos by calling blockingRightLine.
     *
     * <p>Note: This function does not return any value as its purpose is to modify the argNewpos vector directly.
     */

    public void checkPosition(String argPieces, List<String> argNewpos) {

        blockingHorizontal(argPieces, argNewpos);
        blockingVertical(argPieces, argNewpos);
        blockingRightline(argPieces, argNewpos);
        blockingLeftLine(argPieces, argNewpos);

    }

    /**
     * Name: blockingstratCheck
     * Purpose is for blocking strategy. Identifies all blocking positions around the edge of the board based on the opponent's color.
     *
     * @param color The color of the opponent's pieces to be blocked, represented as a char.
     * @return A vector of strings containing all locations where a potential block can happen.
     *
     * <p>Algorithm:
     * 1. Iterate through each side of the board (left, top, right, bottom).
     * 2. Verify whether every location on these sides has an opponent's piece in it (not the color of the current player or empty).
     * 3. Transform these spots' board indices into readable coordinates and append them to the vector.
     */

    public List<String> blockingstratCheck(char color) {
        List<String> tempVec = new ArrayList<>();

        // 7 max amount of location the board can go through
        for (int i = 0; i <= 7; i++) { // left side of board
            if (m_board[0][i] != color && m_board[0][i] != '.') {
                tempVec.add(intToString(1, i));
            }
        }

        // 7 max amount of location the board can go through
        for (int i = 0; i <= 7; i++) { // Top of board
            if (m_board[i][0] != color && m_board[i][0] != '.') {
                tempVec.add(intToString(i, 1));
            }
        }

        // 7 max amount of location the board can go through
        for (int i = 0; i <= 7; i++) { // Right side of board
            if (m_board[7][i] != color && m_board[7][i] != '.') {
                tempVec.add(intToString(6, i));
            }
        }

        // 7 max amount of location the board can go through
        for (int i = 0; i <= 7; i++) { // Bottom of board
            if (m_board[i][7] != color && m_board[i][7] != '.') {
                tempVec.add(intToString(i, 6));
            }
        }

        return tempVec;
    }


}



