package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.DiceController;
import com.ceng453groupmerge.frontend.Controllers.GameController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Random;

public class PlayerAI extends Player {

    private GameLogic gameLogic;

    public PlayerAI() throws IOException {
        setPlayerName("AI_OVERLORD");
        setPlayerID(playerCount++);
        gameLogic = GameLogic.getInstance();
    }

    @Override
    public void playTurn() throws IOException, InterruptedException {
        System.out.println("playTurn called for "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime() == 0) { // If player is not jailed
            GameController.getInstance().rollDice();
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
            AIAction(gameLogic.getTiles().get(getCurrentPosition()));
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

    private void AIAction(Tile tile) throws IOException, InterruptedException {
        if(!tile.getType().equals("misc") && getCurrentBalance() >= tile.getPrice()) {
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
