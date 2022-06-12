package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.Dice;
import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DiceController {
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;

    private static int dice1Value;
    private static int dice2Value;

    private Dice dice;
    private static DiceController diceController;

    Random random = new Random();

    public DiceController() {
        dice = Dice.getInstance();
        diceController = this;
    }

    public static DiceController getInstance() {
        return diceController;
    }


    @FXML
    public void rollDice() {
        System.out.println("Rolled dice");
        GameController.getInstance().setRollButtonDisable(true);
        int dice1ValueFinal = random.nextInt(6) + 1;
        int dice2ValueFinal = random.nextInt(6) + 1;
        dice.setValue1(dice1ValueFinal);
        dice.setValue2(dice2ValueFinal);
        double rotate = 24;

        dice1.setImage(dice.getDiceImage(dice1ValueFinal));
        dice2.setImage(dice.getDiceImage(dice2ValueFinal));

        RotateTransition rt1 = new RotateTransition(Duration.millis(100), dice1);
        RotateTransition rt2 = new RotateTransition(Duration.millis(100), dice2);

        rt1.setByAngle(360);
        rt2.setByAngle(360);

        rt1.setCycleCount(10);
        rt2.setCycleCount(10);

        rt2.setOnFinished(e -> GameLogic.getInstance().getPlayers().get(GameLogic.getInstance().getCurrentPlayer()).playTurnAfterDice());

        rt1.play();
        rt2.play();

        /*Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    dice1Value = random.nextInt(6) + 1;
                    dice2Value = random.nextInt(6) + 1;
                    dice1.setImage(dice.getDiceImage(dice1Value));
                    dice2.setImage(dice.getDiceImage(dice2Value));
                    dice1.rotateProperty().set(dice1.rotateProperty().get() + rotate);
                    dice2.rotateProperty().set(dice2.rotateProperty().get() + rotate);
                    Thread.sleep(50);
                }
                dice1.setImage(dice.getDiceImage(dice1ValueFinal));
                dice2.setImage(dice.getDiceImage(dice2ValueFinal));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();*/
    }
}
