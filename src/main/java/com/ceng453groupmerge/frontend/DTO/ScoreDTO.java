package com.ceng453groupmerge.frontend.DTO;

import java.util.HashMap;

public class ScoreDTO {
    private Integer gameId;
    private Integer roomId;
    private HashMap<String, Integer> players;

    public ScoreDTO(){
        players = new HashMap<>();
    }

    public ScoreDTO(Integer gameId, Integer roomId, HashMap<String, Integer> players) {
        this.gameId = gameId;
        this.roomId = roomId;
        this.players = players;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public HashMap<String, Integer> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String, Integer> players) {
        this.players = players;
    }

    public void addPlayer(String username, Integer score){
        this.players.put(username, score);
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
