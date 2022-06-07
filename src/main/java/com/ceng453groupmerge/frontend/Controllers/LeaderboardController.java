package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.AlertHelper;
import com.ceng453groupmerge.frontend.RestClients.AuthRestClient;
import com.ceng453groupmerge.frontend.RestClients.LeaderboardRestClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.ceng453groupmerge.frontend.Constants.ErrorConstants.SERVER_NOT_RESPONDING;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.MAIN_MENU;

@Component
public class LeaderboardController {
    private LeaderboardRestClient leaderboardRestClient = LeaderboardRestClient.getInstance();

    @FXML
    public Button allTimeButton;
    @FXML
    public Button monthlyButton;
    @FXML
    public Button weeklyButton;
    @FXML
    public Button mainMenuButton;
    @FXML
    public GridPane gridPane;

    public LeaderboardController() throws IOException {
    }

    @FXML
    public void handleAllTimeButtonAction(ActionEvent event) {
        Window owner = allTimeButton.getScene().getWindow();
        try{
            ArrayList<Object> arr = (ArrayList<Object>) leaderboardRestClient.getAllTimeLeaderboard();
            updateLeaderboard(arr);
        }catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }
    }

    @FXML
    public void handleMonthlyButtonAction(ActionEvent event) {
        Window owner = monthlyButton.getScene().getWindow();
        try{
            ArrayList<Object> arr = (ArrayList<Object>) leaderboardRestClient.getMonthlyLeaderboard();
            updateLeaderboard(arr);
        }catch(Exception e){
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }

    }

    @FXML
    public void handleWeeklyButtonAction(ActionEvent event) {
        Window owner = weeklyButton.getScene().getWindow();
        try{
            ArrayList<Object> arr = (ArrayList<Object>) leaderboardRestClient.getWeeklyLeaderboard();
            updateLeaderboard(arr);
        }catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", SERVER_NOT_RESPONDING);
        }

    }


    @FXML
    public void handleSwitchToMainMenu(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, MAIN_MENU);
    }


    private void updateLeaderboard(ArrayList<Object> arr) {
        gridPane.getChildren().clear();
        gridPane.getChildren().removeAll(gridPane.getChildren());
        int count = 0;
        for(Object obj:arr) {
            LinkedHashMap<String,?> hashMap = (LinkedHashMap) obj;
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            VBox vBox3 = new VBox();
            if(count%2==0) {
                vBox1.setStyle("-fx-background-color: #94F799");
                vBox2.setStyle("-fx-background-color: #94F799");
                vBox3.setStyle("-fx-background-color: #94F799");
            } else {
                vBox1.setStyle("-fx-background-color: #DCF4DD");
                vBox2.setStyle("-fx-background-color: #DCF4DD");
                vBox3.setStyle("-fx-background-color: #DCF4DD");
            }
            vBox1.alignmentProperty().setValue(Pos.CENTER_LEFT);
            vBox2.alignmentProperty().setValue(Pos.CENTER_LEFT);
            vBox3.alignmentProperty().setValue(Pos.CENTER_LEFT);
            gridPane.addRow(count++,vBox1, vBox2, vBox3);
            Label label1 = new Label(count+"");
            label1.paddingProperty().setValue(new Insets(0,0,0,5));
            vBox1.getChildren().add(label1);
            vBox2.getChildren().add(new Label(hashMap.get("player").toString()));
            vBox3.getChildren().add(new Label(hashMap.get("player_score").toString()));
//            System.out.println(hashMap.get("player")+" "+hashMap.get("player_score"));
//            count++;
        }
    }
}
