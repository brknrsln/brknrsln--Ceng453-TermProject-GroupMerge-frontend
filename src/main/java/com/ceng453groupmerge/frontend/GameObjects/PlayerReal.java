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

        if(spendJailTime() == 0) { // If player is not jailed
            DiceController.getInstance().rollDice(); // TODO: Remove this
            // TODO: Initialize dice button
            // TODO: Wait for dice thread finish
            int diceValue = Dice.getInstance().sumDice();
            if(Dice.getInstance().isDouble()) consecutiveDoubles++;
            else consecutiveDoubles = 0;
            if(consecutiveDoubles == 2) { // If 2 consecutive doubles, go to jail and end function
                sendToJail();
            }
            else {
                int oldPosition = getCurrentPosition();
                movePlayer(diceValue);
                if(getCurrentPosition()<oldPosition) {
                    addMoney(100); // Moved over starting point
                }
                // TODO: Print new player position and money

                gameLogic.getTiles().get(getCurrentPosition()).tileAction(this);
                // TODO: Above function should either perform an action or bring up the necessary buttons. Handle them.
                // TODO: Print new player position and money
            }
            if(consecutiveDoubles > 0) playTurn(); // If player rolled double, play turn again
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
