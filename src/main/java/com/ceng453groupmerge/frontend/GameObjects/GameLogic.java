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
import java.util.LinkedHashMap;
import java.util.TimerTask;

public class GameLogic {

    private static GameLogic instance = null;
    private static ArrayList<Player> players;
    private static ArrayList<Tile> tiles;
    private static int gameId;
    private static int roomId;
    private int currentPlayer;
    private static int turn = 0;
    private static Boolean purchased = false;
    private static Boolean multiplayer = false;
    private static GameLogicDTO gameLogicDTO;
    private TimerTask timerTask;
    public static Boolean waitingOnButtons = false;

    public GameLogic() {
        players = new ArrayList<>();
        tiles = new ArrayList<>();
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void resetGame() {
        if(instance != null) {
            if(timerTask != null) {
                timerTask.cancel();
            }
            instance = null;
        }
    }

    private void initializePlayers() {
        if(multiplayer) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).setPlayerID();
                GameController.getInstance().addPlayerSprite(i);
            }
            currentPlayer = players.size()-1;
            gameLogicDTO = new GameLogicDTO(gameId, currentPlayer, turn, Dice.getInstance().getValue1(), Dice.getInstance().getValue2(), purchased);
            System.out.println("SetGameLogicDTO" );
            GameRestClient.getInstance().setGameLogicDTO(gameId, gameLogicDTO);
        } else{
            players.add(PlayerReal.getInstance());
            players.add(new PlayerAI());
            GameController.getInstance().addPlayerSprite(0);
            GameController.getInstance().addPlayerSprite(1);
        }
        currentPlayer = players.size()-1;
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

        initializePlayers();
        initializeTiles();

        if(multiplayer) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    loadGameLogicDTO();
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
            System.out.println("currentPlayerId: " + currentPlayer);
            if(currentPlayer == 0) {
                turn++;
                GameController.getInstance().addInfo("Turn: " + turn);
            }
            GameController.getInstance().addInfo(players.get(currentPlayer).getPlayerName() + "'s turn");
            PlayerReal.getInstance().setSelfTerm(currentPlayer==PlayerReal.getInstance().getPlayerID());
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
        if(multiplayer) {
            if(PlayerReal.getInstance().isSelfTerm()) gameLogicDTO.setPurchased(true);
        }
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

    public void setGameLogicDTO() {
        gameLogicDTO = new GameLogicDTO(gameId, currentPlayer, turn, Dice.getInstance().getValue1(), Dice.getInstance().getValue2(), purchased);
        if(PlayerReal.getInstance().isSelfTerm()) GameRestClient.getInstance().setGameLogicDTO(gameId, gameLogicDTO);
    }

    public void loadGameLogicDTO() {
        System.out.println("loadGameLogicDTO");
        LinkedHashMap<String, ?> gameLogicDTO1 = (LinkedHashMap<String, ?>) GameRestClient.getInstance().getGameLogicDTO(gameId);
        System.out.println("gameLogicDTO: " + gameLogicDTO1);
        if(gameLogicDTO1 != null) {
            gameLogicLoad(gameLogicDTO1);
        }
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

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    private void gameLogicLoad(LinkedHashMap<String, ?> gameLogicDTO1){
        currentPlayer = (int) gameLogicDTO1.get("currentPlayer");
        turn = (int) gameLogicDTO1.get("turn");
        purchased = (boolean) gameLogicDTO1.get("purchased");
        Dice.getInstance().setValue1((int) gameLogicDTO1.get("value1"));
        Dice.getInstance().setValue2((int) gameLogicDTO1.get("value2"));
        oneGameTurn();
    }
}
