package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.Dice;
import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
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
        if(!GameLogic.getInstance().gameHasStarted) {
//            GameLogic.getInstance().startGame();
            GameLogic.getInstance().gameHasStarted = true;
        }
        GameController.getInstance().setRollButtonDisable(true);
        int dice1ValueFinal = random.nextInt(6) + 1;
        int dice2ValueFinal = random.nextInt(6) + 1;
        dice.setValue1(dice1ValueFinal);
        dice.setValue2(dice2ValueFinal);
        double rotate = 24;
        GameLogic.rollDice = true;
        Thread thread = new Thread(() -> {
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
                GameLogic.rollDice = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        GameLogic.getInstance().getPlayers().get(GameLogic.getInstance().getCurrentPlayer()).playTurnAfterDice();

    }
}
