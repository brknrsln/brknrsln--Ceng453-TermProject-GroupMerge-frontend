package com.ceng453groupmerge.frontend.GameObjects;

public abstract class Tile {
    protected String tileName;

    public String getTileName() {
        return tileName;
    }

    public abstract boolean getPrice();
}
