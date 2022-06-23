package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.CredentialController;
import com.ceng453groupmerge.frontend.Controllers.GameController;
import com.ceng453groupmerge.frontend.DTO.GameLogicDTO;
import com.ceng453groupmerge.frontend.DTO.ScoreDTO;
import com.ceng453groupmerge.frontend.RestClients.GameRestClient;
import com.ceng453groupmerge.frontend.Controllers.SceneController;
import com.ceng453groupmerge.frontend.RestClients.MultiplayerRestClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TimerTask;

public class GameLogic {

    private static GameLogic instance = null;
    private ArrayList<Player> players;
    private ArrayList<Tile> tiles;
    private int gameId;
    private int roomId;
    private int currentPlayer = 1;
    public boolean waitingOnButtons = false;
    private int turn = 0;
    private Boolean multiplayer = false;
    private GameLogicDTO gameLogicDTO;
    private TimerTask timerTask;

    private GameLogic() {
        players = new ArrayList<>();
        tiles = new ArrayList<>();
    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void resetGame() {
        if(instance != null) {
            timerTask.cancel();
            instance = null;
        }
    }

    private void initializePlayers() {
        if(multiplayer) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).setPlayerID();
                GameController.getInstance().addPlayerSprite(i);
            }
        } else{
            players.add(PlayerReal.getInstance());
            players.add(new PlayerAI());
            GameController.getInstance().addPlayerSprite(0);
            GameController.getInstance().addPlayerSprite(1);
        }
    }

    private void initializeTiles() {
        tiles.add(new TileMiscGo());
        tiles.add(new TilePurchasableStreet("Sincan", 100));
        tiles.add(new TilePurchasableRailroad("Aşti"));
        tiles.add(new TilePurchasableStreet("Pursaklar", 150));
        tiles.add(new TileMiscJailCell());
        tiles.add(new TilePurchasableStreet("Polatlı", 200));
        tiles.add(new TilePurchasableRailroad("TCDD"));
        tiles.add(new TilePurchasableStreet("Ayaş", 250));
        tiles.add(new TileMiscTaxIncome());
        tiles.add(new TilePurchasableStreet("Gölbaşı", 300));
        tiles.add(new TilePurchasableRailroad("Ankaray"));
        tiles.add(new TilePurchasableStreet("Beypazarı", 350));
        tiles.add(new TileMiscGoToJail());
        tiles.add(new TilePurchasableStreet("Yenimahalle", 420));
        tiles.add(new TilePurchasableRailroad("Esenboğa"));
        tiles.add(new TilePurchasableStreet("Çankaya", 500));
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getOtherPlayer() {
        return (currentPlayer+1)%2;
    }

    public void startGame() {
        SceneController.setDiceNodeVisibility(true);
        GameController.getInstance().setRollButtonDisable(true);
        GameController.getInstance().setTileButtonsDisable(true);

//        resetGame();

        initializePlayers();
        initializeTiles();
        if(multiplayer) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    gameLogicDTO = (GameLogicDTO) GameRestClient.getInstance().getGameLogicDTO(gameId);
                    if(gameLogicDTO != null) {
                        loadGameLogicDTO();
                    }
                }
            };
            new java.util.Timer().schedule(timerTask, 0, 2000);
        } else {
            oneGameTurn();
        }
    }

    public void oneGameTurn() {
        if(players.get(currentPlayer).getCurrentBalance()>=0) { // Main loop runs while both players are not bankrupt
            currentPlayer = (currentPlayer+1)%players.size();
            if(currentPlayer == 0) {
                turn++;
                GameController.getInstance().addInfo("Turn: " + turn);
            }
            GameController.getInstance().addInfo(players.get(currentPlayer).getPlayerName() + "'s turn");
            players.get(currentPlayer).playTurn();
        }
        else {
            endGame();
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void purchaseTile() {
//        System.out.println("Purchased");
        Player currentPlayer = getPlayers().get(getCurrentPlayer());
        currentPlayer.purchaseProperty(getTiles().get(currentPlayer.getCurrentPosition()));
        String text = currentPlayer.getPlayerName() + " purchased " + getTiles().get(currentPlayer.getCurrentPosition()).getTileName();
        GameController.getInstance().addInfo(text);
        GameController.getInstance().updateScoreBoard();
        skipTurn();
    }


    public void skipTurn() {
//        System.out.println("Skipped");
        GameController.getInstance().setTileButtonsDisable(true);
        String text = getPlayers().get(getCurrentPlayer()).getPlayerName() + " ended their turn";
        GameController.getInstance().addInfo(text);
        waitingOnButtons = false;
        players.get(currentPlayer).playTurnAfterButton();
    }

    public void endGame() {
        if(multiplayer) {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setGameId(gameId);
            scoreDTO.setRoomId(roomId);
            players.forEach(player -> {
                scoreDTO.addPlayer(player.getPlayerName(), player.getCurrentBalance());
            });
            MultiplayerRestClient.getInstance().sendScore(scoreDTO);
        } else {
            Player player1 = players.get(0);
            Player player2 = players.get(1);
            GameRestClient.getInstance().save(player1.getPlayerName(), player2.getPlayerName(), String.valueOf(player1.getScore()), String.valueOf(player2.getScore()));
            SceneController.setEndGameNodeVisibility(true);
        }
    }

    public int getPlayerPosition(int player) {
        return players.get(player).getCurrentPosition();
    }

    public void setPlayerPosition(int player, int newPosition) {
        players.get(player).setCurrentPosition(newPosition);
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void sortPlayers() {
        players.sort(Comparator.comparing(Player::getPlayerName));
    }

    private void setGameLogicDTO() {
        this.gameLogicDTO = new GameLogicDTO(players, tiles, gameId, currentPlayer, turn, Dice.getInstance().getValue1(), Dice.getInstance().getValue2());
        GameRestClient.getInstance().setGameLogicDTO(gameLogicDTO);
    }

    protected void loadGameLogicDTO() {
        players = gameLogicDTO.getPlayers();
        tiles = gameLogicDTO.getTiles();
        currentPlayer = gameLogicDTO.getCurrentPlayer();
        turn = gameLogicDTO.getTurn();
        Dice.getInstance().setValue1(gameLogicDTO.getValue1());
        Dice.getInstance().setValue2(gameLogicDTO.getValue2());
        oneGameTurn();
    }

    public Boolean getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(boolean b) {
        multiplayer = b;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
