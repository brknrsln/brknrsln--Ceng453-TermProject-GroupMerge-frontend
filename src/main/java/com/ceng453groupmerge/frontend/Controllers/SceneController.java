package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import com.ceng453groupmerge.frontend.GameObjects.Player;
import javafx.animation.RotateTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.*;

public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static VBox diceNode;
    private static VBox endGameNode;
    static final BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
    static final BooleanProperty ninePressed = new SimpleBooleanProperty(false);
    static final BooleanBinding ctrlAndNinePressed = ctrlPressed.and(ninePressed);
    public static void switchToScene(ActionEvent event, String fxmlPath) throws IOException {
        resetGame();
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(fxmlPath)));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        setStage();
        stage.show();
    }

    public static void switchToGameScene(ActionEvent event) throws IOException {
        ctrlAndNinePressed.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TODO: This listener is not added if we end and then restart the game. Why??
                GameController.getInstance().addInfo("CHEAT CODE ENABLED");
                ArrayList<Player> players = GameLogic.getInstance().getPlayers();
                Player player;
                if(players.get(0).getPlayerName().equals(CredentialController.username)) player = players.get(0);
                else player = players.get(1);
                player.setCurrentPosition(8);
                player.subtractMoney(10000000);
                RotateTransition rt = new RotateTransition(Duration.millis(300), new VBox());
                rt.setOnFinished(event1 -> {
                    GameLogic.getInstance().endGame();
                });
                rt.play();
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
        stage.setScene(scene);


        scene.setOnKeyPressed(event1 -> {
            System.out.println(event1.getCode());
            if (event1.getCode().toString().equals("ESCAPE")) {
                GameEndController.getInstance().backButton.fire();
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
        setStage();
        stage.show();
    }

    public static void setDiceNodeVisibility(boolean visible) {
        diceNode.setVisible(visible);
    }

    public static void setEndGameNodeVisibility(boolean visible) {
        endGameNode.setVisible(visible);
        GameEndController.getInstance().endGameScoreView();
    }

    private static void resetGame() {
        if(GameLogic.getInstance() != null) GameLogic.getInstance().resetGame();
        if(GameController.getInstance() != null) GameController.getInstance().resetGame();
        if(GameEndController.getInstance() != null) GameEndController.getInstance().resetGame();
        if(DiceController.getInstance() != null) DiceController.getInstance().resetGame();
    }

    private static void setStage(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primaryScreenBounds.getWidth()  - stage.getWidth()) / 2);
        stage.setY((primaryScreenBounds.getHeight()  - stage.getHeight()) / 2);
    }
}
