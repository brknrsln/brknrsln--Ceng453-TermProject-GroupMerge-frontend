package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.CredentialController;

import java.io.IOException;
import java.util.ArrayList;

public class GameLogic {

    private static GameLogic gameLogic;
    private ArrayList<Player> players;
    private ArrayList<Tile> tiles;
    private int currentPlayer = 0;

    public static synchronized GameLogic getInstance() throws IOException {
        if(gameLogic == null) {
            gameLogic = new GameLogic();
        }
        return gameLogic;
    }

    private void initializePlayers() throws IOException {
        players = new ArrayList<>();
        players.add(new PlayerReal(CredentialController.username));
        players.add(new PlayerAI());
    }

    private void initializeTiles() {
        tiles = new ArrayList<>();
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

    public void startGame() throws IOException {
        initializePlayers();
        initializeTiles();

        while(players.get(currentPlayer).getCurrentBalance()>=0) { // Main loop runs while both players are not bankrupt
            // TODO: Print "PLAYER X'S TURN" on screen
            players.get(currentPlayer).playTurn();
            if(players.get(currentPlayer).getCurrentBalance()>=0) { // Move game forward if current player still isn't bankrupt
                currentPlayer = (currentPlayer+1)%2;
            }
        }

        // TODO: Calculate endgame score, and save via the API.
    }
}
