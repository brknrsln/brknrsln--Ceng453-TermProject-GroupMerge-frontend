package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.PlayerReal;
import com.ceng453groupmerge.frontend.RestClients.MultiplayerRestClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

public class RoomController {
    @FXML
    private Button room1;

    @FXML
    private Button room2;

    @FXML
    private Button room3;

    @FXML
    private Button room4;

    @FXML
    private Button room5;

    @FXML
    private Button leaveRoom;

    private Boolean joinedRoom = false;

    private int roomNumber = 0;

    private HashMap<String, Boolean> roomStatus = new HashMap<>();

    private List<Integer> roomMap = new ArrayList<>(5);

    private MultiplayerRestClient multiplayerRestClient = MultiplayerRestClient.getInstance();

    private String playerName = PlayerReal.getInstance().getPlayerName();
    public void initialize() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!joinedRoom) {
                        roomMap = (ArrayList<Integer>) multiplayerRestClient.getRoomList();
                        updateButtons();
                    }
                });
            }
        };
        new java.util.Timer().schedule(task, 0, 1000);
    }

    @FXML
    public void handleRoom1Action(ActionEvent event) {
        if(!roomStatus.get("room1")) {
            roomStatus.put("room1", true);
            roomNumber = 0;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            updateButtons();
        } else {
            roomStatus.put("room1", false);
            handleLeaveRoom(event);
        }
    }

    @FXML
    public void handleRoom2Action(ActionEvent event) {
        if(!roomStatus.get("room2")) {
            roomStatus.put("room2", true);
            roomNumber = 1;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            updateButtons();
        } else {
            roomStatus.put("room2", false);
            handleLeaveRoom(event);
        }
    }

    @FXML
    public void handleRoom3Action(ActionEvent event) {
        if (!roomStatus.get("room3")) {
            roomStatus.put("room3", true);
            roomNumber = 2;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            updateButtons();
        } else {
            roomStatus.put("room3", false);
            handleLeaveRoom(event);
        }
    }

    @FXML
    public void handleRoom4Action(ActionEvent event) {
        if (!roomStatus.get("room4")) {
            roomStatus.put("room4", true);
            roomNumber = 3;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            updateButtons();
        } else {
            roomStatus.put("room4", false);
            handleLeaveRoom(event);
        }
    }

    @FXML
    public void handleRoom5Action(ActionEvent event) {
        if (!roomStatus.get("room5")) {
            roomStatus.put("room5", true);
            roomNumber = 4;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            updateButtons();
        } else {
            roomStatus.put("room5", false);
            handleLeaveRoom(event);
        }
    }

    @FXML
    public void handleLeaveRoom(ActionEvent event) {
        roomMap = (List<Integer>) multiplayerRestClient.leaveRoom(playerName, roomNumber);
        joinedRoom = false;
        updateButtons();
    }

    private void updateButtons() {
        room1.setText(roomMap.get(0).toString() + "/" + 2);
        room2.setText(roomMap.get(1).toString() + "/" + 3);
        room3.setText(roomMap.get(2).toString() + "/" + 4);
        room4.setText(roomMap.get(3).toString() + "/" + 5);
        room5.setText(roomMap.get(4).toString() + "/" + 6);
        room1.setDisable(!roomStatus.get("room1"));
        room2.setDisable(!roomStatus.get("room2"));
        room3.setDisable(!roomStatus.get("room3"));
        room4.setDisable(!roomStatus.get("room4"));
        room5.setDisable(!roomStatus.get("room5"));
    }
}
