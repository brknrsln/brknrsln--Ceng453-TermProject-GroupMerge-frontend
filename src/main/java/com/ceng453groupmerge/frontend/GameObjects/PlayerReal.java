package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

import java.util.*;

public class PlayerReal extends Player {

    private static Random random = new Random();

    private static PlayerReal instance = null;

    public PlayerReal() {
        if(instance == null) {
            instance = this;
        }
    }

    public PlayerReal(String name) {
        setPlayerName(name);
    }

    public static PlayerReal getInstance() {
        if(instance == null) {
            instance = new PlayerReal();
        }
        return instance;
    }

    @Override
    public void playTurn() {
        System.out.println("playTurn called for "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime() == 0) { // If player is not jailed
            if(instance != this) {
                GameController.getInstance().rollDice();
            } else {
                GameController.getInstance().setRollButtonDisable(false);
                System.out.println("Waiting for dice"); // TODO: Debug, remove
            }
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

            if(!GameLogic.getInstance().waitingOnButtons) GameLogic.getInstance().skipTurn();
            else System.out.println("Waiting for buttons for "+getPlayerName());
        }
    }

    public void destructPlayer() {
        instance = null;
    }
}
