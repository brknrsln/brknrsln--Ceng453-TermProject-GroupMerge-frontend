package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;

import java.io.IOException;

public class TileMiscJailCell extends TileMisc {

    public TileMiscJailCell() {
        setTileName("JailCell");
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) throws IOException, InterruptedException {
        GameController.getInstance().skipTurn();
    }
}
