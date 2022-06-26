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
    private final ArrayList<Player> players;
    private final ArrayList<Tile> tiles;
    private Integer gameId;
    private Integer roomId;
    private Integer playerIndex;
    private Integer turn = 0;
    private Boolean purchased = false;
    private Boolean multiplayer = false;
    private GameLogicDTO gameLogicDTO;
    private TimerTask timerTask;
    public Boolean waitingOnButtons = false;

    public GameLogic() {
        this.players = new ArrayList<>();
        this.tiles = new ArrayList<>();
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void resetGame() {
        if(instance != null) {
            if(this.timerTask != null) {
                this.timerTask.cancel();
            }
            instance = null;
        }
    }

    private void initializePlayers() {
        if(this.multiplayer) {
            for (int i = 0; i < this.players.size(); i++) {
                this.players.get(i).setPlayerID();
                GameController.getInstance().addPlayerSprite(i);
            }
            this.playerIndex = this.players.size()-1;
            this.gameLogicDTO = new GameLogicDTO(this.gameId, this.playerIndex, this.turn, Dice.getInstance().getValue1(), Dice.getInstance().getValue2(), this.purchased);
            System.out.println("SetGameLogicDTO" );
            GameRestClient.getInstance().setGameLogicDTO(this.gameId, this.gameLogicDTO);
        } else{
            this.players.add(PlayerReal.getInstance());
            this.players.add(new PlayerAI());
            GameController.getInstance().addPlayerSprite(0);
            GameController.getInstance().addPlayerSprite(1);
            this.playerIndex = this.players.size()-1;
        }
    }

    private void initializeTiles() {
        this.tiles.add(new TileMiscGo());
        this.tiles.add(new TilePurchasableStreet("Sincan", 100));
        this.tiles.add(new TilePurchasableRailroad("Aşti"));
        this.tiles.add(new TilePurchasableStreet("Pursaklar", 150));
        this.tiles.add(new TileMiscJailCell());
        this.tiles.add(new TilePurchasableStreet("Polatlı", 200));
        this.tiles.add(new TilePurchasableRailroad("TCDD"));
        this.tiles.add(new TilePurchasableStreet("Ayaş", 250));
        this.tiles.add(new TileMiscTaxIncome());
        this.tiles.add(new TilePurchasableStreet("Gölbaşı", 300));
        this.tiles.add(new TilePurchasableRailroad("Ankaray"));
        this.tiles.add(new TilePurchasableStreet("Beypazarı", 350));
        this.tiles.add(new TileMiscGoToJail());
        this.tiles.add(new TilePurchasableStreet("Yenimahalle", 420));
        this.tiles.add(new TilePurchasableRailroad("Esenboğa"));
        this.tiles.add(new TilePurchasableStreet("Çankaya", 500));
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

    public int getOtherPlayer() {
        return (this.playerIndex +1)%2;
    }

    public void startGame() {
        SceneController.setDiceNodeVisibility(true);
        GameController.getInstance().setRollButtonDisable(true);
        GameController.getInstance().setTileButtonsDisable(true);

        this.initializePlayers();
        this.initializeTiles();

        if(this.multiplayer) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("TimerTask");
                    System.out.println(gameId);
                    if(!PlayerReal.getInstance().isSelfTerm())
                        loadGameLogicDTO();
                    else{
                        System.out.println("SelfTerm");
                    }
                }
            };
            new java.util.Timer().schedule(timerTask, 0, 2000);
        } else {
            oneGameTurn();
        }
    }

    public void oneGameTurn() {
        System.out.println(this.players);
        if(this.players.get(this.playerIndex).getCurrentBalance()>=0) { // Main loop runs while both this.players are not bankrupt
            this.playerIndex = (this.playerIndex +1)%this.players.size();
            System.out.println("this.currentPlayerId: " + this.playerIndex);
            if(this.playerIndex == 0) {
                this.turn++;
                GameController.getInstance().addInfo("Turn: " + this.turn);
            }
            GameController.getInstance().addInfo(this.players.get(this.playerIndex).getPlayerName() + "'s turn");
            PlayerReal.getInstance().setSelfTerm(this.playerIndex ==PlayerReal.getInstance().getPlayerID());
            this.players.get(this.playerIndex).playTurn();
        }
        else {
            endGame();
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void purchaseTile() {
        if(this.multiplayer) {
            if(PlayerReal.getInstance().isSelfTerm()) this.gameLogicDTO.setPurchased(true);
        }
        Player currentPlayer = getPlayers().get(getPlayerIndex());
        currentPlayer.purchaseProperty(getTiles().get(currentPlayer.getCurrentPosition()));
        String text = currentPlayer.getPlayerName() + " purchased " + getTiles().get(currentPlayer.getCurrentPosition()).getTileName();
        GameController.getInstance().addInfo(text);
        GameController.getInstance().updateScoreBoard();
        this.skipTurn();
    }


    public void skipTurn() {
//        System.out.println("Skipped");
        GameController.getInstance().setTileButtonsDisable(true);
        String text = getPlayers().get(getPlayerIndex()).getPlayerName() + " ended their turn";
        GameController.getInstance().addInfo(text);
        this.waitingOnButtons = false;
        this.players.get(this.playerIndex).playTurnAfterButton();
    }

    public void endGame() {
        if(this.multiplayer) {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setGameId(this.gameId);
            scoreDTO.setRoomId(this.roomId);
            this.players.forEach(player -> {
                scoreDTO.addPlayer(player.getPlayerName(), player.getCurrentBalance());
            });
            MultiplayerRestClient.getInstance().sendScore(scoreDTO);
        } else {
            Player player1 = this.players.get(0);
            Player player2 = this.players.get(1);
            GameRestClient.getInstance().save(player1.getPlayerName(), player2.getPlayerName(), String.valueOf(player1.getScore()), String.valueOf(player2.getScore()));
            SceneController.setEndGameNodeVisibility(true);
        }
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
        this.players.sort(Comparator.comparing(Player::getPlayerName));
    }

    public void setGameLogicDTO() {
        this.gameLogicDTO = new GameLogicDTO(this.gameId, this.playerIndex, this.turn, Dice.getInstance().getValue1(), Dice.getInstance().getValue2(), this.purchased);
        if(PlayerReal.getInstance().isSelfTerm()) GameRestClient.getInstance().setGameLogicDTO(this.gameId, this.gameLogicDTO);
    }

    public void loadGameLogicDTO() {
        System.out.println("loadGameLogicDTO with gameId: " + this.gameId);
        LinkedHashMap<String, ?> gameLogicDTO1 = (LinkedHashMap<String, ?>) GameRestClient.getInstance().getGameLogicDTO(gameId);
        System.out.println("gameLogicDTO: " + gameLogicDTO1);
        if(gameLogicDTO1 != null) {
            this.gameLogicLoad(gameLogicDTO1);
        }
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

    private void gameLogicLoad(LinkedHashMap<String, ?> gameLogicDTO1){
        this.playerIndex = (int) gameLogicDTO1.get("currentPlayer");
        this.turn = (int) gameLogicDTO1.get("turn");
        this.purchased = (boolean) gameLogicDTO1.get("purchased");
        Dice.getInstance().setValue1((int) gameLogicDTO1.get("value1"));
        Dice.getInstance().setValue2((int) gameLogicDTO1.get("value2"));
        this.oneGameTurn();
    }
}
