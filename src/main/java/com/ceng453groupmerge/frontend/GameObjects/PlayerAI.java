package com.ceng453groupmerge.frontend.GameObjects;

import java.io.IOException;

public class PlayerAI extends Player {

    private GameLogic gameLogic;

    public PlayerAI() throws IOException {
        setPlayerName("AI_OVERLORD");
        gameLogic = GameLogic.getInstance();
    }

    @Override
    public void playTurn() {
        System.out.println("playTurn called for AI "+getPlayerName()); // TODO: Debug, remove

        if(spendJailTime()>0) { // If player is not jailed
        }
    }
}
