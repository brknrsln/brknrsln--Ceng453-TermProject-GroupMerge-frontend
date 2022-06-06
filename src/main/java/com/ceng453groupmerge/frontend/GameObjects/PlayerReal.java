package com.ceng453groupmerge.frontend.GameObjects;

public class PlayerReal extends Player {

    public PlayerReal(String name) {
        setPlayerName(name);
    }

    @Override
    public void playTurn() {
        // TODO
        System.out.println("playTurn called for real "+getPlayerName()); // TODO: Debug, remove
    }
}
