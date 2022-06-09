package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;

import java.io.IOException;

public class TilePurchasableRailroad extends TilePurchasable {

    public TilePurchasableRailroad(String name) {
        super(name, 250);
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) throws IOException, InterruptedException {
        System.out.println("Tile action called for "+getTileName()); // TODO: Debug, remove
        if(getOwner().equals("")) { // If tile not owned
            System.out.println("No owner for "+getTileName()); // TODO: Debug, remove
            GameController.getInstance().setTileButtonsVisibility(true);
        }
        else if(!getOwner().equals(currentPlayer.getPlayerName())) { // If owner is someone else
            System.out.println("Opponent-owned for "+getTileName()); // TODO: Debug, remove
            int railroadsCount = otherPlayer.countRailroads();
            currentPlayer.subtractMoney(25*railroadsCount);
            otherPlayer.addMoney(25*railroadsCount);
            GameLogic.getInstance().skipTurn();
        }
        else {
            System.out.println("Player-owned for "+getTileName()); // TODO: Debug, remove
            GameLogic.getInstance().skipTurn();
        }
    }

    @Override
    public String getType() {
        return "railroad";
    }
}
