package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.Dice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void rollDice() throws InterruptedException {
        rollButton.setDisable(true);
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
                rollButton.setDisable(false);
                dice.setValue1(dice1Value);
                dice.setValue2(dice2Value);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
    }
}
