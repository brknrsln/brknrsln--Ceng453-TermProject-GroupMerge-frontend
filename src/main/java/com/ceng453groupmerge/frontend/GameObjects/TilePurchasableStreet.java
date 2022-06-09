package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;

import java.io.IOException;

public class TilePurchasableStreet extends TilePurchasable {

    public TilePurchasableStreet(String name, int price) {
        super(name, price);
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
            currentPlayer.subtractMoney(getPrice()/10);
            otherPlayer.addMoney(getPrice()/10);
            GameLogic.getInstance().skipTurn();
        }
        else {
            System.out.println("Player-owned for "+getTileName()); // TODO: Debug, remove
            GameLogic.getInstance().skipTurn();
        }
    }

    @Override
    public String getType() {
        return "street";
    }
}
