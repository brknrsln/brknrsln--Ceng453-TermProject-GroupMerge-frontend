package com.ceng453groupmerge.frontend.GameObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Player {
    protected static int playerCount = 0;
    private int playerID;
    private String playerName;
    private int currentBalance = 1500;
    private int currentPosition = 0;
    private ArrayList<Tile> ownedProperties = new ArrayList<>();
    private int jailTime = 0;
    public int consecutiveDoubles = 0;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        playerName = name;
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

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int countRailroads() {
        int count = 0;
        for(Tile property:ownedProperties) {
            if(property.getType().equals("railroad")) {
                count++;
            }
        }
        return count;
    }

    public void sendToJail() {
        jailTime = 2;
        currentPosition = 4;
        System.out.println("Player "+playerName+" has been jailed"); // TODO: Debug, remove
    }

    public int spendJailTime() {
        if(jailTime>0) {
            jailTime -= 1;
            return jailTime+1;
        }
        return jailTime;
    }

    public void movePlayer(int x) {
        currentPosition = (currentPosition+x)%16;
        System.out.println("Player position after move: "+((currentPosition+16-x)%16)+"+"+x+"="+currentPosition); // TODO: Debug, remove
    }

    public ArrayList<Tile> getOwnedProperties() {
        return ownedProperties;
    }

    public void purchaseProperty(Tile newProperty) {
        if(newProperty.getPrice() != -1) { // If property purchasable
            if(currentBalance >= newProperty.getPrice()) { // If player enough money
                if(Objects.equals(newProperty.getOwner(), "")) { // If property not owned
                    ownedProperties.add(newProperty);
                    newProperty.setOwner(playerName);
                    subtractMoney(newProperty.getPrice());
                    // TODO: Print new tile owner
                    System.out.println("Player "+playerName+" now owns property "+newProperty.getTileName()); // TODO: Debug, remove
                }
            }
        }
    }

    public abstract void playTurn() throws IOException, InterruptedException;

    public int calculateScore() {
        int score = 0;
        for(Tile property:ownedProperties) {
            score += property.getPrice();
        }
        score += currentBalance;
        return score;
    }

    public void setCurrentPosition(int newPosition) {
        currentPosition = newPosition;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
