package com.example.locjava;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Scanner;

public class Tournament{

    private Player Human;
    private Player Computer;
    public Tournament(){}
    public Tournament(Player[] playerlist){
        if(playerlist[0].isHuman()){
            Human = playerlist[0];
            Computer = playerlist[1];
        }
        else{
            Human = playerlist[1];
            Computer = playerlist[0];
        }
    }

    /**
     * Name: setPlayers
     * Assign tournament with the players references
     *
     * @param playerlist
     * <p>Algorithm
     * 1. Check if the first player in the array is human and assign the proper variables.
     */
    public void setPlayers(Player[] playerlist){
        if(playerlist[0].isHuman()){
            Human = playerlist[0];
            Computer = playerlist[1];
        }
        else{
            Human = playerlist[1];
            Computer = playerlist[0];
        }
    }

    /**
     * Name: endRound
     * Help decide who is the winner of the tournament
     *
     * @return A string resultTour.
     * <p>Algorithm
     * 1. Get human and computer wins
     * 2. Compare wins and return back the winner.
     */
    public String endRound(){

        String resultTour;
        int Humanwins = Human.getWins();
        int Computerwins = Computer.getWins();

        if(Humanwins > Computerwins){
            resultTour = "Human";
        } else if (Humanwins < Computerwins) {
            resultTour = "Computer";
        }else{
            resultTour = "Tie";
        }

       return resultTour;
    }
}