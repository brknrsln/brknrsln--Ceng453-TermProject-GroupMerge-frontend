package com.ceng453groupmerge.frontend.GameObjects;

public class PlayerAI extends Player {

    public PlayerAI() {
        setPlayerName("AI_OVERLORD");
    }

    @Override
    public void playTurn() {
        // TODO
        System.out.println("playTurn called for AI "+getPlayerName()); // TODO: Debug, remove
    }
}
