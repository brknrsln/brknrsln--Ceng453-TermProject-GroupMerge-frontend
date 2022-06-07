package com.ceng453groupmerge.frontend.GameObjects;

public abstract class TilePurchasable extends Tile {

    private final int tilePrice;
    private String tileOwner;

    public TilePurchasable(String name, int price) {
        setTileName(name);
        tilePrice = price;
        tileOwner = "";
    }

    @Override
    public int getPrice() {
        return tilePrice;
    }

    @Override
    public String getOwner() {
        return tileOwner;
    }

    @Override
    public void setOwner(String newOwner) {
        tileOwner = newOwner;
    }
}
