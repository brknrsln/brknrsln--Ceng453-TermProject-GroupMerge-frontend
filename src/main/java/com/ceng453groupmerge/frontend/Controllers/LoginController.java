package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.AlertHelper;
import com.ceng453groupmerge.frontend.Constants.ErrorMessages;
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
public class LoginController {
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
                    ErrorMessages.EMPTY_NAME_ERROR);
            return;
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    ErrorMessages.EMPTY_PASSWORD_ERROR);
            return;
        }

        try{
            AuthRestClient.login(nameField.getText(), passwordField.getText());
            SceneController.switchToScene(event, "/mainMenu.fxml");
        }catch (Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", "Login Failed!");
        }
    }

    @FXML
    void handleSwitchToRegisterPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, "/registerPage.fxml");
    }

    @FXML
    void handleSwitchToForgotPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, "/forgotPage.fxml");
    }
}
