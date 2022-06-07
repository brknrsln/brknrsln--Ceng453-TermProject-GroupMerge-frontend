package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.DiceController;

import java.io.IOException;

public class PlayerReal extends Player {

    private GameLogic gameLogic;

    public PlayerReal(String name) throws IOException {
        setPlayerName(name);
        gameLogic = GameLogic.getInstance();
    }

    @Override
    public void playTurn() throws IOException {
        System.out.println("playTurn called for real "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime()>0) { // If player is not jailed
            // TODO: Initialize dice button
            // TODO: Wait for dice thread finish
            // TODO: Check for double, increment a count if so, if count==1 player plays again, if count==2 player goes to jail, else count=0
            DiceController.diceRoll();
            int diceValue = Dice.getInstance().sumDice();
            int oldPosition = getCurrentPosition();
            movePlayer(diceValue);
            if(getCurrentPosition()<oldPosition) {
                addMoney(100); // Moved over starting point
            }
            // TODO: Print new player position and money

            gameLogic.getTiles().get(getCurrentPosition()).tileAction(this);
            // TODO: Above function should either perform an action or bring up the necessary buttons. Handle them.
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
