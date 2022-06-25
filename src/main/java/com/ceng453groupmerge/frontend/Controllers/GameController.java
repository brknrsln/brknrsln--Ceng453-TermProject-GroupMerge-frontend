package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    @FXML
    private GridPane gameGridPane;

    @FXML
    private VBox scoreBoardVBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox infoVbox;

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

    private static final GameLogic gameLogic = GameLogic.getInstance();

    public GameController() {
        instance = this;
    }

    public static GameController getInstance() {
        if(instance == null) instance = new GameController();
        return instance;
    }
    public void resetGame() {
        if(instance != null) {
            instance = null;
        }
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

    public void drawPlayerSprites() {
        for(int player=0; player<2; player++) {
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
        setInfoVbox();
    }

    public void printTileOwner(int position){
        String owner = gameLogic.getTiles().get(position).getOwner();
        Label label = new Label(owner);
        label.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1415FA; -fx-font: Comic Sans MS;");
        switch (position) {
            case 1:
                label.rotateProperty().setValue(90);
                gameGridPane.add(label, 0, 3);
                break;
            case 2:
                label.rotateProperty().setValue(90);
                gameGridPane.add(label, 0, 2);
                break;
            case 3:
                label.rotateProperty().setValue(90);
                gameGridPane.add(label, 0, 1);
                break;
            case 5:
                label.rotateProperty().setValue(180);
                gameGridPane.add(label, 1, 0);
                break;
            case 6:
                label.rotateProperty().setValue(180);
                gameGridPane.add(label, 2, 0);
                break;
            case 7:
                label.rotateProperty().setValue(180);
                gameGridPane.add(label, 3, 0);
                break;
            case 9:
                label.rotateProperty().setValue(270);
                gameGridPane.add(label, 4, 1);
                break;
            case 10:
                label.rotateProperty().setValue(270);
                gameGridPane.add(label, 4, 2);
                break;
            case 11:
                label.rotateProperty().setValue(270);
                gameGridPane.add(label, 4, 3);
                break;
            case 13:
                gameGridPane.add(label, 3, 4);
                break;
            case 14:
                gameGridPane.add(label, 2, 4);
                break;
            case 15:
                gameGridPane.add(label, 1, 4);
                break;
        }
    }

    public void addInfo(String info){
        Text text1 = new Text(info);
        text1.setStyle("-fx-font-size: 15px; -fx-font-weight: italic; -fx-font-family: Comic Sans MS;");
        text1.setWrappingWidth(300);
        infoVbox.getChildren().add(text1);
    }

    private void setInfoVbox() {
        infoVbox.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setVvalue(scrollPane.getVmax());
        });

        GameLogic.getInstance().getPlayers().stream()
                .forEach(player -> {
                    Label username = new Label(player.getPlayerName() + ": ");
                    username.setStyle("-fx-font-size: 14px; -fx-font-weight: italic; -fx-text-fill: #1415FA; -fx-font: Comic Sans MS;");
                    Label score = new Label(player.getCurrentBalance() + "");
                    score.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1415FA; -fx-font: Comic Sans MS;");
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.getChildren().addAll(username, score);
                    scoreBoardVBox.getChildren().add(hBox);
                });
    }

    public void updateScoreBoard() {
        for(int i = 0; i < GameLogic.getInstance().getPlayers().size(); i++){
            Label score = (Label) ((HBox)scoreBoardVBox.getChildren().get(i)).getChildren().get(1);
            score.setText(GameLogic.getInstance().getPlayers().get(i).getCurrentBalance() + "");
        }
    }
}
