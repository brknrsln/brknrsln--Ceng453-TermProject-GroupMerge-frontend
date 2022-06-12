package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;

import java.util.Random;

public class PlayerReal extends Player {

    private static Random random = new Random();

    public PlayerReal(String name) {
        setPlayerName(name);
        setPlayerID(playerCount++);
    }

    @Override
    public void playTurn() {
        System.out.println("playTurn called for "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime() == 0) { // If player is not jailed
            GameController.getInstance().setRollButtonDisable(false);
            System.out.println("Waiting for dice"); // TODO: Debug, remove
        }
    }

    @Override
    public void playTurnAfterDice() {
        while (GameLogic.rollDice);
        System.out.println("playTurnAfterDice called for "+getPlayerName()); // TODO: Debug, remove
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
            // TODO: Print player money
            GameController.getInstance().drawPlayerSprites(getPlayerID());

            Player otherPlayer = GameLogic.getInstance().getPlayers().get(GameLogic.getInstance().getOtherPlayer());

            GameLogic.getInstance().getTiles().get(getCurrentPosition()).tileAction(this, otherPlayer);
            System.out.println("Waiting for buttons for "+getPlayerName());
            if(!GameLogic.getInstance().waitingOnButtons) GameLogic.getInstance().skipTurn();
        }
    }

    @Override
    public void playTurnAfterButton() {
        System.out.println("playTurnAfterButton called for "+getPlayerName()); // TODO: Debug, remove

        // TODO: Print player money
        GameController.getInstance().drawPlayerSprites(getPlayerID());

        if(consecutiveDoubles > 0) playTurn(); // If player rolled double, play turn again
        else GameLogic.getInstance().oneGameTurn();
    }
}
