package com.ceng453groupmerge.frontend.DTO;

import com.ceng453groupmerge.frontend.GameObjects.Player;
import com.ceng453groupmerge.frontend.GameObjects.Tile;

import java.util.ArrayList;

public class GameLogicDTO {
    private ArrayList<Player> players;
    private ArrayList<Tile> tiles;
    private int gameId;
    private int currentPlayer;
    private int turn;
    private int value1;
    private int value2;

    public GameLogicDTO(ArrayList<Player> players, ArrayList<Tile> tiles, int gameId, int currentPlayer, int turn, int value1, int value2) {
        this.players = players;
        this.tiles = tiles;
        this.gameId = gameId;
        this.currentPlayer = currentPlayer;
        this.turn = turn;
        this.value1 = value1;
        this.value2 = value2;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
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
}
