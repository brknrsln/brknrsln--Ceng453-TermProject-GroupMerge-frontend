package com.ceng453groupmerge.frontend.DTO;

import com.ceng453groupmerge.frontend.GameObjects.Dice;
import com.ceng453groupmerge.frontend.GameObjects.GameLogic;

import java.util.LinkedHashMap;

public class GameLogicDTO {
    private int gameId;
    private int playerIndex;
    private int turn;
    private int value1;
    private int value2;
    private boolean purchased;

    private static GameLogicDTO instance;

    public static GameLogicDTO getInstance() {
        if (instance == null) {
            instance = new GameLogicDTO();
        }
        return instance;
    }

    public GameLogicDTO(int gameId, int playerIndex, int turn, int value1, int value2, boolean purchased) {
        instance = this;
        this.gameId = gameId;
        this.playerIndex = playerIndex;
        this.turn = turn;
        this.value1 = value1;
        this.value2 = value2;
        this.purchased = purchased;
    }

    public GameLogicDTO() {
        this.gameId = 0;
        this.playerIndex = 0;
        this.turn = 0;
        this.value1 = 0;
        this.value2 = 0;
        this.purchased = false;
    }

    public GameLogicDTO(GameLogicDTO gameLogicDTO) {
        this.gameId = gameLogicDTO.getGameId();
        this.playerIndex = gameLogicDTO.getPlayerIndex();
        this.turn = gameLogicDTO.getTurn();
        this.value1 = gameLogicDTO.getValue1();
        this.value2 = gameLogicDTO.getValue2();
        this.purchased = gameLogicDTO.getPurchased();
    }

    private boolean getPurchased() {
        return purchased;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
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

    public void setGameLogicDTO(LinkedHashMap<String, ?> gameLogicDTO1) {
        this.playerIndex = (int) gameLogicDTO1.get("playerIndex");
        this.turn = (int) gameLogicDTO1.get("turn");
        this.purchased = (boolean) gameLogicDTO1.get("purchased");
        Dice.getInstance().setValue1((int) gameLogicDTO1.get("value1"));
        Dice.getInstance().setValue2((int) gameLogicDTO1.get("value2"));

        GameLogic.getInstance().setPlayerIndex(this.playerIndex);
        GameLogic.getInstance().setTurn(this.turn);
        GameLogic.getInstance().setPurchased(this.purchased);
    }
}
