package com.ceng453group15.frontend.Controllers;

import com.ceng453group15.frontend.AlertHelper;
import com.ceng453group15.frontend.RestClients.AuthRestClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
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
