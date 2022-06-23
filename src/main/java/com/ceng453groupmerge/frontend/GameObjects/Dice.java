package com.ceng453groupmerge.frontend.GameObjects;

import javafx.scene.image.Image;

import java.util.Objects;

public class Dice {
    private int value1;
    private int value2;
    private static Dice instance;

    public static Dice getInstance() {
        if(instance == null) {
            instance = new Dice();
        }
        return instance;
    }

    public Image getDiceRandom() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/diceRandom.png")));
    }
    public Image getDiceImage(int i) {
        switch (i) {
            case 1:
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/dice1.png")));
            case 2:
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/dice2.png")));
            case 3:
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/dice3.png")));
            case 4:
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/dice4.png")));
            case 5:
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/dice5.png")));
            case 6:
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice/dice6.png")));
        }
        return null;
    }

    public int sumDice() {
        return value1 + value2;
    }

    public boolean isDouble() {
        return value1 == value2;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}
