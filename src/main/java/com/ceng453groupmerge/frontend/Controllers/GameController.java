package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import javafx.fxml.FXML;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class GameController {
    @FXML
    private void initialize() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(50);
                GameLogic gameLogic = GameLogic.getInstance();
                gameLogic.startGame();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
