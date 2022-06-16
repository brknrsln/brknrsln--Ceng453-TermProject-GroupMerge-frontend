package com.ceng453groupmerge.frontend.Controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;

public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static VBox diceNode;
    private static VBox endGameNode;

    public static void switchToScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(fxmlPath)));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToGameScene(ActionEvent event) throws IOException {
        final BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
        final BooleanProperty ninePressed = new SimpleBooleanProperty(false);
        final BooleanBinding ctrlAndNinePressed = ctrlPressed.and(ninePressed);

        ctrlAndNinePressed.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TODO: implement ctr+9 functionality
            }
        });

        VBox gameRoot = new VBox();
        gameRoot.setAlignment(Pos.CENTER);
        gameRoot.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameRoot.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameRoot.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameRoot.setStyle("-fx-background-color: #dcf4dd");

        ImageView titleNode  = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(TITLE)));
        GridPane gameBoardNode = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(GAME)));
        diceNode        = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(DICE)));
        endGameNode     = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(END_GAME)));


        gameRoot.getChildren().add(titleNode);
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameBoard.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameBoard.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        gameBoard.add(diceNode, 0, 1);
        gameBoard.add(gameBoardNode, 0, 1);
        gameBoard.add(endGameNode, 0, 1);
        gameRoot.getChildren().add(gameBoard);
        diceNode.setVisible(false);
        endGameNode.setVisible(false);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(gameRoot);

        scene.setOnKeyPressed(event1 -> {
            System.out.println(event1.getCode());
            if (event1.getCode().toString().equals("ESCAPE")) {
                try {
                    SceneController.switchToScene(event, MAIN_MENU);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event1.getCode() == javafx.scene.input.KeyCode.CONTROL) {
                ctrlPressed.set(true);
            } else if ( event1.getCode() == KeyCode.DIGIT9) {
                ninePressed.set(true);
            }
        });

        scene.setOnKeyReleased(event1 -> {
            if (event1.getCode() == javafx.scene.input.KeyCode.CONTROL) {
                ctrlPressed.set(false);
            } else if ( event1.getCode() == KeyCode.DIGIT9) {
                ninePressed.set(false);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void setDiceNodeVisibility(boolean visible) {
        diceNode.setVisible(visible);
    }

    public static void setEndGameNodeVisibility(boolean visible) {
        GameEndController.getInstance().endGameScoreView();
        endGameNode.setVisible(visible);
    }
}
