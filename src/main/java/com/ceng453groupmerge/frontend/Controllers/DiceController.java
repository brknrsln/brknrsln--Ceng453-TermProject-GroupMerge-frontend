package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.Dice;
import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class DiceController {
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;
    @FXML
    private Button rollButton;

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
    public void rollDice()  {
        rollButton.setDisable(true);
        int dice1Value1 = random.nextInt(6) + 1;
        int dice2Value1 = random.nextInt(6) + 1;
        dice.setValue1(dice1Value1);
        dice.setValue2(dice2Value1);
        double rotate = 24;
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
                rollButton.setDisable(false); // TODO: Remove
                dice1.setImage(dice.getDiceImage(dice1Value1));
                dice2.setImage(dice.getDiceImage(dice2Value1));
                dice1.rotateProperty().set(dice1.rotateProperty().get() + rotate);
                dice2.rotateProperty().set(dice2.rotateProperty().get() + rotate);

                GameLogic.getInstance().waitForDice = false;
                GameLogic.getInstance().waitForDiceLock.notifyAll();

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
