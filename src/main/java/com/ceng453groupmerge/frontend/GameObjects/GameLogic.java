package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.CredentialController;
import com.ceng453groupmerge.frontend.Controllers.DiceController;
import com.ceng453groupmerge.frontend.Controllers.GameController;
import com.ceng453groupmerge.frontend.RestClients.GameRestClient;
import com.ceng453groupmerge.frontend.Controllers.SceneController;

import java.util.ArrayList;

public class GameLogic {

    private static GameLogic gameLogic;
    private ArrayList<Player> players;
    private ArrayList<Tile> tiles;
    private int currentPlayer = 1;
    public boolean waitingOnButtons = false;

    private GameLogic() {
        players = new ArrayList<>();
        tiles = new ArrayList<>();
    }

    public static synchronized GameLogic getInstance() {
        if(gameLogic == null) {
            gameLogic = new GameLogic();
        }
        return gameLogic;
    }

    private void initializePlayers() {
        players.add(new PlayerReal(CredentialController.username));
        GameController.getInstance().addPlayerSprite(0);
        players.add(new PlayerAI());
        GameController.getInstance().addPlayerSprite(1);
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

        oneGameTurn();
    }

    public void oneGameTurn() {
        if(players.get(currentPlayer).getCurrentBalance()>=0) { // Main loop runs while both players are not bankrupt
            if(players.get(currentPlayer).getCurrentBalance()>=0) { // Move game forward if current player still isn't bankrupt
                currentPlayer = (currentPlayer+1)%2;
            }
            SceneController.clearInfoNode();
            SceneController.addToInfoNode(players.get(currentPlayer).getPlayerName() + "'s turn");
            players.get(currentPlayer).playTurn();
        }
        else {
            endGame();
        }
    }

    public void rollDice() {
        GameController.getInstance().setRollButtonDisable(true);
        DiceController.getInstance().rollDice();
    }

    public void purchaseTile() {
        System.out.println("Purchased"); // TODO: Debug, remove
        Player currentPlayer = getPlayers().get(getCurrentPlayer());
        currentPlayer.purchaseProperty(getTiles().get(currentPlayer.getCurrentPosition()));
        skipTurn();
    }


    public void skipTurn() {
        System.out.println("Skipped"); // TODO: Debug, remove
        GameController.getInstance().setTileButtonsDisable(true);
        waitingOnButtons = false;
        players.get(currentPlayer).playTurnAfterButton();
    }

    public void endGame() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        GameRestClient.getInstance().save(player1.getPlayerName(), player2.getPlayerName(), String.valueOf(player1.calculateScore()), String.valueOf(player2.calculateScore()));
        // TODO: Print endgame message
    }

    public int getPlayerPosition(int player) {
        return players.get(player).getCurrentPosition();
    }

    public void setPlayerPosition(int player, int newPosition) {
        players.get(player).setCurrentPosition(newPosition);
    }
}
