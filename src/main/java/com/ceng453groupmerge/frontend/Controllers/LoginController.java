package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.AlertHelper;
import com.ceng453groupmerge.frontend.RestClients.AuthRestClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.ceng453groupmerge.frontend.Constants.ErrorConstants.*;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;

@Component
public class LoginController implements Initializable {
    private  AuthRestClient authRestClient = AuthRestClient.getInstance();

    @FXML
    public TextField nameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button submitButton;
    @FXML
    public Button registerPageButton;
    @FXML
    public Button forgotPageButton;

    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();
        if(nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    EMPTY_NAME_ERROR);
            return;
        } else {
            Preferences prefs = Preferences.userRoot().node("Monopoly");
            prefs.put("name", nameField.getText());
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    EMPTY_PASSWORD_ERROR);
            return;
        }

        try{
            CredentialController.access_token = authRestClient.login(nameField.getText(), passwordField.getText());
            CredentialController.username = nameField.getText();
            SceneController.switchToScene(event, MAIN_MENU);
        }catch (Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, LOGIN_FAILED, e.getMessage());
        }
    }

    @FXML
    void handleSwitchToRegisterPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, REGISTER_PAGE);
    }

    @FXML
    void handleSwitchToForgotPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, FORGOT_PAGE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.setText(Preferences.userRoot().node("Monopoly").get("name", ""));
    }
}
