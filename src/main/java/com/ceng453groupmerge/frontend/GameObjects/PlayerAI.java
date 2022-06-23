package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.DiceController;
import com.ceng453groupmerge.frontend.Controllers.GameController;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class PlayerAI extends Player {


    public PlayerAI() {
        setPlayerName("AI_OVERLORD");
        setPlayerID();
    }

    @Override
    public void playTurn() {
        System.out.println("playTurn called for "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime() == 0) { // If player is not jailed
            GameController.getInstance().rollDice();
        }
        else {
            GameController.getInstance().skipTurn();
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
            GameController.getInstance().skipTurn();
        }
        else {
            int oldPosition = getCurrentPosition();
            movePlayer(diceValue);
            if(getCurrentPosition()<oldPosition) {
                addMoney(100); // Moved over starting point
            }
            // TODO: Print player money

            Player otherPlayer = GameLogic.getInstance().getPlayers().get(GameLogic.getInstance().getOtherPlayer());

            GameLogic.getInstance().getTiles().get(getCurrentPosition()).tileAction(this, otherPlayer);
            AIAction(GameLogic.getInstance().getTiles().get(getCurrentPosition()));
        }
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
