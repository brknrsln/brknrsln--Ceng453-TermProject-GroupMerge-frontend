package com.ceng453groupmerge.frontend.GameObjects;

import java.io.IOException;

public class TileMiscGoToJail extends TileMisc {

    public TileMiscGoToJail() {
        setTileName("GoToJail");
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) throws IOException, InterruptedException {
        currentPlayer.sendToJail();
    }
}
