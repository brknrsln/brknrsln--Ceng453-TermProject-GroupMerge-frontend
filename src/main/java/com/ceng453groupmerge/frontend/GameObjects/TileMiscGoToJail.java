package com.ceng453groupmerge.frontend.GameObjects;

public class TileMiscGoToJail extends TileMisc {

    public TileMiscGoToJail() {
        setTileName("GoToJail");
    }

    @Override
    public void tileAction(Player currentPlayer) {
        currentPlayer.sendToJail();
    }
}
