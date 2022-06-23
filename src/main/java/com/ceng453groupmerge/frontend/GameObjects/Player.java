package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.Controllers.GameController;
import com.ceng453groupmerge.frontend.Controllers.SceneController;
import javafx.animation.RotateTransition;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Player {
    protected static int playerCount = 0;
    private int playerID;
    private String playerName;
    private int roomId;
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
//        System.out.println("Player balance after addition: "+(currentBalance-x)+"+"+x+"="+currentBalance);
        GameController.getInstance().addInfo(playerName+"'s balance after addition: "+(currentBalance-x)+"+"+x+"="+currentBalance);
        GameController.getInstance().updateScoreBoard();
    }

    public void subtractMoney(int x) {
        currentBalance -= x;
//        System.out.println("Player balance after subtraction: "+(currentBalance+x)+"-"+x+"="+currentBalance);
        GameController.getInstance().addInfo(playerName+"'s balance after subtraction: "+(currentBalance+x)+"-"+x+"="+currentBalance);
        GameController.getInstance().updateScoreBoard();
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
//        String text = "Player " are in jail. You have 2 turns to get out.";
        String text = "Player "+playerName+" has been jailed for 2 turns.";
        GameController.getInstance().addInfo(text);
        consecutiveDoubles = 0;
        GameController.getInstance().drawPlayerSprites();
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
//        System.out.println(playerName+" position after move: "+((currentPosition+16-x)%16)+"+"+x+"="+currentPosition);
        GameController.getInstance().drawPlayerSprites();
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
                    GameController.getInstance().printTileOwner(currentPosition);
//                    System.out.println("Player "+playerName+" now owns property "+newProperty.getTileName());
                    String text = "Player "+playerName+" now owns property "+newProperty.getTileName();
                    GameController.getInstance().addInfo(text);
                }
            }
        }
    }

    public abstract void playTurn();
    public abstract void playTurnAfterDice();
    public void playTurnAfterButton() {
//        System.out.println("playTurnAfterButton called for "+getPlayerName());

        // TODO: Print player money

        RotateTransition rt = new RotateTransition(Duration.millis(300), new VBox());
        rt.setOnFinished(e -> {
            if(consecutiveDoubles > 0) playTurn(); // If player rolled double, play turn again
            else GameLogic.getInstance().oneGameTurn();
        });
        rt.play();
    }

    public int getScore() {
        int score = 0;
        for(Tile property:ownedProperties) {
            score += property.getPrice();
        }
        score += currentBalance;
        if(score < 0) return 0;
        return score;
    }

    public void setCurrentPosition(int newPosition) {
        currentPosition = newPosition;
        GameController.getInstance().drawPlayerSprites();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID() {
        this.playerID = playerCount++;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
