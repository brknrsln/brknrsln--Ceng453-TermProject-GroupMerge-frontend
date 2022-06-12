package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;

public class TilePurchasableStreet extends TilePurchasable {

    public TilePurchasableStreet(String name, int price) {
        super(name, price);
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) {
        System.out.println("Tile action called for "+getTileName()); // TODO: Debug, remove
        if(getOwner().equals("")) { // If tile not owned
            GameController.getInstance().setTileButtonsDisable(false);
            GameLogic.getInstance().waitingOnButtons = true;
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
