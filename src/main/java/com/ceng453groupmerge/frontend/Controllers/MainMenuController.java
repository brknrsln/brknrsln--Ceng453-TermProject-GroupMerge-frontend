package com.ceng453groupmerge.frontend.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainMenuController {
    public Button playSingleButton;
    public Button playMultiButton;
    public Button leaderboardsAllTimeButton;

    @FXML
    public void handleSwitchToGameSingle(ActionEvent event) throws IOException {
        SceneController.switchToGameSingle(event);
    }
    @FXML
    public void handleSwitchToGameMulti(ActionEvent event) throws IOException {
        SceneController.switchToGameMulti(event);
    }
    @FXML
    public void handleSwitchToLeaderboardsAllTimePage(ActionEvent event) throws IOException {
        SceneController.switchToLeaderboardsAllTimePage(event);
    }
}
