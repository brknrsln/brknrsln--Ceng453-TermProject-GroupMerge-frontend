package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.AlertHelper;
import static com.ceng453groupmerge.frontend.Constants.ErrorConstants.*;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;
import com.ceng453groupmerge.frontend.RestClients.LeaderboardRestClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Component
public class LeaderboardController {
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
        Window owner = allTimeButton.getScene().getWindow();
        try{
            ArrayList<Object> arr = (ArrayList<Object>) LeaderboardRestClient.getAllTimeLeaderboard();
            for(Object obj:arr) {
                LinkedHashMap<String,?> hashMap = (LinkedHashMap) obj;
                System.out.println(hashMap.get("player")+" "+hashMap.get("player_score"));
            }
        }catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }
    }

    @FXML
    public void handleMonthlyButtonAction(ActionEvent event) {
        Window owner = monthlyButton.getScene().getWindow();
        try{
            ArrayList<Object> arr = (ArrayList<Object>) LeaderboardRestClient.getMonthlyLeaderboard();
            for(Object obj:arr) {
                LinkedHashMap<String,?> hashMap = (LinkedHashMap) obj;
                System.out.println(hashMap.get("player")+" "+hashMap.get("player_score"));
            }
        }catch(Exception e){
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }

    }

    @FXML
    public void handleWeeklyButtonAction(ActionEvent event) {
        Window owner = weeklyButton.getScene().getWindow();
        try{
            ArrayList<Object> arr = (ArrayList<Object>) LeaderboardRestClient.getWeeklyLeaderboard();
            for(Object obj:arr) {
                LinkedHashMap<String,?> hashMap = (LinkedHashMap) obj;
                System.out.println(hashMap.get("player")+" "+hashMap.get("player_score"));
            }
        }catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }

    }


    @FXML
    public void handleSwitchToMainMenu(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, MAIN_MENU);
    }

}
