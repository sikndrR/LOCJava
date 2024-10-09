package com.example.locjava;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Player {
    protected char p_color;
    protected int p_TotalPieces;
    protected List<String> p_pieces;
    protected int p_ID;
    protected static int nextID = 1;

    protected int score;
    protected int wins;

    protected boolean human;
    protected List<String> allStrats;

    public Player() {
        p_color = 'N';
        p_TotalPieces = 0;
        p_pieces = new ArrayList<>();
        p_ID = nextID++;
    }


    /* *********************************************
    Source Code to set the change the variables in player
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: setScore
     * Changing the number of score a player has.
     *
     * @param argScore an integer. New value of the amount of wins.
     *
     * <p>Algorithm:
     * 1. Addiontally adds a specified number of score to the player's member variable wins.
     */

    public void setScore(int argScore) {
        score = argScore + score;
    }

    /**
     * Name: setWins
     * Changing the number of wins a player has.
     *
     * @param argWins an integer. New value of the amount of wins.
     * <p>Algorithm
     * 1. Assigns specified number of wins to the player's member variable wins.
     */

    public void setWins(int argWins) {
        wins = argWins;
    }

    /**
     * Name: setColor
     * Change color of player
     *
     * @param argColor a char. given value is what the players color will change to.
     *
     * <p>Algorithm
     * 1. Assigning player new color.
     */

    public void setColor(char argColor) {
        p_color = argColor;
    }


    /* *********************************************
    Source Code to get the variables of player
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: getColor
     * To retrieve a players color.
     *
     * @return char, players color.
     * <p>Algorithm
     * 1. Returns the member variable stored in the player's p_color.
     */

    public char getColor() {
        return p_color;
    }

    /**
     * Name: getCurrentscore
     * Calculate current score for a player by subtracting the number of pieces between the current player and the next player.
     *
     * @param argBoard
     * @param argCurrentplayer
     * @param argNextplayer
     * @return an integer, returning the difference between total pieces from currentplayer and total pieces from next player.
     *
     * <p>Algorithm
     * 1. Getting the different of pieces belonging to the next player from the number of pieces belonging to the current player.
     */

    public int getCurrentscore(Board argBoard, Player argCurrentplayer, Player argNextplayer) {
        return argBoard.getPieces(argCurrentplayer.getColor()) - argBoard.getPieces(argNextplayer.getColor());
    }

    /**
     * Name: getNextPlayer
     * To get the next player in the game
     *
     * @param argCurrentPlayer player object, representing the current player
     * @param listPlayers array of Players, containing all players in the game.
     * @return Player, representing the next player
     *
     * <p>Algorithm
     * 1. Compare the current player with the first player in the list.
     * 2. If the current player is the first player, return the second player.
     * 3. Otherwise, return the first player.
     */

    Player getNextPlayer(Player argCurrentPlayer, Player[] listPlayers) {
        if (argCurrentPlayer == listPlayers[0]) {
            return listPlayers[1];
        } else {
            return listPlayers[0];
        }
    }

    /**
     * Name: getWins
     * Retrieve the current score of player.
     *
     * @return integer, players wins
     * <p>Algorithm
     * 1. return member variable of player, score.
     */

    public int getWins() {
        return wins;
    }

    /**
     * Name: getScore
     * Retrieve the current score of player.
     *
     * @return integer, players score
     * <p>Algorithm
     * 1. return member variable of player, score.
     */

    public int getScore() {
        return score;
    }

    /* *********************************************
    Source Code to increment and update the score of a player
    ********************************************* */
    // List all the relevant functions here

    /**
     * Name: updateScore
     * Players need to have score updated after every round. if the score isnt updated you wont know whos winning.
     *
     * @param argBoard Board represents the current state of the game board. This parameter is used to count the pieces.
     * @param argNextplayer Player This parameter is used to calculate and update the score based on the number of pieces they have on the board.
     * @param argCurrentPlayer Player This parameter is used to calculate and update the score based on the number of pieces they have on the board.
     *
     * <p> Algorithm
     * 1. Current player pieces and next player pieces are found.
     * 2. Get the difference between pieces and set them to the each own players score.
     */

    public void updateScore(Board argBoard, Player argNextplayer, Player argCurrentPlayer) {
        int currentPieces = 0;
        int oppositePieces = 0;

        currentPieces = argBoard.getPieces(argCurrentPlayer.getColor());
        oppositePieces = argBoard.getPieces(argNextplayer.getColor());

        argCurrentPlayer.setScore((currentPieces - oppositePieces));
        argNextplayer.setScore((oppositePieces - currentPieces));
    }

    /**
     * Name: addWin
     * Increment the win count for a player.
     *
     * <p>Algorithm
     * 1. increment players win
     */

    public void addWin() {
        wins = wins + 1;
    }

    /**
     * Name: isHuamn
     * Check if the player is a human.
     *
     * @return boolean from the class.
     *
     * <p>Algorithm
     * 1. Returns the value based on human member variable.
     */

    public boolean isHuman() {
        return human;
    }


    /* *********************************************
    Source Code for assistance in the strategies or finding them.
    ********************************************* */
    // List all the relevant functions here

    /** FUNCTION ISNT REALLY USED CAUSE HUMAN OR COMPUTER IS PLAYING */
    public void play(Board argBoard, Player argCurrentplayer, Player argNextplayer) {
        System.out.println("testing playing from player class");
    }

    /**
     * Name: runStrategies
     * To execute all strategy functions for the computer to use and the human to ask for help.
     *
     * @param argBoard Board represents the current state of the game board.
     * @param argCurrentPlayer Player represents the current player
     * @param argNextPlayer Player represents the next player
     *
     * <p>Algorithm:
     * 1. Clear all previous strategies.
     * 2. Execute thwart strategy to block the opponent's winning move.
     * 3. Execute winning and delay strategy based on current scores.
     * 4. Execute capture strategy aimed at capturing opponent pieces.
     * 5. Execute blocking strategy to block opponent's moves.
     * 6. List all possible moves for the current player.
     * 7. Choose the first random play doable.
     */

    public void runStrategies(Board argBoard, Player argCurrentPlayer, Player argNextPlayer) {
        allStrats = new ArrayList<>();

        thwartStrat(argBoard, argCurrentPlayer, argNextPlayer);				//Check Thwart
        winningANDdelay(argBoard, argCurrentPlayer, argNextPlayer);			//Check Delay
        captureStrat(argBoard, argCurrentPlayer, argNextPlayer, "Capture"); //Fix capture
        blockingStrat(argBoard, argCurrentPlayer, argNextPlayer);
        allpossiblemoves(argBoard, argCurrentPlayer, argNextPlayer);
        randomstrat(argBoard, argCurrentPlayer, argNextPlayer);
        //allStrats.clear();
    }

    /**
     * Name: pickStrategies
     * To select the best strategy for the current player based on the pieces and moves that was found from runStrategies.
     *
     * @return a string with the best strategy found.
     *
     * <p>Algorithm:
     * 1. Initialize the best strategy as an empty string and set the highest priority to 9.
     * 2. Iterate through all strategies stored in the allStrats vector.
     * 3. If the "Best:" strategy is found, return it immediately as it has the highest priority.
     * 4. Compare each strategy's priority against the current best priority and update the best strategy and best priority as needed.
     * 5. Prioritize strategies in the order of "Thwart:", "Delay:", "Win:", "Capture:", "Blocking:", and "Random:".
     * 6. Return the best strategy found during the iteration.
     */

    public String pickStrategies() {
        //Will be holding the best strategy.
        String bestStrategy = "";
        //Priority of all the possible strategies to choose from. Starting at 9 so its able to go through all of them.
        int bestPriority = 9;

        // Going through the entire vector of strategies found from my runStratgies function to choose the best one.
        for (String line : allStrats) {
            //Best: Win is found with taking the other players piece.
            if (line.contains("Best:")) {
                return line;
            }
            //Thwart: the other player can win in the next move, so the currentplayer is going to see if he can intercept it.
            else if (line.contains("Thwart:") && bestPriority > 2) {
                bestStrategy = line;
                bestPriority = 2;
            }
            //Delay: Current player can win but doesnt have a good enough score, so itll prolong the game until he has a better score.
            else if (line.contains("Delay:") && bestPriority > 3) {
                bestStrategy = line;
                bestPriority = 3;
            }
            //Win: Score is above the other players and is able to find a piece to connect all the pieces together.
            else if (line.contains("Win:") && bestPriority > 4) {
                bestStrategy = line;
                bestPriority = 4;
            }
            //Capture: Current player is down in score for the current round and will try and get the other players piece.
            else if (line.contains("Capture:") && bestPriority > 5) {
                bestStrategy = line;
                bestPriority = 5;
            }
            //Blocking: The current player will try and get the other players piece stuck on the side of the board
            else if (line.contains("Blocking:") && bestPriority > 6) {
                bestStrategy = line;
                bestPriority = 6;
            }
            //Random: No other strategy was found so the first moveable piece is done.
            else if (line.contains("Random:") && bestPriority > 7) {
                bestStrategy = line;
                bestPriority = 7;
            }
        }
        return bestStrategy;
    }

    /**
     * Name: allpossiblemoves
     * To list all possible moves for a player's pieces on the board.
     *
     * @param argBoard
     * @param argCurrentplayer
     * @param argNextplayer
     *
     * <p>Algorithm:
     * 1. Create a copy of the current and next player to avoid modifying the original players.
     * 2. Retrieve all pieces of the current player from the board.
     * 3. For each piece, find all possible new positions it can move to.
     * 4. Display each piece along with its possible new positions.
     * 5. Clear the list of new positions after each piece to prepare for the next piece's possible moves.
     */

    public void allpossiblemoves(Board argBoard, Player argCurrentplayer, Player argNextplayer) {
        //Copy of players are made inorder to not modify the original variables.
        Player currentplayerCopy = argCurrentplayer;
        Player nextplayerCopy = argNextplayer;

        //Vectors that copy all possible pieces and the position those pieces can go to.
        List<String> copyPieces = new ArrayList<>();
        List<String> copyNewpos = new ArrayList<>();

        //Getting the location of all the pieces on the board currently
        argBoard.findPieces(copyPieces, currentplayerCopy.getColor()); //ISSUE FOUND

        //Loop to go through all the pieces on the board
        for (String piece : copyPieces) {
            //Find avaliable positions the current piece can go to.
            argBoard.checkPosition(piece, copyNewpos);
            //Display the piece
            System.out.print("Piece:" + piece + " -> ");
            //Loop through all the positions the piece can go to.
            for (String newPos : copyNewpos) {
                //Display the piece the position can go to
                System.out.print(newPos + " ");
            }
            //Clear the vector so the position from the preivous piece isnt used
            System.out.println();
            copyNewpos.clear();
        }
    }

    /**
     * Name: captureStrat
     * Implements a strategy focused on capturing or improving the position based on the game score.
     *
     * @param argBoard A Board object, representing the game board. This object is not modified within this function.
     * @param argCurrentplayer A Player object, representing the player executing the strategy. Used to assess the player's total pieces and score. This object is not modified.
     * @param argNextplayer A Player object, representing the opponent player. Used to assess the opponent's total pieces. This object is not modified.
     * @param strategyDescription A string that describes the strategy to be applied.
     *
     * <p>Algorithm:
     * 1. Make copies of the current and next players to avoid modifying the originals.
     * 2. Get the current score and compare it after making a move to see if it improves.
     * 3. Find all pieces of the current player and check possible new positions for each.
     * 4. For each piece, evaluate if moving to a new position improves the score, considering the total number of pieces.
     * 5. If the move is advantageous (improves score and piece count), add the strategy to a list.
     *
     * <p>Note: This function does not return any value as its purpose is to develop and add capturing strategies to a list.
     */

    public void captureStrat(Board argBoard, Player argCurrentplayer, Player argNextplayer, String strategyDescription) {
        //Copy of players are made inorder to not modify the original variables.
        Player currentplayerCopy = argCurrentplayer;
        Player nextplayerCopy = argNextplayer;

        //Vectors that copy all possible pieces and the position those pieces can go to.
        List<String> copyPieces = new ArrayList<>();
        List<String> copyNewpos = new ArrayList<>();

        //Assigning a copy of the current game score, will be change when pieces are player.
        int copyCurrscore = getCurrentscore(argBoard, currentplayerCopy, nextplayerCopy);
        //Getting the current score of the game.
        int oldCurrScore = getCurrentscore(argBoard, currentplayerCopy, nextplayerCopy);

        //Get all pieces from the current gamestate
        argBoard.findPieces(copyPieces, currentplayerCopy.getColor());


        for (int i = 0; i <= copyPieces.size() - 1; i++) {
            //Getting all positions the current piece can move
            argBoard.checkPosition(copyPieces.get(i), copyNewpos);

            for (int j = 0; j <= copyNewpos.size() - 1; j++) {

                //Getting the total pieces of the current board
                int totalCurrentPieces = argBoard.getPieces(currentplayerCopy.getColor());
                int totalNextPieces = argBoard.getPieces(nextplayerCopy.getColor());

                //Temporay board to play the game on, with the pieces of positions that can be moved
                Board tempboard = new Board();
                tempboard.copyBoard(argBoard);
                tempboard.movePiece(copyPieces.get(i), copyNewpos.get(j), currentplayerCopy.getColor(), false);

                //Update score based on the position moved
                copyCurrscore = getCurrentscore(tempboard, currentplayerCopy, nextplayerCopy);

                //Check to see if the position played on the tempboard has greater score than the the oldsCurrScore (old game states score)
                //also checking to see from the original game state if the current players total number of pieces of less than the other players
                if (copyCurrscore > oldCurrScore && totalCurrentPieces < totalNextPieces) {
                    String strat;
                    //StrategyDescription could either be delay or capture.
                    strat = strategyDescription + ":" + copyPieces.get(i) + "-" + copyNewpos.get(j);
                    //Strat added
                    allStrats.add(strat);
                }


            }
            //Cleared to get new positions of the next piece
            copyNewpos.clear();
        }


    }

    /**
     * Name: winningANDdelay
     * Implements a strategy that decides whether to pursue a winning move or delay for strategic advantages.
     *
     * @param argBoard A Board object, representing the game board. Used to evaluate if all pieces are connected for a potential win. This object is not modified within this function.
     * @param argCurrentplayer A Player object, representing the current player whose strategy of winning or delaying is being considered. This object is not modified.
     * @param argNextplayer A Player object, representing the next player. Used for score comparison. This object is not modified.
     *
     * <p>Algorithm:
     * 1. Copy current and next players to avoid modifying the original objects.
     * 2. Evaluate all possible moves for the current player on a temporary board.
     * 3. If a move results in a win condition but the current score is less than the opponent's, invoke a delay strategy to attempt to improve the score.
     * 4. If a move results in a win condition with a score increase, mark it as the best strategy.
     * 5. Otherwise, if it simply meets the win condition, mark it as a win strategy.
     *
     * <p>Note: This function does not return any value as its purpose is to analyze and select strategies.
     */

    public void winningANDdelay(Board argBoard, final Player argCurrentplayer, final Player argNextplayer) {
        // Copy of players are made inorder to not modify the original variables.
        Player currentplayerCopy = argCurrentplayer;
        Player nextplayerCopy = argNextplayer;

        // variable of pieces and position for current player
        List<String> copyPieces = new ArrayList<>();
        List<String> copyNewpos = new ArrayList<>();

        // Original score from current player, temp value just for assignment and declaring
        int copyCurrscore = getCurrentscore(argBoard, currentplayerCopy, nextplayerCopy);
        // Score from next player
        int oldCurrScore = getCurrentscore(argBoard, currentplayerCopy, nextplayerCopy);
        // Score of next player
        int copynextScore = getCurrentscore(argBoard, nextplayerCopy, currentplayerCopy);

        // Getting all pieces of current player
        argBoard.findPieces(copyPieces, currentplayerCopy.getColor());

        for (int i = 0; i <= copyPieces.size() - 1; i++) {
            // getting all positions of the [i]th piece
            argBoard.checkPosition(copyPieces.get(i), copyNewpos);

            for (int j = 0; j <= copyNewpos.size() - 1; j++) {

                // Creating a temp board to evaluate and play position
                Board tempboard = new Board();
                tempboard.copyBoard(argBoard);
                tempboard.movePiece(copyPieces.get(i), copyNewpos.get(j), currentplayerCopy.getColor(), false);

                // Seeing if score changed from position moved
                copyCurrscore = getCurrentscore(tempboard, currentplayerCopy, nextplayerCopy);

                // If all pieces of current player on the board are connected go into
                if (tempboard.winCondition(currentplayerCopy.getColor())) {

                    // if there is a win condition although the score of the current round is less than the other players
                    if (copynextScore > copyCurrscore) {
                        captureStrat(argBoard, argCurrentplayer, argNextplayer, "Delay");
                    }
                    // Win condition is met along wil another addition piece is gained
                    else if (copyCurrscore > oldCurrScore) {
                        String strat;
                        strat = "Best:" + copyPieces.get(i) + "-" + copyNewpos.get(j);
                        allStrats.add(strat);
                    }
                    // Win condition is met
                    String strat;
                    strat = "Win:" + copyPieces.get(i) + "-" + copyNewpos.get(j);
                    allStrats.add(strat);
                }
            }
            // Positions are cleared so the new piece can be assigned correct positions
            copyNewpos.clear();
        }
    }

    /**
     * Name: blockingStrat
     * Implements a strategy to block an opponent's moves by identifying and occupying crucial positions on the board.
     * This function is designed to enhance the defensive capabilities of the current player by preemptively blocking key positions that could allow the opponent to gain an advantage.
     *
     * @param argBoard A Board object, representing the game board where the location of all potential blocks is crucial. This object is not modified within this function.
     * @param argCurrentplayer A Player object, representing the current player who is executing this blocking strategy. This object is not modified.
     * @param argNextplayer A Player object, used for context, representing the opponent player. This object is not modified.
     *
     * <p>Algorithm:
     * 1. Copy the current player to avoid modifying the original object.
     * 2. Retrieve potential blocking positions from the game board.
     * 3. For each piece of the current player, find all possible moves.
     * 4. If a possible move matches a potential blocking position, consider it as part of the blocking strategy.
     * 5. Add any found blocking moves to a list of strategies.
     *
     * <p>Note: This function does not return any value as its purpose is to formulate and add blocking strategies to a strategic list.
     */

    public void blockingStrat(Board argBoard, final Player argCurrentplayer, final Player argNextplayer) {
        // Maybe implement argNextplayer some how?

        // Copy of current player is made inorder to not modify the original variables.
        Player currentplayerCopy = argCurrentplayer;

        // Vectors that copy all possible pieces and positions the current player can go to.
        List<String> copyPieces = new ArrayList<>();
        List<String> copyNewpos = new ArrayList<>();

        // Populating potentialblocks from blockingstratCheck in Board class to returning the location needed to block the piece.
        List<String> potentialBlocks = argBoard.blockingstratCheck(currentplayerCopy.getColor());

        // Finds all possible piece of current player
        argBoard.findPieces(copyPieces, currentplayerCopy.getColor());

        for (int i = 0; i <= copyPieces.size() - 1; i++) {

            // Finds all possible position the piece can move to
            argBoard.checkPosition(copyPieces.get(i), copyNewpos);

            for (int j = 0; j <= (copyNewpos.size() - 1); j++) {

                // Goes through all potentialBlocks
                for (String blockingCoords : potentialBlocks) {

                    // Check to see if current position is found in blocking position
                    if (blockingCoords.equals(copyNewpos.get(j))) {

                        String strat = "Blocking:" + copyPieces.get(i) + "-" + copyNewpos.get(j);
                        allStrats.add(strat);
                    }
                }
            }
            // Clear new positions to get new positions of the next piece
            copyNewpos.clear();
        }
    }

    /**
     * Name: thwartStrat
     * Implements a strategy to prevent the opponent from winning by identifying and blocking their potential winning moves.
     *
     * @param argBoard A Board object, representing the game board where the location of all pieces is crucial. This object is not modified within this function.
     * @param argCurrentplayer A Player object, representing the current player who is attempting to thwart the opponent. This object is not modified.
     * @param argNextplayer A Player object, representing the opponent player. This function checks if the opponent is near a win condition. This object is not modified.
     *
     * <p>Algorithm:
     * 1. Copy current and next players to avoid modifying the original objects.
     * 2. Find all pieces of the opponent and their potential winning moves.
     * 3. Simulate on a temporary board if the next player's potential move could result in a win.
     * 4. If a move results in a win condition for the next player, find moves for the current player that can block this winning move.
     * 5. Add the thwart strategy to the list of strategies if the current player can effectively block the opponent's winning move by landing on the same crucial piece.
     *
     * <p>Note: This function does not return any value as its purpose is to add strategies to a list of strategies for blocking opponent moves.
     */

    public void thwartStrat(Board argBoard, final Player argCurrentplayer, final Player argNextplayer) {
        // Copy of players are made inorder to not modify the original variables.
        Player nextplayerCopy = argNextplayer;
        List<String> nxtPieces = new ArrayList<>();
        List<String> nxtPos = new ArrayList<>();

        // Finds all the pieces the nextplayer has.
        argBoard.findPieces(nxtPieces, nextplayerCopy.getColor());

        for (int i = 0; i <= (nxtPieces.size() - 1); i++) {
            //Gets all positions to the [i]th piece next player has.
            argBoard.checkPosition(nxtPieces.get(i),nxtPos);

            for(int j = 0; j<= (nxtPos.size() - 1); j++){

                //Creating a temporay board to play the position
                Board tempboard = new Board();
                tempboard.copyBoard(argBoard);
                tempboard.movePiece(nxtPieces.get(i),nxtPos.get(j),nextplayerCopy.getColor(),false);

                //If the next player finds a win position
                if(tempboard.winCondition(nextplayerCopy.getColor())){ // Opp can win
                    Log.d("NextWin",nxtPieces.get(i) + nxtPos.get(j));

                    //Get all pieces and position of the currentplayer to stop opp win.
                    Player currPlayercopy = argCurrentplayer;
                    List<String> curPieces = new ArrayList<>();
                    List<String> curPos = new ArrayList<>();

                    //Find all pieces of the current player
                    argBoard.findPieces(curPieces, currPlayercopy.getColor());

                    for(int x = 0; x <= (curPieces.size() - 1);x++){

                        argBoard.checkPosition(curPieces.get(x),curPos);

                        for(int y = 0; y <= (curPos.size() - 1); y++){

                            //Compare to see if the nextplayers winning position is found in current player
                            if(curPos.get(y).equals(nxtPos.get(j))){

                                String strat = "Thwart:" + curPieces.get(x) + "-" + curPos.get(y);
                                allStrats.add(strat);

                            }

                        }

                        //Clear current player positions to have new positions for the current piece.
                        curPos.clear();
                    }


                }

            }
            //Clear next player positions to have new positions for the next piece.
            nxtPos.clear();
        }
    }

    /**
     * Name: randomstrat
     * Implements a random strategy by selecting a move not previously considered in other strategies.
     *
     * @param argBoard A Board object, representing the game board. This object is not modified within this function.
     * @param argCurrentplayer A Player object, representing the current player. This object is not modified.
     * @param argNextplayer A Player object, used for context, representing the opponent player. This object is not modified.
     *
     * <p>Algorithm:
     * 1. Copy the current player to avoid modifying the original object.
     * 2. Retrieve all pieces of the current player and their possible new positions.
     * 3. For each possible move, check if the strategy already exists using a strategy checking function.
     * 4. If a move does not already exist as a strategy, add it as a random strategy to the list of strategies.
     *
     * <p>Note: This function does not return any value as its purpose is to add new strategies to a strategy list.
     */

    public void randomstrat(Board argBoard, final Player argCurrentplayer,final Player argNextplayer) {
        //Copy of current player inorder not to modify the original variable
        Player currentplayerCopy = argCurrentplayer;

        //Pieces and positions of copied current player will be stored here
        List<String> copyPieces = new ArrayList<>();
        List<String> copyNewpos = new ArrayList<>();

        //Store all the current player pieces in copyPieces
        argBoard.findPieces(copyPieces, currentplayerCopy.getColor()); //Getting all pieces of player

        for (int i = 0; i < copyPieces.size(); i++) {
            //Get the [i]th piece position
            argBoard.checkPosition(copyPieces.get(i), copyNewpos);

            for (int j = 0; j < copyNewpos.size(); j++) {
                String strat = copyPieces.get(i) + "-" + copyNewpos.get(j);

                //Checks to see if a movement has already been made
                if (stratExists(strat)) {
                    allStrats.add("Random:" + strat);
                }
            }
            //Clear positions for the next piece
            copyNewpos.clear();
        }
    }

    /**
     * Name: stratExists
     * Checks if a specific movement strategy already exists in the list of strategies.
     *
     * @param movement A string representing the move being checked, formatted as "Piece-Position" (e.g., "A6-E5").
     * @return A boolean value. Returns true if the movement does not exist in the list of strategies, indicating it's a new strategy.
     *         Returns false if the movement already exists, indicating it's not a new strategy.
     *
     * <p>Algorithm:
     * 1. Iterate through all strategies stored in the allStrats vector.
     * 2. For each strategy, extract the movement part after the colon (":").
     * 3. Compare the extracted movement with the Movement parameter.
     * 4. If a match is found, return false to indicate the strategy exists.
     * 5. If no match is found after checking all strategies, return true to indicate the strategy is new.
     */


    public boolean stratExists(String movement) {
        //Clone is refering to if vector allStrat has a similar movement already
        boolean clone = false;

        //Going through all of allStrats
        for (String visited : allStrats) {
            //Element from allStrats is assigned to line, being a strategy from previous strategies.
            String line = visited;
            //filtered is getting position in where ':' exist to properly get a substring
            int filter = line.indexOf(':');
            //Foundmov is displaying the piece and the position
            String foundMov = line.substring(filter + 1);
            //Comparing is a piece and position given from randomStrat EX.(A6-E5)
            String comparing = movement;


            //Check to see if a copy exists
            if (comparing.equals(foundMov)) {
                clone = true;
                break;
            } else {
                clone = false;
            }
        }

        return !clone;
    }

}



