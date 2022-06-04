package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.AlertHelper;
import com.ceng453groupmerge.frontend.Constants.ErrorConstants;
import com.ceng453groupmerge.frontend.RestClients.AuthRestClient;
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
public class LeaderboardController {
    @FXML
    public TextField nameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button allTimeButton;
    @FXML
    public Button weeklyButton;
    @FXML
    public Button monthlyButton;
    @FXML
    public Button mainMenuButton;

    @FXML
    public void handleSwitchToMainMenuPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, "/mainMenu.fxml");
    }

}
