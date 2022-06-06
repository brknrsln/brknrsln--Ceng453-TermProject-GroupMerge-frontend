package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.CredentialController;

import java.io.IOException;
import java.util.ArrayList;

public class GameLogic {

    private static GameLogic gameLogic;
    private ArrayList<Player> players;
    private ArrayList<Tile> tiles;

    public static synchronized GameLogic getInstance() throws IOException {
        if(gameLogic == null) {
            gameLogic = new GameLogic();
        }
        return gameLogic;
    }

    private void initializePlayers() {
        System.out.println("Initializing players"); // TODO: Debug, remove
        players = new ArrayList<>();
        players.add(new PlayerReal(CredentialController.username));
        players.add(new PlayerAI());
        System.out.println("Initialized "+players.size()+" players "+ players.get(0).getPlayerName()+" and "+players.get(1).getPlayerName()); // TODO: Debug, remove
    }

    private void initializeTiles() {
        System.out.println("Initializing tiles"); // TODO: Debug, remove
        tiles = new ArrayList<>();
        tiles.add(new TileMisc("Go"));
        tiles.add(new TilePurchasable("Sincan", 100));
        tiles.add(new TilePurchasable("Aşti", 250));
        tiles.add(new TilePurchasable("Pursaklar", 150));
        tiles.add(new TileMisc("JailCell"));
        tiles.add(new TilePurchasable("Polatlı", 200));
        tiles.add(new TilePurchasable("TCDD", 250));
        tiles.add(new TilePurchasable("Ayaş", 250));
        tiles.add(new TileMisc("TaxIncome"));
        tiles.add(new TilePurchasable("Gölbaşı", 300));
        tiles.add(new TilePurchasable("Ankaray", 250));
        tiles.add(new TilePurchasable("Beypazarı", 350));
        tiles.add(new TileMisc("GoToJail"));
        tiles.add(new TilePurchasable("Yenimahalle", 420));
        tiles.add(new TilePurchasable("Esenboğa", 250));
        tiles.add(new TilePurchasable("Çankaya", 500));
        System.out.println("Initialized "+tiles.size()+" tiles"); // TODO: Debug, remove
    }

    public GameLogic() {
    }

    public void startGame() {
        initializePlayers();
        initializeTiles();

        while(players.get(0).getCurrentBalance()>=0 && players.get(1).getCurrentBalance()>=0) { // Main loop runs while both players are not bankrupt
            players.get(0).playTurn();
            players.get(1).playTurn();
        }

        // TODO: Calculate endgame score, and save via the API.
    }
}
