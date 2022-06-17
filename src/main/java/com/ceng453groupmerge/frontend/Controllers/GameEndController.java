package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import com.ceng453groupmerge.frontend.GameObjects.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.MAIN_MENU;

public class GameEndController {
    @FXML
    public Button backButton;

    @FXML
    private TextFlow textFlow;

    private static GameEndController instance;

    public GameEndController() {
        instance = this;
    }

    public static GameEndController getInstance() {
        return instance;
    }

    public void resetGame() {
        if(instance != null) {
            instance = null;
        }
    }

    @FXML
    public void handleBackButtonAction(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, MAIN_MENU);
    }

    public void endGameScoreView() {
        AtomicInteger order = new AtomicInteger(1);
        List<Player> players = GameLogic.getInstance().getPlayers().stream().sorted((p1, p2) -> p2.getScore() - p1.getScore()).collect(java.util.stream.Collectors.toList());
        players.stream().forEach(p -> {
            Text text = new Text(order.getAndIncrement() + ") "+ p.getPlayerName() + "\t: " + p.getScore() + "\n");
            text.setStyle("-fx-font-size: 20px");
            text.setStyle("-fx-font-weight: bold");
            textFlow.getChildren().add(text);
        });
    }
}
