package com.ceng453groupmerge.frontend.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    private static GridPane gameRoot;
    private static GridPane gameBoardNode;
    private static VBox diceNode;
    private static VBox infoNode;

    public static void switchToScene(ActionEvent event, String fxmlPath) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(fxmlPath)));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToGameScene(ActionEvent event, String gamePath, String dicePath, String infoPath) throws IOException {
        gameRoot = new GridPane();
        gameRoot.setAlignment(Pos.CENTER);
        gameRoot.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameRoot.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameRoot.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameRoot.setStyle("-fx-background-color: #dcf4dd");

        gameBoardNode = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(gamePath)));
        diceNode = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(dicePath)));
        infoNode = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(infoPath)));

        gameRoot.add(infoNode, 0, 0);
        gameRoot.add(gameBoardNode, 0, 0);
        gameRoot.add(diceNode, 0, 0);
        diceNode.setVisible(false);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(gameRoot);
        stage.setScene(scene);
        stage.show();
    }

    public static void setDiceNodeVisibility(boolean visible) {
        diceNode.setVisible(visible);
    }

    public static void addToInfoNode(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-family: Calibri");
        infoNode.getChildren().add(label);
    }

    public static void clearInfoNode() {
        infoNode.getChildren().clear();
    }
}
