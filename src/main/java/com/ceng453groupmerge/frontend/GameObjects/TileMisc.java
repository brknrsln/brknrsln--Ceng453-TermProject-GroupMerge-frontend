package com.ceng453groupmerge.frontend.GameObjects;

public abstract class TileMisc extends Tile {

    @Override
    public int getPrice() {
        return -1;
    }

    @Override
    public String getOwner() {
        return "";
    }

    @Override
    public void setOwner(String newOwner) { // Do nothing
    }

    @Override
    public String getType() {
        return "misc";
    }
}
