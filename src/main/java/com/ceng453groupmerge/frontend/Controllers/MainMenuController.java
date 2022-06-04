package com.ceng453groupmerge.frontend.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;


import java.io.IOException;

@Component
public class MainMenuController {
    public Button playSingleButton;
    public Button playMultiButton;
    public Button leaderboardsAllTimeButton;

    @FXML
    public void handleSwitchToGameSingle(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, GAME);
    }
    @FXML
    public void handleSwitchToGameMulti(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, GAME);
    }
    @FXML

    public void handleSwitchToLeaderboardsPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, LEADERBOARD_PAGE);
    }
}
