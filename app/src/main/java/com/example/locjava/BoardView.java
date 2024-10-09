package com.example.locjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;
import android.widget.TextView;

public class BoardView extends AppCompatActivity {

    private Round currRound = new Round();
    private Board mainBoard = new Board();
    private Player player1 = new Human();
    private Player player2 = new Computer();
    private Player currentPlayer = null;
    private Player[] playerList = new Player[2];
    Tournament tournament = new Tournament();
    private String log = "";
    private int clicks = 0;
    private boolean reprint = false;
    private Button firstClickedButton = null;
    private Button secondClickedButton = null;

    /**
     * Name: onCreate
     * Initializes the activity, sets up the user interface, and prepares game components based previous selection from main activity.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     *
     * <p>Algorithm:
     * 1. Set the content view to the activity's layout.
     * 2. Retrieve UI components like TableLayout for pieces and ConstraintLayout for controls.
     * 3. Obtain the Intent that started this activity and extract necessary information from it.
     * 4. If information is provided, disable interaction buttons, deserialize game data, hide the coin layout,
     *    and make the control layout visible. Also repopulate entire game state.
     * 5. Dynamically create game pieces and display them on the layout.
     *
      */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadgame);

        TableLayout piecesLayout = findViewById(R.id.piecesLayout);
        ConstraintLayout coinLayout = findViewById(R.id.coinflipLayout);
        ConstraintLayout controlLayout = findViewById(R.id.controlLayout);

        Intent i = getIntent();
        String information = i.getStringExtra("Information");
        TextView log = findViewById(R.id.textLog);

        if (information != null && !information.isEmpty()) {
            Button btnHead = findViewById(R.id.Tails);
            Button btnTail = findViewById(R.id.Heads);

            btnTail.setEnabled(false);
            btnHead.setEnabled(false);

            serialization(mainBoard, playerList, currentPlayer, information);
            coinLayout.setVisibility(View.INVISIBLE);
            controlLayout.setVisibility(View.VISIBLE);
            createPieces(piecesLayout);
            piecesLayout.setVisibility(View.VISIBLE);

        }

    }

    /**
     * Name: coinInput
     * assigns the currentplayer and setups all the rounds interface.
     *
     * @param View a view class reference buttons heads and tails.
     * <p>Algorithm
     *  1. Get usersinput for button choosen in heads or tails
     *  2. Result results if the user is correct with the current player
     *  3. Disable all coinflip interface
     *  4. Make board and controls visible.
     */
    /* *********************************************
    Source Code to help help setup/guide the game
    ********************************************* */
    // List all the relevant functions here

    public void coinInput(View View) {
        // Get buttons from the XML file
        Button headsBtn = findViewById(R.id.Heads);
        Button tailsBtn = findViewById(R.id.Tails);
        Button selectedBtn = (Button) View;
        // Get button clicked
        String textSelected = selectedBtn.getText().toString();
        int input;
        //Text to display current player when coin flip is decided
        TextView text = findViewById(R.id.textCurrentPlayer);

        //Check selectedbtns name to figure out what the user choose
        if (textSelected.equals("Tails")) {
            //input equals 0
            input = 0;
        } else {
            //input equals 1
            input = 1;
        }

        //Give user input to the round logic to see get results from coinflip
        currentPlayer = currRound.newCoinflip(player1, player2, input);

        //If current player is returned is human first player is human
        if (currentPlayer.isHuman()) {
            text.setText("Turn: Human");
            text.setTextColor(Color.GREEN);
            playerList[0] = player1;
            playerList[1] = player2;
        } else {
            text.setText("Turn: Computer");
            text.setTextColor(Color.WHITE);
            playerList[0] = player1;
            playerList[1] = player2;
        }

        //Disable buttons for coinlfip
        headsBtn.setEnabled(false);
        tailsBtn.setEnabled(false);

        //Get Tablelayout to display board
        TableLayout board = (TableLayout) findViewById(R.id.boardLayout);
        TableLayout pieces = (TableLayout) findViewById(R.id.piecesLayout);

        //Make the board visible
        board.setVisibility(View.VISIBLE);
        pieces.setVisibility(View.VISIBLE);

        //Make controls enable for movement and computer
        ConstraintLayout controlLayout = findViewById(R.id.controlLayout);
        controlLayout.setVisibility(View.VISIBLE);

        //create the board.
        createPieces(pieces);
    }

    /**
     * Name: helpDisplay
     * Display best strategy the player should take and display all possible moves.
     *
     * @param View, Object View, used to display strategy referencing help button.
     *
     * <p>Algorithm
     *  1. initialize display text
     *  2. Find best strategy through player class and display it
     *  3. Find all pieces
     *  4. Fina all moves for pieces
     *  5. Store information in allpossibilities
     *  6. Display allpossibilities in log text
     */
    public void helpDisplay(View View) {

        //Text to display the help dialouge
        TextView displaytext = findViewById(R.id.txtDisplay);

        //Check to see if human and will give best possible strategy found.
        if (currentPlayer.isHuman()) {
            currentPlayer.runStrategies(mainBoard, currentPlayer, currentPlayer.getNextPlayer(currentPlayer,playerList));
            String helptext = currentPlayer.pickStrategies();

            displaytext.setTextColor(Color.YELLOW);
            displaytext.setText("Help: " + helptext);

        }

        //Find all pieces and store them
        List<String> allpieces = new ArrayList<>();
        mainBoard.findPieces(allpieces,currentPlayer.getColor());
        String allpossibilities = "";

        //Loop through all possible pieces
        for(int x = 0; x <= (allpieces.size() - 1); x++ ) {

            //Find all moves for specific piece
            List<String> allmoves = new ArrayList<>();
            mainBoard.checkPosition(allpieces.get(x),allmoves);

            allpossibilities += allpieces.get(x) + " -> ";

            //Display all movements for current piece
            for(int i = 0; i <= (allmoves.size() - 1); i++ ){
                allpossibilities += allmoves.get(i) + " ";
            }
            allpossibilities += "\n";
        }

        //Display all pieces and moves in log text.
        TextView logText = findViewById(R.id.textLog);
        logText.setText(allpossibilities);
        logText.setTextColor(Color.MAGENTA);

    }

    /* *********************************************
    Source Code for everything that interacts with the board and pieces buttons.
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: createPieces
     * Creates and configures a grid of buttons on a TableLayout to represent the game board, enabling interaction based on player type.
     *
     * @param boardLayout The TableLayout on which the game board buttons are arranged.
     *
     * <p>Algorithm:
     * 1. Retrieve a copy of the game board's current state represented as a 2D array.
     * 2. Iterate through each cell in the 2D array to create corresponding buttons in the TableLayout.
     * 3. Set properties for each button, including dimensions, text, and event handlers.
     * 4. Disable buttons if the current player is not human to prevent interactions.
     * 5. Color the buttons based on the pieces they represent on the game board.
     * 6. Add each button to the appropriate TableRow and each row to the TableLayout.
     * 7. Display the current score after setting up the board.
     *
     * <p>Note: This function does not return any value; it visually constructs the game board and updates the UI.
     */
    private void createPieces(TableLayout boardLayout) {

        //Get copy of board
        char[][] arrayBoard = mainBoard.getCopyBoard();

        //Go through the rows of the board and Tablelayout to correspond
        for (int i = 0; i < 8; i++) {
            TableRow row = new TableRow(this);
            //Create new row
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            //Inside row of tablelayout
            for (int j = 0; j < 8; j++) {
                //Create button
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                //Assign button to the name of the coordinates it exists on
                String coords = mainBoard.intToString(i, j);
                button.setText(coords);  // Set text or background as needed
                //text.setText(coords);
                button.setId(((i + 1) * 10) + (j + 1));

                //Event handler for button to be able to select piece and move piece
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleButtonClick(view);
                    }
                });

                //If the currentplayer isnt human the buttons shouldnt be clickable
                if (!currentPlayer.isHuman()) {
                    button.setEnabled(false);
                }

                //Depending on the games board have the pieces colored accordingly
                if (arrayBoard[i][j] == 'B') {
                    button.setBackgroundColor(Color.BLUE);
                } else if (arrayBoard[i][j] == 'W') {
                    button.setBackgroundColor(Color.WHITE);
                } else {
                    button.setBackgroundColor(Color.LTGRAY);
                }

                //Add button to row
                row.addView(button);
            }
            //Add row to layout.
            boardLayout.addView(row);
        }
        //Also display the score
        displayScore();
    }

    /**
     * Name: handleButtonClick
     * Handles user interactions with game board buttons, managing game state and updating the UI accordingly.
     *
     * @param view A View class that was clicked, used for all buttons on the board.
     *
     * <p>Algorithm:
     * 1. Detect the number of button clicks to determine if it's the first or second button interaction.
     * 2. Validate the first button click; if valid, update UI to reflect the selected piece and wait for the second click.
     * 3. If the first click is invalid, reset the selection process.
     * 4. Validate the second button click; if valid, update UI to show the move and reset for a new turn.
     * 5. If the second click is invalid, indicate error and allow for reselection.
     * 6. Repaint the board if necessary to update visual changes.
     *
     * <p>Note: This function modifies the game state and updates the UI based on user interaction but does not return any value.
     */

    private void handleButtonClick(View view) {

        //Text to display if piece/move is valid
        TextView currentpiece = findViewById(R.id.txtDisplay);
        currentpiece.setTextColor(Color.BLACK);

        //First piece selection
        switch (clicks) {
            case 0:
                firstClickedButton = (Button) view;
                // Validation first button
                if (validatePiece(firstClickedButton)) { //Valid piece
                    Log.d("ValidFirst", firstClickedButton.getText().toString());
                    //Set piece selected to green
                    firstClickedButton.setBackgroundColor(Color.GREEN);
                    //Get piece and display if valid
                    String selectedFirst = firstClickedButton.getText().toString();
                    currentpiece.setText("Piece: " + selectedFirst);
                    reprint = false;
                    secondClickedButton = null;
                    clicks++;
                } else { //Invalid selection
                    Log.d("InvalidFirst", firstClickedButton.getText().toString());
                    firstClickedButton.setBackgroundColor(Color.RED);
                    currentpiece.setText("First Selection Invalid");
                    reprint = true;
                    secondClickedButton = null;
                    //Reset clicks to get correct piece
                    clicks = 0;
                }
                break;
            case 1: //Selection of piece was find
                secondClickedButton = (Button) view;
                secondClickedButton.setBackgroundColor(Color.GREEN);
                String secondSelection = secondClickedButton.getText().toString();

                //Get all possible moves the selected piece could go
                List<String> possible_moves = new ArrayList<>();
                mainBoard.checkPosition(firstClickedButton.getText().toString(), possible_moves);

                Boolean invalidsecond = false;

                //Go through all possible moves
                for (int i = 0; i < possible_moves.size(); i++) {
                    String onemove = possible_moves.get(i);

                    //Check if the movement exists
                    if (secondSelection.equals(onemove)) {
                        Log.d("ValidSecond", secondClickedButton.getText().toString());
                        //Valid piece was selected
                        secondClickedButton.setBackgroundColor(Color.GREEN);
                        String selectedFirst = firstClickedButton.getText().toString();
                        String selectedSecond = secondClickedButton.getText().toString();
                        //Display valid piece
                        currentpiece.setText("Piece: " + selectedFirst + " -> " + selectedSecond);
                        //Doesnt reset movement
                        invalidsecond = false;
                        break;
                    } else { //Doesnt exists
                        Log.d("InvalidSecond", secondClickedButton.getText().toString());
                        currentpiece.setText("Second Selection Invalid");
                        //Resets first click to have user redo movement incase they didnt want it selected
                        invalidsecond = true;
                    }
                }

                if (invalidsecond == true) {
                    firstClickedButton = null;
                }

                reprint = true;
                clicks = 0;
                break;
            default:

                break;
        }

        if (reprint) {
            reprintBoard();
            reprint = false;
        }

    }

    /**
     * Name: playpiece
     * Handles game piece movements based on player interactions, updating the game state and UI based on the moves made.
     *
     * @param View a View class, that continue button was clicked.
     *
     * <p>Algorithm:
     * 1. Check whose turn it is, human or computer, and process moves accordingly.
     * 2. For human players, ensure both a piece and a destination have been selected before making a move.
     * 3. If valid, execute the move on the game board and check for a win condition.
     * 4. For computer players, automatically compute and execute the move.
     * 5. If a win condition is detected, update the UI to reflect the game's outcome and prompt for a rematch.
     * 6. Update the game logs, swap the display for whose turn it is, and repaint the board to reflect the current game state.
     *
      */

    public void playpiece(View View) {
        //Text to display message
        TextView text = findViewById(R.id.txtDisplay);
        //validate movement
        boolean validmovement = false;

        //Check whos turn it is
        if (currentPlayer.isHuman()) { //Human is moving

            //Checking to see if either clicks have been completed to play a piece.
            if (firstClickedButton == null || secondClickedButton == null) {
                text.setText("Select a Piece/Position");
                text.setTextColor(Color.BLACK);
            }
            //Piece is playable
            else {

                //Get both moves coordinates
                String firstSelection = firstClickedButton.getText().toString();
                String secondSelection = secondClickedButton.getText().toString();
                //play out piece and movement on board
                mainBoard.movePiece(firstSelection, secondSelection, currentPlayer.getColor(), false);

                //Reset text
                text.setText("");
                validmovement = true;

            }

        } else {  //Computer is moving

            //Computer play its move.
            player2.play(mainBoard, player2, player1);
            validmovement = true;
            firstClickedButton = null;
            secondClickedButton = null;

        }


        //Check for win
        if (mainBoard.winCondition(currentPlayer.getColor())) {
            Log.d("Win", "Win found!");
            //Increment win for currentplayer
            currentPlayer.addWin();
            currentPlayer.updateScore(mainBoard, currentPlayer.getNextPlayer(currentPlayer, playerList), currentPlayer);
            displayScore();

            //Get layouts to modify for round end
            ConstraintLayout roundEnd = findViewById(R.id.rematchLayout);
            ConstraintLayout controlLayout = findViewById(R.id.controlLayout);
            ConstraintLayout coinflip = findViewById(R.id.coinflipLayout);
            TextView displayWinner = findViewById(R.id.displayWinner);

            //checking which player one
            if (currentPlayer.isHuman()) {
                displayWinner.setText("Human Winner!");
            } else {
                displayWinner.setText("Computer Winner!");
            }

            //Disabling current round controls and asking for a rematch
            coinflip.setVisibility(View.INVISIBLE);
            controlLayout.setVisibility(View.INVISIBLE);
            roundEnd.setVisibility(View.VISIBLE);

        }

        //Movement was valid update the game state
        if (validmovement) {
            updateLog();
            swapPlayerText();
            reprintBoard();
            validmovement = false;
        }

    }

    /**
     * Name: finishGame
     * To end the game
     *
     * @param View, is a view class used for clicking, referencing Exit and End buttons.
     * <p>Algorithm
     *  1. Ends game
     */
    public void finishGame(View View){
        System.exit(0);
    }

    /**
     * Name: Rematch
     * Win condition is met and the user has to decide for a rematch or not, it'll update the UI accordingly
     *
     * @param View, a view class referencing buttons Yes and No
     *  <P>Algorithm
     *  1. User chooses for a rematch
     *  2. Yes for rematch, checks if the how many wins both users have
     *      2a. Same amount of wins user has to do coinflip to decide starting player
     *      2b. Player with more wins is the starting player.
     *  3. No, gets player with the most amount of wins and declares them the winner of the tournament.
     */
    public void rematch(View View) {

        // selecionBtn is between Yes and No buttons to see what the user chooses
        Button selectionBtn = (Button) View;
        //Getting all layouts to disable or make visible
        ConstraintLayout coinflipLayout = findViewById(R.id.coinflipLayout);
        ConstraintLayout controlLayout = findViewById(R.id.controlLayout);
        ConstraintLayout rematch = findViewById(R.id.rematchLayout);
        TableLayout piecesLayout = findViewById(R.id.piecesLayout);

        //Checking to see what the play choose.
        if (selectionBtn.getText().toString().equals("Yes")) { // Want Rematch
            rematch.setVisibility(View.INVISIBLE);

            mainBoard = new Board();

            //Have the same amount of wins meaning a coinflip is needed.
            if (player1.getWins() == player2.getWins()) { // Equal amount of wins need coinflip

                Button headsBtn = findViewById(R.id.Heads);
                Button tailsBtn = findViewById(R.id.Tails);
                headsBtn.setEnabled(true);
                tailsBtn.setEnabled(true);
                TextView displayCurrplayer = findViewById(R.id.textCurrentPlayer);
                coinflipLayout.setVisibility(View.VISIBLE);
                displayCurrplayer.setText("");
                piecesLayout.removeAllViews();
                piecesLayout.setVisibility(View.INVISIBLE);


            } else { // No need for coinflip since the
                firstClickedButton = null;
                secondClickedButton = null;
                swapPlayerText();
                coinflipLayout.setVisibility(View.VISIBLE);
                controlLayout.setVisibility(View.VISIBLE);
                reprintBoard();

            }

        }

        else {

            //Get tournament winner
            tournament.setPlayers(playerList);
            ConstraintLayout tournamentLayout = findViewById(R.id.layoutTournament);
            TextView Winner,Humanscore,Humanwins,Computerscore,Computerwins;
            Winner = findViewById(R.id.textWinner);
            Humanscore = findViewById(R.id.textHScore);
            Humanwins = findViewById(R.id.textHWins);
            Computerscore = findViewById(R.id.textCScore);
            Computerwins = findViewById(R.id.textCWins);

            String result = tournament.endRound();

            //Getting value to display the winner
            if(result.equals("Human")){
                Winner.setText("Winner: Human!");
            } else if (result.equals("Computer")) {
                Winner.setText("Winner: Computer!");
            }else {
                Winner.setText("TIE!");
            }

            //Show score
            Humanscore.setText("Human Score: " + Integer.toString(player1.getScore()));
            Humanwins.setText( "Human Wins: " + Integer.toString(player1.getWins()));
            Computerscore.setText("Computer Score: " + Integer.toString(player2.getScore()));
            Computerwins.setText("Computer wins: " +  Integer.toString(player2.getWins()));

            //Disable all layouts

            TableLayout pieceLayout = findViewById(R.id.piecesLayout);
            pieceLayout.setVisibility(View.INVISIBLE);

            TextView textlog = findViewById(R.id.textLog);
            textlog.setVisibility(View.INVISIBLE);

            TextView textStats = findViewById(R.id.textStats);
            textStats.setVisibility(View.INVISIBLE);

            TextView statHeader = findViewById(R.id.Stats);
            statHeader.setVisibility(View.INVISIBLE);

            TextView displayInfo = findViewById(R.id.txtDisplay);
            displayInfo.setVisibility(View.INVISIBLE);

            TextView currPlayer = findViewById(R.id.textCurrentPlayer);
            currPlayer.setVisibility(View.INVISIBLE);

            Button btnlog = findViewById(R.id.btnLog);
            btnlog.setVisibility(View.INVISIBLE);

            controlLayout.setVisibility(View.INVISIBLE);
            coinflipLayout.setVisibility(View.INVISIBLE);
            rematch.setVisibility(View.INVISIBLE);

            //Show the end of the tournament layout
            tournamentLayout.setVisibility(View.VISIBLE);

        }

    }

    /**
     * Name: displayLog
     * Displaying log when button clicked
     *
     * @param View, from view class. Referencing button "log"
     * <p>Algorithm
     *  1. initialize gamelog text.
     *  2. Display log and change color.
     */
    public void displayLog(View View) {
        TextView gamelog = findViewById(R.id.textLog);
        gamelog.setText(log);
        gamelog.setTextColor(Color.BLACK);

    }

    /**
     * Name: displayScore
     * get both players stats and displays it.
     *
     * <p>Algorithm
     * 1. initialize textview textStats
     * 2. Get computer and human stats
     * 3. Append stats on a string called input
     * 4, Display stats in textStats
     */

    /* *********************************************
    Source Code to update all the information displayed for UI
    ********************************************* */
    // List all the relevant functions here
    public void displayScore() {
        TextView scoreText = findViewById(R.id.textStats);

        String computerWins = Integer.toString(player2.getWins());
        String computerScore = Integer.toString(player2.getScore());

        String humanWins = Integer.toString(player1.getWins());
        String humanScore = Integer.toString(player1.getScore());

        String input = "Human Stats:\n"
                + "Wins: " + humanWins + "\n"
                + "Score: " + humanScore + "\n"
                + "Computer Stats" + "\n"
                + "Wins: " + computerWins + "\n"
                + "Score: " + computerScore;

        scoreText.setText(input);
    }

    /**
     * Name: swapPlayerText
     * updates text to show current player
     *
     * <p>Algorithm
     * 1. initialize text for displaying currentplayer
     * 2. Depending on current player the text will be green or white
     * 3. Display current player
     */
    public void swapPlayerText() {
        TextView text = findViewById(R.id.textCurrentPlayer);
        currentPlayer = currentPlayer.getNextPlayer(currentPlayer, playerList);

        if (currentPlayer.isHuman()) {
            text.setTextColor(Color.GREEN);
            text.setText("Turn: Human");
        } else {
            text.setTextColor(Color.WHITE);
            text.setText("Turn: Computer");
        }

    }

    /**
     * Name: updateLog
     * updatelog information to textView and display
     *
     * <p>Algorithm
     * 1. initialize gamelog text view
     * 2. Make textvoew scrollable
     * 3. If human store/display piece selected and movement to save, else store/display strategy from computer
     */
    public void updateLog() {
        TextView gamelog = findViewById(R.id.textLog);
        gamelog.setMovementMethod(new ScrollingMovementMethod());
        String isHuman;
        gamelog.setTextColor(Color.BLACK);

        if (currentPlayer.isHuman()) {
            String firstButton = firstClickedButton.getText().toString();
            String secondButton = secondClickedButton.getText().toString();

            isHuman = "Human";
            log += isHuman + ": " + firstButton + "->" + secondButton + "\n";
            gamelog.setText(log);
        } else {
            String computerlog = currentPlayer.pickStrategies();
            isHuman = "Computer";
            log += isHuman + ": " + computerlog + "\n";
            gamelog.setText(log);
        }
    }

    /**
     * Name: reprintBoard
     *
     * <p>Algorithm
     * 1. Initialize tablelayout for pieces
     * 2. Remove all previous information and create all the buttons again
     */
    private void reprintBoard() {
        TableLayout piecelayout = findViewById(R.id.piecesLayout);
        piecelayout.removeAllViews();
        createPieces(piecelayout);
    }

    /**
     *
     * @param button, button class reference button from board.
     * @return true if piece is valid, false if piece isnt valid
     * <p>Algorithm
     * 1. initialize string coordinates from button
     * 2. Check if piece is valid through round newSelecetionValidation
     * 3. if valid return true and make button green, otherwise return false.
     */

    /* *********************************************
    Source Code to validate piece
    ********************************************* */
    // List all the relevant functions here
    public boolean validatePiece(Button button) {
        String Coordinates = button.getText().toString();
        boolean checkPiece = currRound.newSelectionValidation(Coordinates, mainBoard, player1);

        if (checkPiece) {
            Log.d("pieceVal", "Valid");
            button.setBackgroundColor(Color.GREEN);
            return true;
        } else {
            Log.d("pieceVal", "Invalid");
            return false;
        }


    }

    /* *********************************************
    Source Code to repopulate entire game state
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: serialization
     * Processes serialized game data from a string to restore game state including board configuration, player turns, and scores.
     *
     * @param argBoard The game board object that the board state will be serialized into.
     * @param argListPlayer An array of Player objects to update with the restored state.
     * @param argCurrentPlayer The current player's state to be updated based on the serialized data.
     * @param information The serialized string containing all necessary game data.
     *
     * <p>Algorithm:
     * 1. Use two scanners: one to determine the next player and their color, and another to parse the overall game state.
     * 2. Update the current player and their color based on the contents of the serialized data.
     * 3. Read the board configuration and restore each row from the serialized data.
     * 4. Parse player-specific data such as wins and scores, updating the Player objects accordingly.
     * 5. Output debug information regarding the restoration of human and computer player states.
     *
     * <p>Note: This function does not return any value; it updates the game state objects directly.
      */

    public void serialization(Board argBoard, Player[] argListPlayer, Player argCurrentPlayer, String information) {

        //Scanner used to parse information
        Scanner scanner = new Scanner(information);
        int boardRows = 0;
        //Initialize currenplayer text
        TextView textCurrplay = findViewById(R.id.textCurrentPlayer);

        //Copy scanner to find current player first
        Scanner findnextplayer = new Scanner(information);

        //Checking to find next player until false
        while(findnextplayer.hasNextLine()){
            String findnextline = findnextplayer.nextLine();

            //Current player is computer
            if(findnextline.equals("Next player: Computer")){
                textCurrplay.setText("Turn: Computer");
                currentPlayer = player2;
                playerList[0] = player2;
                playerList[1] = player1;

                //Set color of computer player
                String findcolor = findnextplayer.nextLine();
                if(findcolor.equals("Color: White")){
                    playerList[0].setColor('W');
                    playerList[1].setColor('B');
                }else{
                    playerList[1].setColor('W');
                    playerList[0].setColor('B');
                }

            }
            //Current player is Human
            else if (findnextline.equals("Next player: Human")){
                    textCurrplay.setText("Turn: Human");
                    currentPlayer = player1;
                    playerList[0] = player1;
                    playerList[1] = player2;

                    //Set color of human player
                String findcolor = findnextplayer.nextLine();
                if(findcolor.equals("Color: White")){
                    playerList[0].setColor('W');
                    playerList[1].setColor('B');
                }else{
                    playerList[1].setColor('W');
                    playerList[0].setColor('B');
                }

            }

        }

        //Parse through entire scanner to get information for game
        while(scanner.hasNextLine()){

           String line = scanner.nextLine();

           //Board found
            if (line.equals("Board:")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.equals("")) {
                        break;
                    }
                    //Set each line from scanner to the board
                    argBoard.serializeBoard(boardRows, line);
                    boardRows++;
                }
            }

            //Set human scores and wins
            if (line.equals("Human:")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.equals("")) {
                        break;
                    }
                    if (line.contains("Rounds won")) {
                        int pos = line.indexOf(":") + 2;
                        player1.setWins(Integer.parseInt(line.substring(pos)));
                    }
                    if (line.contains("Score")) {
                        int pos = line.indexOf(":") + 2;
                        player1.setScore(Integer.parseInt(line.substring(pos)));
                    }
                }
                System.out.println("Human: Wins:" + argListPlayer[0].getWins() + " Score: " + argListPlayer[0].getScore());
            }

            //Set computer wins and scores
            if (line.equals("Computer:")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.equals("")) {
                        break;
                    }
                    if (line.contains("Rounds won")) {
                        int pos = line.indexOf(":") + 2;
                        player2.setWins(Integer.parseInt(line.substring(pos)));
                    }
                    if (line.contains("Score")) {
                        int pos = line.indexOf(":") + 2;
                        player2.setScore(Integer.parseInt(line.substring(pos)));
                    }
                }
                System.out.println("Computer: Wins:" + argListPlayer[1].getWins() + " Score: " + argListPlayer[1].getScore());
            }

        }

    }
}

