package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.AlertHelper;
import static com.ceng453groupmerge.frontend.Constants.ErrorConstants.*;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;
import com.ceng453groupmerge.frontend.RestClients.LeaderboardRestClient;

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
    public Button monthlyButton;
    @FXML
    public Button weeklyButton;
    @FXML
    public Button mainMenuButton;

    @FXML
    public void handleAllTimeButtonAction(ActionEvent event) {
        Window owner = monthlyButton.getScene().getWindow();
        try{
            LeaderboardRestClient.getAllTimeLeaderboard();
        }catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }
    }

    @FXML
    public void handleMonthlyButtonAction(ActionEvent event) {
        Window owner = monthlyButton.getScene().getWindow();
        try{
            LeaderboardRestClient.getMonthlyLeaderboard();
        }catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }

    }

    @FXML
    public void handleWeeklyButtonAction(ActionEvent event) {
        Window owner = monthlyButton.getScene().getWindow();
        try{
            LeaderboardRestClient.getWeeklyLeaderboard();
        }catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }

    }


    @FXML
    public void handleSwitchToMainMenu(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, MAIN_MENU);
    }

}
