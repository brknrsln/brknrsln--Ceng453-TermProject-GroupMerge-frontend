package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.DiceController;
import com.ceng453groupmerge.frontend.Controllers.GameController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Random;

public class PlayerAI extends Player {


    public PlayerAI() {
        setPlayerName("AI_OVERLORD");
        setPlayerID(playerCount++);
    }

    @Override
    public void playTurn() {
        System.out.println("playTurn called for "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime() == 0) { // If player is not jailed
            GameController.getInstance().rollDice();
        }
    }

    @Override
    public void playTurnAfterDice() {
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
            AIAction(GameLogic.getInstance().getTiles().get(getCurrentPosition()));
        }
    }

    @Override
    public void playTurnAfterButton() {
        while (GameLogic.rollDice);
        System.out.println("playTurnAfterButton called for "+getPlayerName()); // TODO: Debug, remove

        // TODO: Print player money
        GameController.getInstance().drawPlayerSprites(getPlayerID());
        if(consecutiveDoubles > 0) playTurn(); // If player rolled double, play turn again
        else GameLogic.getInstance().oneGameTurn();
    }

    private void AIAction(Tile tile) {
        System.out.println("AIAction called");
        if(GameLogic.getInstance().waitingOnButtons) {
            int randInt = new Random().nextInt(5);
            if(randInt > 1) { // 60%
                GameController.getInstance().purchaseTile();
            }
            else { // 40%
                GameController.getInstance().skipTurn();
            }
        }
        else {
            GameController.getInstance().skipTurn();
        }
    }
}
