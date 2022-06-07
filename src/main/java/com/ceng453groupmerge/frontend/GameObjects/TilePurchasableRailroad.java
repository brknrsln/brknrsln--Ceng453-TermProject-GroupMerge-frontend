package com.ceng453groupmerge.frontend.GameObjects;

import java.io.IOException;

public class TilePurchasableRailroad extends TilePurchasable {

    public TilePurchasableRailroad(String name) {
        super(name, 250);
    }

    @Override
    public void tileAction(Player currentPlayer) throws IOException {
        if(getOwner().equals("")) { // If tile not owned
            // TODO: Bring up purchase buttons
        }
        else if(!getOwner().equals(currentPlayer.getPlayerName())) { // If owner is someone else
            String tileOwner = getOwner();
            int ownerRailroads = 0;
            for(int i=0;i<16;i++)
            {
                if(GameLogic.getInstance().getTiles().get(i).getType().equals("railroad") && GameLogic.getInstance().getTiles().get(i).getOwner().equals(tileOwner))
                {
                    ownerRailroads++; // If a tile is a railroad and the owner is the other player, increment this
                }
            }
            currentPlayer.subtractMoney(25*ownerRailroads);
        }
    }

    @Override
    public String getType() {
        return "railroad";
    }
}
