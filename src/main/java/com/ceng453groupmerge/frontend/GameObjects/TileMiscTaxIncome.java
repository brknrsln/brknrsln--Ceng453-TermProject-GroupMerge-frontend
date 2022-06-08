package com.ceng453groupmerge.frontend.GameObjects;

public class TileMiscTaxIncome extends TileMisc {

    public TileMiscTaxIncome() {
        setTileName("TaxIncome");
    }

    @Override
    public void tileAction(Player currentPlayer, Player otherPlayer) {
        currentPlayer.subtractMoney(50);
    }
}
