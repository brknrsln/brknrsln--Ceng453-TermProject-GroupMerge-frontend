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

    private int dice1Value;
    private int dice2Value;

    private Dice dice = new Dice();

    Random random = new Random();

    @FXML
    private void rollDice() {
        rollButton.setDisable(true);
        dice1.setImage(dice.getDiceRandom());
        dice2.setImage(dice.getDiceRandom());

        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    dice1Value = random.nextInt(6) + 1;
                    dice2Value = random.nextInt(6) + 1;
                    dice1.setImage(dice.getDiceImage(dice1Value));
                    dice2.setImage(dice.getDiceImage(dice2Value));
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
    }
}
