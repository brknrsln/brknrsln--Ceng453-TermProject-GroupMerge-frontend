package com.ceng453groupmerge.frontend.GameObjects;

public class TilePurchasableStreet extends TilePurchasable {

    public TilePurchasableStreet(String name, int price) {
        super(name, price);
    }

    @Override
    public void tileAction(Player currentPlayer) {
        if(getOwner().equals("")) { // If tile not owned
            // TODO: Bring up purchase buttons
        }
        else if(!getOwner().equals(currentPlayer.getPlayerName())) { // If owner is someone else
            currentPlayer.subtractMoney(getPrice()/10);
        }
    }

    @Override
    public String getType() {
        return "street";
    }
}
