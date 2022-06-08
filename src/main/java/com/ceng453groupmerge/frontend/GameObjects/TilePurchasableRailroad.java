package com.ceng453groupmerge.frontend.GameObjects;

import java.io.IOException;

public class TilePurchasableRailroad extends TilePurchasable {

    public TilePurchasableRailroad(String name) {
        super(name, 250);
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) throws IOException {
        if(getOwner().equals("")) { // If tile not owned
            // TODO: Bring up purchase buttons if player, figure out how to handle if AI_OVERLORD
            GameLogic.getInstance().waitForPurchaseOrSkip = true;
        }
        else if(!getOwner().equals(currentPlayer.getPlayerName())) { // If owner is someone else
            int railroadsCount = otherPlayer.countRailroads();
            currentPlayer.subtractMoney(25*railroadsCount);
            otherPlayer.addMoney(25*railroadsCount);
        }
    }

    @Override
    public String getType() {
        return "railroad";
    }
}
