package com.ceng453groupmerge.frontend.GameObjects;

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
    private ArrayList<Player> players;
    private ArrayList<Tile> tiles;
    private Integer gameId;
    private Integer roomId;
    private Integer playerIndex;
    private Integer turn = 0;
    private Boolean purchased = false;
    private Boolean multiplayer = false;
    public static Boolean waitingOnButtons = false;

//    private final GameController gameController;

    public GameLogic() {
//        this.gameController = gameController;
        this.players = new ArrayList<>();
        this.tiles = new ArrayList<>();
        initializeTiles();
        instance = this;
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void GameLogic(GameLogic gameLogic) {
        this.players = gameLogic.getPlayers();
        this.tiles = gameLogic.getTiles();
        this.gameId = gameLogic.getGameId();
        this.roomId = gameLogic.getRoomId();
        this.playerIndex = gameLogic.getPlayerIndex();
        this.turn = gameLogic.getTurn();
        this.purchased = gameLogic.getPurchased();
        this.multiplayer = gameLogic.getMultiplayer();
    }

    private Boolean getPurchased() {
        return purchased;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public void resetGame() {
        if(instance != null) {
            instance = null;
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
        return this.tiles;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public int getPlayerIndex() {
        return this.playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getOtherPlayer() {
        return (this.playerIndex +1)%2;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }


    public int getPlayerPosition(int player) {
        return this.players.get(player).getCurrentPosition();
    }

    public void setPlayerPosition(int player, int newPosition) {
        this.players.get(player).setCurrentPosition(newPosition);
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void sortPlayers() {
        players.sort(Comparator.comparing(Player::getPlayerName));
        players.forEach(player -> player.setPlayerID());
    }

    public void setGameLogicDTO() {
        new GameLogicDTO(this.gameId, this.playerIndex, this.turn, Dice.getInstance().getValue1(), Dice.getInstance().getValue2(), this.purchased);
        GameRestClient.getInstance().setGameLogicDTO(this.gameId);
    }

    public Boolean getMultiplayer() {
        return this.multiplayer;
    }

    public void setMultiplayer(boolean b) {
        this.multiplayer = b;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public boolean isPurchased() {
        return this.purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public int getCurrentBalance() {
        return this.players.get(this.playerIndex).getCurrentBalance();
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.playerIndex);
    }
}
