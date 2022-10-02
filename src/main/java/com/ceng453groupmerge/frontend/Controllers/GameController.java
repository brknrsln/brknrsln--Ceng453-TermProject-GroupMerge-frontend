package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.DTO.GameLogicDTO;
import com.ceng453groupmerge.frontend.DTO.ScoreDTO;
import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import com.ceng453groupmerge.frontend.GameObjects.Player;
import com.ceng453groupmerge.frontend.GameObjects.PlayerAI;
import com.ceng453groupmerge.frontend.GameObjects.PlayerReal;
import com.ceng453groupmerge.frontend.RestClients.GameRestClient;
import com.ceng453groupmerge.frontend.RestClients.MultiplayerRestClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.util.*;

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
    private Button quitGame;

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

    private final ArrayList<ImageView> playerSprites = new ArrayList<>();

    private final HashMap<Integer, HBox> gameBoard = new HashMap<>();

    public final GameLogic gameLogic = GameLogic.getInstance();
    private TimerTask timerTask;

    public GameController() {
        instance = this;
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    public void resetGame() {
        if (instance != null) {
            if (this.timerTask != null) {
                this.timerTask.cancel();
            }
            instance = null;
        }
    }

    @FXML
    public void startGame() {
        start.setVisible(false);
        SceneController.setDiceNodeVisibility(true);
        setRollButtonDisable(true);
        setTileButtonsDisable(true);
        if (gameLogic.getMultiplayer()) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        updateGame();
                    });
                }
            };
            new java.util.Timer().schedule(timerTask, 0, 1000);
            gameLogic.setPlayerIndex(gameLogic.getPlayers().size() - 1);
            gameLogic.setGameLogicDTO();

        } else {
            gameLogic.addPlayer(PlayerReal.getInstance());
            gameLogic.addPlayer(new PlayerAI());
            gameLogic.getPlayers().forEach(player -> {
                player.setPlayerID();
            });
            gameLogic.setPlayerIndex(gameLogic.getPlayers().size() - 1);
        }
        setInfoVbox();
        addPlayerSprites();
    }

    private void updateGame() {
        if (PlayerReal.getInstance().isSelfTerm()) {
            return;
        } else {
            LinkedHashMap<String, ?> gameLogicDTO = (LinkedHashMap<String, ?>) GameRestClient.getInstance().getGameLogic();
            if (gameLogicDTO != null) {
                GameLogicDTO.getInstance().setGameLogicDTO(gameLogicDTO);
                if (gameLogic.getTurn() == gameLogic.getPlayerIndex()) {
                    setRollButtonDisable(false);
                    setTileButtonsDisable(false);
                } else {
                    setRollButtonDisable(true);
                    setTileButtonsDisable(true);
                }
                oneGameTurn();
            }
        }
    }

    @FXML
    public void rollDice() {
        DiceController.getInstance().rollDice();
    }

    @FXML
    public void purchaseTile() {
        if (gameLogic.getMultiplayer()) {
            if (PlayerReal.getInstance().isSelfTerm()) GameLogicDTO.getInstance().setPurchased(true);
        }
        Player currentPlayer = gameLogic.getCurrentPlayer();
        currentPlayer.purchaseProperty(gameLogic.getTiles().get(currentPlayer.getCurrentPosition()));
        String text = currentPlayer.getPlayerName() + " purchased " + gameLogic.getTiles().get(currentPlayer.getCurrentPosition()).getTileName();
        addInfo(text);
        updateScoreBoard();
        skipTurn();
    }

    @FXML
    public void skipTurn() {
        setTileButtonsDisable(true);
        String text = gameLogic.getCurrentPlayer().getPlayerName() + " ended their turn";
        addInfo(text);
        GameLogic.waitingOnButtons = false;
        gameLogic.getCurrentPlayer().playTurnAfterButton();
    }

    public void addPlayerSprites() {
        for (int i = 0; i < gameLogic.getPlayers().size(); i++) {
            ImageView playerSprite = new ImageView();
            playerSprite.setFitHeight(45);
            playerSprite.setFitWidth(45);
            playerSprite.setPreserveRatio(true);
            playerSprite.setImage(new Image(getClass().getResourceAsStream("/images/player/player" + (i + 1) + ".png")));
            playerSprites.add(playerSprite);
            spawnPlayer(go, i, playerSprite);
        }
    }

    public void drawPlayerSprites() {
        for (int player = 0; player < 2; player++) {
            ImageView playerSprite = playerSprites.get(player);
            HBox hBox = gameBoard.get(player);
            hBox.getChildren().remove(playerSprite);
            if (hBox.getChildren().size() == 0) {
                VBox parent = (VBox) hBox.getParent();
                parent.getChildren().remove(hBox);
            }
            int position = gameLogic.getPlayerPosition(player);
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

    public void setRollButtonDisable(boolean disable) {
        rollButton.setDisable(disable);
    }

    public void setTileButtonsDisable(boolean disable) {
        purchase.setDisable(disable);
        skip.setDisable(disable);
    }

    private void spawnPlayer(VBox vbox, int player, ImageView playerSprite) {
        int count = vbox.getChildren().size();
        if (count == 0) {
            HBox hBox = new HBox();
            hBox.setAlignment(javafx.geometry.Pos.CENTER);
            hBox.getChildren().add(playerSprite);
            gameBoard.put(player, hBox);
            vbox.getChildren().add(hBox);
        } else {
            if (((HBox) vbox.getChildren().get(count - 1)).getChildren().size() == 3) {
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

    public void printTileOwner(int position) {
        String owner = gameLogic.getTiles().get(position).getOwner();
        Label label = new Label(owner);
        label.setStyle("-fx-font-size: 15; -fx-stroke-width: bold; -fx-text-fill: #1415FA; -fx-font-family: Comic Sans MS;");
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

    public void addInfo(String info) {
        Text text1 = new Text(info);
        text1.setStyle("-fx-font-size: 15; -fx-stroke-width: italic; -fx-font-family: Comic Sans MS;");
        text1.setWrappingWidth(300);
        infoVbox.getChildren().add(text1);
    }

    private void setInfoVbox() {
        infoVbox.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setVvalue(scrollPane.getVmax());
        });

        gameLogic.getPlayers().stream()
                .forEach(player -> {
                    Label username = new Label(player.getPlayerName() + ": ");
                    username.setStyle("-fx-font-size: 14; -fx-stroke-width: italic; -fx-text-fill: #1415FA; -fx-font-family: Comic Sans MS;");
                    Label score = new Label(player.getCurrentBalance() + "");
                    score.setStyle("-fx-font-size: 14; -fx-stroke-width: bold; -fx-text-fill: #1415FA; -fx-font-family: Comic Sans MS;");
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.getChildren().addAll(username, score);
                    scoreBoardVBox.getChildren().add(hBox);
                });
    }

    public void updateScoreBoard() {
        for (int i = 0; i < gameLogic.getPlayers().size(); i++) {
            Label score = (Label) ((HBox) scoreBoardVBox.getChildren().get(i)).getChildren().get(1);
            score.setText(gameLogic.getPlayers().get(i).getCurrentBalance() + "");
        }
    }

    public void oneGameTurn() {
        if (gameLogic.getCurrentBalance() >= 0) { // Main loop runs while both this.players are not bankrupt
            gameLogic.setPlayerIndex((gameLogic.getPlayerIndex() + 1) % gameLogic.getPlayers().size());
            if (gameLogic.getPlayerIndex() == 0) {
                gameLogic.setTurn(gameLogic.getTurn() + 1);
                addInfo("Turn: " + gameLogic.getTurn());
            }
            addInfo(gameLogic.getCurrentPlayer().getPlayerName() + "'s turn");
            PlayerReal.getInstance().setSelfTerm(gameLogic.getPlayerIndex() == PlayerReal.getInstance().getPlayerID());
            gameLogic.getCurrentPlayer().playTurn();
        } else {
            endGame();
        }
    }

    public void endGame() {
        if (gameLogic.getMultiplayer()) {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setGameId(gameLogic.getGameId());
            scoreDTO.setRoomId(gameLogic.getRoomId());
            gameLogic.getPlayers().forEach(player -> {
                scoreDTO.addPlayer(player.getPlayerName(), player.getCurrentBalance());
            });
            MultiplayerRestClient.getInstance().sendScore(scoreDTO);
        } else {
            Player player1 = gameLogic.getPlayers().get(0);
            Player player2 = gameLogic.getPlayers().get(1);
            GameRestClient.getInstance().save(player1.getPlayerName(), player2.getPlayerName(), String.valueOf(player1.getScore()), String.valueOf(player2.getScore()));
            SceneController.setEndGameNodeVisibility(true);
        }
    }

    @FXML
    public void quitGame(ActionEvent event) throws IOException {
        if (gameLogic.getMultiplayer()) {
            timerTask.cancel();
            gameLogic.resetGame();
            SceneController.switchToMultiplayerRoomScene(event);
        } else {
            SceneController.setEndGameNodeVisibility(true);
        }
    }
}
