package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.AlertHelper;
import static com.ceng453groupmerge.frontend.Constants.ErrorConstants.*;
import com.ceng453groupmerge.frontend.RestClients.AuthRestClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RegisterController {
    @FXML
    public TextField nameField;

    @FXML
    public TextField emailField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button submitButton;
    @FXML
    public Button loginPageButton;

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();
        if(nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    EMPTY_NAME_ERROR);
            return;
        }
        if(emailField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    EMPTY_EMAIL_ERROR);
            return;
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    EMPTY_PASSWORD_ERROR);
            return;
        }

        try{
            AuthRestClient.register(nameField.getText(), emailField.getText(),passwordField.getText());
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Success!",
                    "Registration successful for " + nameField.getText());
        }catch (Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", REGISTER_FAILED);
        }

    }

    @FXML
    public void handleSwitchToLoginPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, LOGIN_PAGE);
    }
}
