package com.ceng453groupmerge.frontend.DTO;

import com.ceng453groupmerge.frontend.GameObjects.Tile;

import java.util.ArrayList;

public class PlayerDTO {
    protected static int playerCount;
    private int playerID;
    private String playerName;
    private int currentBalance;
    private int currentPosition;
    private ArrayList<TilePurchasableDTO> ownedProperties;
    private int jailTime;
    public int consecutiveDoubles;

    public static int getPlayerCount() {
        return playerCount;
    }

    public static void setPlayerCount(int playerCount) {
        PlayerDTO.playerCount = playerCount;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ArrayList<TilePurchasableDTO> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(ArrayList<TilePurchasableDTO> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public int getJailTime() {
        return jailTime;
    }

    public void setJailTime(int jailTime) {
        this.jailTime = jailTime;
    }

    public int getConsecutiveDoubles() {
        return consecutiveDoubles;
    }

    public void setConsecutiveDoubles(int consecutiveDoubles) {
        this.consecutiveDoubles = consecutiveDoubles;
    }

}
