package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;

import java.io.IOException;

public class TileMiscGo extends TileMisc {

    public TileMiscGo() {
        setTileName("Go");
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) throws IOException, InterruptedException {
        GameController.getInstance().skipTurn();
    }
}
