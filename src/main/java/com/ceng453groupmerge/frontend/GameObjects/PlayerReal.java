package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.DiceController;
import com.ceng453groupmerge.frontend.Controllers.GameController;
import com.ceng453groupmerge.frontend.Controllers.SceneController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Random;

public class PlayerReal extends Player {

    private GameLogic gameLogic;

    private static Random random = new Random();

    public PlayerReal(String name) throws IOException {
        setPlayerName(name);
        setPlayerID(playerCount++);
        gameLogic = GameLogic.getInstance();
    }

    @Override
    public void playTurn() {
        System.out.println("playTurn called for "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime() == 0) { // If player is not jailed
            GameController.getInstance().setRollButtonVisibility(true);
            System.out.println("Waiting for dice"); // TODO: Debug, remove
        }
    }

    @Override
    public void playTurnAfterDice() throws IOException, InterruptedException {
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
            GameController.getInstance().drawPlayerSprites();

            Player otherPlayer = gameLogic.getPlayers().get(gameLogic.getOtherPlayer());

            gameLogic.getTiles().get(getCurrentPosition()).tileAction(this, otherPlayer);
            System.out.println("Waiting for buttons for "+getPlayerName());
        }
    }

    @Override
    public void playTurnAfterButton() throws IOException, InterruptedException {
        System.out.println("playTurnAfterButton called for "+getPlayerName()); // TODO: Debug, remove

        // TODO: Print player money
        GameController.getInstance().drawPlayerSprites();

        if(consecutiveDoubles > 0) playTurn(); // If player rolled double, play turn again
        else gameLogic.oneGameTurn();
    }
}
