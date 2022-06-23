package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.PlayerReal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;


import java.io.IOException;

@Component
public class MainMenuController {
    @FXML
    public Button playSingleButton;

    @FXML
    public Button playMultiButton;

    @FXML
    public Button leaderboardsAllTimeButton;

    @FXML
    public Button logoutButton;

    @FXML
    public void handleSwitchToGameSingle(ActionEvent event) throws IOException {
        SceneController.switchToGameScene(event);
    }
    @FXML
    public void handleSwitchToGameMulti(ActionEvent event) throws IOException {
        SceneController.switchToMultiplayerRoomScene(event);
    }
    @FXML

    public void handleSwitchToLeaderboardsPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, LEADERBOARD_PAGE);
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        CredentialController.username = null;
        CredentialController.access_token = null;
        PlayerReal.getInstance().destructPlayer();
        SceneController.switchToScene(event, LOGIN_PAGE);
    }
}
