package com.ceng453groupmerge.frontend.GameObjects;

public class TileMiscJailCell extends TileMisc {

    public TileMiscJailCell() {
        setTileName("JailCell");
    }

    @Override
    public void tileAction(Player currentPlayer) {
        // Do nothing again
        System.out.println("Player "+currentPlayer.getPlayerName()+" has been jailed"); // TODO: Debug, remove
    }
}
