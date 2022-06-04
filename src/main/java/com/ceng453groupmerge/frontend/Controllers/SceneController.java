package com.ceng453groupmerge.frontend.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    public static void switchToScene(ActionEvent event, String fxmlPath) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(fxmlPath)));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
