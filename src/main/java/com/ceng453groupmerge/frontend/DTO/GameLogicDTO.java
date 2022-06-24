package com.ceng453groupmerge.frontend.DTO;

import com.ceng453groupmerge.frontend.GameObjects.Player;
import com.ceng453groupmerge.frontend.GameObjects.Tile;

import java.util.ArrayList;

public class GameLogicDTO {
    private int gameId;
    private int currentPlayer;
    private int turn;
    private int value1;
    private int value2;
    private boolean purchased;

    public GameLogicDTO(int gameId, int currentPlayer, int turn, int value1, int value2, boolean purchased) {
        this.gameId = gameId;
        this.currentPlayer = currentPlayer;
        this.turn = turn;
        this.value1 = value1;
        this.value2 = value2;
        this.purchased = purchased;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
