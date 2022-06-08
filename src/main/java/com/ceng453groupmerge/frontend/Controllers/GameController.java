package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import com.ceng453groupmerge.frontend.GameObjects.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    @FXML
    private GridPane gameGridPane;

    @FXML
    private Button rollButton;

    @FXML
    private HBox go;

    @FXML
    private HBox sincan;

    @FXML
    private HBox asti;

    @FXML
    private HBox pursaklar;

    @FXML
    private HBox jail;

    @FXML
    private HBox polatli;

    @FXML
    private HBox tcdd;

    @FXML
    private HBox ayas;

    @FXML
    private HBox incomeTax;

    @FXML
    private HBox golbasi;

    @FXML
    private HBox ankaray;

    @FXML
    private HBox beypazari;

    @FXML
    private HBox goToJail;

    @FXML
    private HBox yenimahalle;

    @FXML
    private HBox esenboga;

    @FXML
    private HBox cankaya;

    private static GameController instance;

    private int position;

    private ArrayList<ImageView> playerSprites = new ArrayList<>();

    private GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    @FXML
    private void initialize() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(50);
                GameLogic gameLogic = GameLogic.getInstance();
                gameLogic.startGame();

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @FXML
    private synchronized void rollDice() throws InterruptedException {
        SceneController.setDiceNodeVisibility(true);
        rollButton.setDisable(true);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                rollButton.setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        DiceController.getInstance().rollDice();
    }

    @FXML
    public void addPlayerSprite(int player) {
        ImageView playerSprite = new ImageView(new Image(getClass().getResourceAsStream("/images/player/player" + player + ".png")));
        playerSprite.setFitHeight(45);
        playerSprite.setFitWidth(45);
        playerSprites.add(playerSprite);
        go.getChildren().add(playerSprite);
    }

    public void drawPlayerSprite() throws IOException {
        removeAllPlayerSprites();
        for(Player player : GameLogic.getInstance().getPlayers()) {
            position = player.getCurrentPosition();
            ImageView playerSprite = playerSprites.get(player.getPlayerID());
            switch (position) {
                case 0:
                    playerSprite = (ImageView) go.getChildren().get(0);
                    break;
                case 1:
                    sincan.getChildren().add(playerSprite);
                    break;
                case 2:
                    asti.getChildren().add(playerSprite);
                    break;
                case 3:
                    pursaklar.getChildren().add(playerSprite);
                    break;
                case 4:
                    jail.getChildren().add(playerSprite);
                    break;
                case 5:
                    polatli.getChildren().add(playerSprite);
                    break;
                case 6:
                    tcdd.getChildren().add(playerSprite);
                    break;
                case 7:
                    ayas.getChildren().add(playerSprite);
                    break;
                case 8:
                    incomeTax.getChildren().add(playerSprite);
                    break;
                case 9:
                    golbasi.getChildren().add(playerSprite);
                    break;
                case 10:
                    ankaray.getChildren().add(playerSprite);
                    break;
                case 11:
                    beypazari.getChildren().add(playerSprite);
                    break;
                case 12:
                    goToJail.getChildren().add(playerSprite);
                    break;
                case 13:
                    yenimahalle.getChildren().add(playerSprite);
                    break;
                case 14:
                    esenboga.getChildren().add(playerSprite);
                    break;
                case 15:
                    cankaya.getChildren().add(playerSprite);
                    break;
            }
        }
    }

    private void removeAllPlayerSprites() {
        go.getChildren().clear();
        sincan.getChildren().clear();
        asti.getChildren().clear();
        pursaklar.getChildren().clear();
        jail.getChildren().clear();
        polatli.getChildren().clear();
        tcdd.getChildren().clear();
        ayas.getChildren().clear();
        incomeTax.getChildren().clear();
        golbasi.getChildren().clear();
        ankaray.getChildren().clear();
        beypazari.getChildren().clear();
        goToJail.getChildren().clear();
        yenimahalle.getChildren().clear();
        esenboga.getChildren().clear();
        cankaya.getChildren().clear();
    }
}
