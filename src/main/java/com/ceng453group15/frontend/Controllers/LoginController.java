package com.ceng453group15.frontend.Controllers;

import com.ceng453group15.frontend.AlertHelper;
import com.ceng453group15.frontend.RestClients.AuthRestClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
    public void handleSubmitButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();
        if(nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        try{
            AuthRestClient.login(nameField.getText(), passwordField.getText());
            SceneController.switchToMainMenu(event);
        }catch (Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", "Login Failed!");
        }
    }

    @FXML
    void handleSwitchToRegisterPage(ActionEvent event) throws IOException {
        SceneController.switchToRegisterPage(event);
    }

    @FXML
    void handleSwitchToForgotPage(ActionEvent event) throws IOException {
        SceneController.switchToForgotPage(event);
    }
}
