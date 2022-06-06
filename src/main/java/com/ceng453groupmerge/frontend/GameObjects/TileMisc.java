package com.ceng453groupmerge.frontend.GameObjects;

public class TileMisc extends Tile {

    public TileMisc(String name) {
        setTileName(name);
    }

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
    public void tileAction() {
        // TODO
        System.out.println("tileAction called for miscellaneous "+getTileName()); // TODO: Debug, remove
    }
}
