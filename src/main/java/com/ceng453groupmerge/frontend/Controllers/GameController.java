package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    @FXML
    private GridPane gameGridPane;

    @FXML
    private Button start;

    @FXML
    private Button rollButton;

    @FXML
    private Button purchase;

    @FXML
    private Button skip;

    @FXML
    private VBox go;

    @FXML
    private VBox sincan;

    @FXML
    private VBox asti;

    @FXML
    private VBox pursaklar;

    @FXML
    private VBox jail;

    @FXML
    private VBox polatli;

    @FXML
    private VBox tcdd;

    @FXML
    private VBox ayas;

    @FXML
    private VBox incomeTax;

    @FXML
    private VBox golbasi;

    @FXML
    private VBox ankaray;

    @FXML
    private VBox beypazari;

    @FXML
    private VBox goToJail;

    @FXML
    private VBox yenimahalle;

    @FXML
    private VBox esenboga;

    @FXML
    private VBox cankaya;

    private static GameController instance;

    private ArrayList<ImageView> playerSprites = new ArrayList<>();

    private HashMap<Integer, HBox> gameBoard = new HashMap<>();

    private static GameLogic gameLogic;

    public GameController() {
        instance = this;
        gameLogic = GameLogic.getInstance();
    }

    public static GameController getInstance() {
        if(instance == null) instance = new GameController();
        return instance;
    }

    public void setRollButtonDisable(boolean disable) {
        rollButton.setDisable(disable);
    }
    public void setTileButtonsDisable(boolean disable) {
        purchase.setDisable(disable);
        skip.setDisable(disable);
    }

    @FXML
    public void rollDice() {
        DiceController.getInstance().rollDice();
    }

    @FXML
    public void purchaseTile(){
        gameLogic.purchaseTile();
    }

    @FXML
    public void skipTurn() {
        gameLogic.skipTurn();
    }

    public void addPlayerSprite(int player) {
        ImageView playerSprite = new ImageView(new Image(getClass().getResourceAsStream("/images/player/player" + (player + 1) + ".png")));
        playerSprite.setFitHeight(45);
        playerSprite.setFitWidth(45);
        playerSprites.add(playerSprite);
        spawnPlayer(go, player, playerSprite);
    }

    public void drawPlayerSprites(int player) {
        ImageView playerSprite = playerSprites.get(player);
        HBox hBox = gameBoard.get(player);
        hBox.getChildren().remove(playerSprite);
        if(hBox.getChildren().size() == 0) {
            VBox parent = (VBox) hBox.getParent();
            parent.getChildren().remove(hBox);
        }
        int position = GameLogic.getInstance().getPlayerPosition(player);
        switch (position) {
            case 0:
                spawnPlayer(go, player, playerSprite);
                break;
            case 1:
                spawnPlayer(sincan, player, playerSprite);
                break;
            case 2:
                spawnPlayer(asti, player, playerSprite);
                break;
            case 3:
                spawnPlayer(pursaklar, player, playerSprite);
                break;
            case 4:
                spawnPlayer(jail, player, playerSprite);
                break;
            case 5:
                spawnPlayer(polatli, player, playerSprite);
                break;
            case 6:
                spawnPlayer(tcdd, player, playerSprite);
                break;
            case 7:
                spawnPlayer(ayas, player, playerSprite);
                break;
            case 8:
                spawnPlayer(incomeTax, player, playerSprite);
                break;
            case 9:
                spawnPlayer(golbasi, player, playerSprite);
                break;
            case 10:
                spawnPlayer(ankaray, player, playerSprite);
                break;
            case 11:
                spawnPlayer(beypazari, player, playerSprite);
                break;
            case 12:
                spawnPlayer(goToJail, player, playerSprite);
                break;
            case 13:
                spawnPlayer(yenimahalle, player, playerSprite);
                break;
            case 14:
                spawnPlayer(esenboga, player, playerSprite);
                break;
            case 15:
                spawnPlayer(cankaya, player, playerSprite);
                break;
        }
    }

    private void spawnPlayer(VBox vbox, int player, ImageView playerSprite) {
        int count = vbox.getChildren().size();
        if(count == 0){
            HBox hBox = new HBox();
            hBox.setAlignment(javafx.geometry.Pos.CENTER);
            hBox.getChildren().add(playerSprite);
            gameBoard.put(player, hBox);
            vbox.getChildren().add(hBox);
        } else {
            if(((HBox) vbox.getChildren().get(count - 1)).getChildren().size() == 3){
                HBox hBox = new HBox();
                hBox.setAlignment(javafx.geometry.Pos.CENTER);
                hBox.getChildren().add(playerSprite);
                gameBoard.put(player, hBox);
                vbox.getChildren().add(hBox);
            } else {
                HBox hBox = (HBox) vbox.getChildren().get(count - 1);
                hBox.getChildren().add(playerSprite);
                gameBoard.put(player, hBox);
            }
        }
    }

    @FXML
    public void startGame() {
        start.setVisible(false);
        gameLogic.startGame();
    }
}
