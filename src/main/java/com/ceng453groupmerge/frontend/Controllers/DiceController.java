package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.Dice;
import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DiceController {
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;

    private Dice dice;
    private static DiceController instance;

    Random random = new Random();

    private Roller clock;

    public DiceController() {
        dice = Dice.getInstance();
        instance = this;
        clock = new Roller();
    }

    public static DiceController getInstance() {
        return instance;
    }

    public void resetGame() {
        if(instance != null) {
            instance = null;
        }
    }

    @FXML
    public void rollDice() {
        System.out.println("Rolled dice");
        GameController.getInstance().setRollButtonDisable(true);
        clock.start();
    }

    private class Roller extends AnimationTimer {
        private long lastUpdate = 0;
        private long INTERVAL = 1000000000 / 50;
        private int MAX_ROLLS = 20;
        private double angle = 360/MAX_ROLLS;

        private int counter = 0;
        private int dice1Value;
        private int dice2Value;
        @Override
        public void handle(long now) {
            if (now - lastUpdate >= INTERVAL) {
                dice1Value = random.nextInt(6) + 1;
                dice2Value = random.nextInt(6) + 1;

                dice1.setImage(dice.getDiceImage(dice1Value));
                dice2.setImage(dice.getDiceImage(dice2Value));

                dice1.rotateProperty().set(dice1.rotateProperty().get() + angle);
                dice2.rotateProperty().set(dice2.rotateProperty().get() + angle);

                lastUpdate = now;
                counter++;
                if (counter >= MAX_ROLLS) {
                    dice.setValue1(dice1Value);
                    dice.setValue2(dice2Value);
                    this.stop();
                    GameLogic.getInstance().getPlayers().get(GameLogic.getInstance().getCurrentPlayer()).playTurnAfterDice();
                    counter = 0;
                }
            }
        }
    }
}
