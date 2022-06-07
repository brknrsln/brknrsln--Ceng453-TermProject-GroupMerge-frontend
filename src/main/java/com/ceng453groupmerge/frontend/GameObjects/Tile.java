package com.ceng453groupmerge.frontend.GameObjects;

import java.io.IOException;

public abstract class Tile {
    private String tileName;

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String name) {
        tileName = name;
    }

    public abstract int getPrice();

    public abstract String getOwner();

    public abstract void setOwner(String newOwner);

    public abstract void tileAction(Player currentPlayer) throws IOException;

    public abstract String getType();
}
