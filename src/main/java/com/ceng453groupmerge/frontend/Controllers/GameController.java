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

    @FXML
    private void rollDice() throws IOException, InterruptedException {
        System.out.println("Rolled dice"); // TODO: Debug, remove
        rollButton.setDisable(true);
        SceneController.setDiceNodeVisibility(true);
        DiceController.getInstance().rollDice();
    }

    @FXML
    private void purchaseTile() throws IOException {
        System.out.println("Purchased"); // TODO: Debug, remove
        GameLogic gameLogic = GameLogic.getInstance();
        Player currentPlayer = gameLogic.getPlayers().get(gameLogic.getCurrentPlayer());
        currentPlayer.purchaseProperty(gameLogic.getTiles().get(currentPlayer.getCurrentPosition()));
        gameLogic.waitForPurchaseOrSkip = false;
        Platform.exitNestedEventLoop(gameLogic.waitForPurchaseOrSkipLock, new Object());

    }

    @FXML
    private void skipTile() throws IOException {
        System.out.println("Skipped"); // TODO: Debug, remove
        GameLogic.getInstance().waitForPurchaseOrSkip = false;
        Platform.exitNestedEventLoop(GameLogic.getInstance().waitForPurchaseOrSkipLock, new Object());
    }

    @FXML
    private void initialize() throws IOException, InterruptedException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GameLogic.getInstance().startGame();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void addPlayerSprite(int player) {
        ImageView playerSprite = new ImageView(new Image(getClass().getResourceAsStream("/images/player/player" + player + ".png")));
        playerSprite.setFitHeight(45);
        playerSprite.setFitWidth(45);
        playerSprites.add(playerSprite);
    }

    public void drawPlayerSprites() throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                removeAllPlayerSprites();
                try {
                    for(Player player : GameLogic.getInstance().getPlayers()) {
                        int position = player.getCurrentPosition();
                        System.out.println("Player "+player.getPlayerName()+" is drawn in position "+position); // TODO: Debug, remove
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
        });

    }

    private void removeAllPlayerSprites() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Node node : gameGridPane.getChildren()) {
                    if (node instanceof HBox) {
                        ((HBox) node).getChildren().clear();
                    }
                }
            }
        });

//        go.getChildren().clear();
//        sincan.getChildren().clear();
//        asti.getChildren().clear();
//        pursaklar.getChildren().clear();
//        jail.getChildren().clear();
//        polatli.getChildren().clear();
//        tcdd.getChildren().clear();
//        ayas.getChildren().clear();
//        incomeTax.getChildren().clear();
//        golbasi.getChildren().clear();
//        ankaray.getChildren().clear();
//        beypazari.getChildren().clear();
//        goToJail.getChildren().clear();
//        yenimahalle.getChildren().clear();
//        esenboga.getChildren().clear();
//        cankaya.getChildren().clear();
    }
}
