package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import com.ceng453groupmerge.frontend.GameObjects.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    @FXML
    private GridPane gameGridPane;

    @FXML
    private Button rollButton;

    @FXML
    private Button purchase;

    @FXML
    private Button skip;

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

    private ArrayList<ImageView> playerSprites = new ArrayList<>();

    public GameController() {
        instance = this;
    }

    public static GameController getInstance() {
        if(instance == null) instance = new GameController();
        return instance;
    }

    public void setRollButtonVisibility(boolean visible) {
        rollButton.setDisable(!visible);
    }
    public void setTileButtonsVisibility(boolean visible) {
        purchase.setDisable(!visible);
        skip.setDisable(!visible);
    }

    @FXML
    public void rollDice() throws IOException, InterruptedException {
        GameLogic.getInstance().rollDice();
    }

    @FXML
    public void purchaseTile() throws IOException, InterruptedException {
        GameLogic.getInstance().purchaseTile();
    }

    @FXML
    public void skipTurn() throws IOException, InterruptedException {
        GameLogic.getInstance().skipTurn();
    }

    public void addPlayerSprite(int player) {
        ImageView playerSprite = new ImageView(new Image(getClass().getResourceAsStream("/images/player/player" + player + ".png")));
        playerSprite.setFitHeight(45);
        playerSprite.setFitWidth(45);
        playerSprites.add(playerSprite);
    }

    public void drawPlayerSprites() throws IOException {
        removeAllPlayerSprites();
        try {
            for(Player player : GameLogic.getInstance().getPlayers()) {
                int position = player.getCurrentPosition();
                ImageView playerSprite = playerSprites.get(player.getPlayerID());
                switch (position) {
                    case 0:
                        go.getChildren().add(playerSprite);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeAllPlayerSprites() {
        for (Node node : gameGridPane.getChildren()) {
            if (node instanceof HBox) {
                ((HBox) node).getChildren().clear();
            }
        }
    }
}
