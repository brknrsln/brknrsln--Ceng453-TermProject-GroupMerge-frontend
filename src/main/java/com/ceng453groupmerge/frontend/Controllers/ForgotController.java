package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.Utilities.AlertHelper;
import com.ceng453groupmerge.frontend.RestClients.AuthRestClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.springframework.stereotype.Component;
import static com.ceng453groupmerge.frontend.Constants.ErrorConstants.*;
import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;



import java.io.IOException;

@Component
public class ForgotController {

    private AuthRestClient authRestClient = AuthRestClient.getInstance();

    @FXML
    public TextField emailField;

    @FXML
    public Button submitButton;
    @FXML
    public Button loginPageButton;


    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();
        if(emailField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    EMPTY_EMAIL_ERROR);
            return;
        }

        try{
            authRestClient.forgot(emailField.getText());
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Success",
                    "Sent password reset link to " + emailField.getText());
        }catch (Exception e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, EMAIL_FAILED, e.getMessage());
        }

    }

    @FXML
    public void handleSwitchToLoginPage(ActionEvent event) throws IOException {
        SceneController.switchToScene(event, LOGIN_PAGE);
    }
}
