package com.ceng453groupmerge.frontend.GameObjects;

import java.util.ArrayList;

public abstract class Player {
    protected String playerName;
    protected int currentBalance = 1500;
    protected ArrayList<Tile> ownedProperties = new ArrayList<>(); // TODO: Change to PurchasableTile?

    public String getPlayerName() {
        return playerName;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void addMoney(int x) {
        currentBalance += x;
        System.out.println("Player balance after addition: "+(currentBalance-x)+"+"+x+"="+currentBalance); // TODO: Debug, remove
    }

    public void subtractMoney(int x) {
        currentBalance -= x;
        System.out.println("Player balance after subtraction: "+(currentBalance+x)+"-"+x+"="+currentBalance); // TODO: Debug, remove
    }

    public ArrayList<Tile> getOwnedProperties() {
        return ownedProperties;
    }

    public void addProperty(Tile newProperty) {  // TODO: Change to PurchasableTile?
        ownedProperties.add(newProperty);
        System.out.println("Player "+playerName+" now owns property "+newProperty.getTileName());
    }

    public abstract void playTurn();
}
