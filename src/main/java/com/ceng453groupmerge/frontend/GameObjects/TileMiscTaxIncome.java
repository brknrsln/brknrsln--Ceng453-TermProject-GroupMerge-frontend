package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;

import java.io.IOException;

public class TileMiscTaxIncome extends TileMisc {

    public TileMiscTaxIncome() {
        setTileName("TaxIncome");
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) throws IOException, InterruptedException {
        currentPlayer.subtractMoney(50);
    }
}
