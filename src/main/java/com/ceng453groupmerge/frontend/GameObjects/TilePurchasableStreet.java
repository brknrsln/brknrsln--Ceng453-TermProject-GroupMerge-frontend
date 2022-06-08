package com.ceng453groupmerge.frontend.GameObjects;

import java.io.IOException;

public class TilePurchasableStreet extends TilePurchasable {

    public TilePurchasableStreet(String name, int price) {
        super(name, price);
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) throws IOException {
        if(getOwner().equals("")) { // If tile not owned
            // TODO: Bring up purchase buttons if player, figure out how to handle if AI_OVERLORD
            GameLogic.getInstance().waitForPurchaseOrSkip = true;
        }
        else if(!getOwner().equals(currentPlayer.getPlayerName())) { // If owner is someone else
            currentPlayer.subtractMoney(getPrice()/10);
            otherPlayer.addMoney(getPrice()/10);
        }
    }

    @Override
    public String getType() {
        return "street";
    }
}
