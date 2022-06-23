package com.ceng453groupmerge.frontend.Controllers;

import com.ceng453groupmerge.frontend.GameObjects.GameLogic;
import com.ceng453groupmerge.frontend.GameObjects.PlayerReal;
import com.ceng453groupmerge.frontend.RestClients.MultiplayerRestClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

import static com.ceng453groupmerge.frontend.Constants.fxmlPathConstants.MAIN_MENU;

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
    private Button backMenu;

    @FXML
    private Button startGame;

    @FXML
    private Text waitingInfo;

    private Boolean joinedRoom = false;

    private int roomNumber = 0;

    private HashMap<String, Boolean> roomStatus = new HashMap<>(){
        {
            put("room1", false);
            put("room2", false);
            put("room3", false);
            put("room4", false);
            put("room5", false);
        }
    };

    private TimerTask task;

    private List<Integer> roomMap = new ArrayList<>(5){{
        add(0);
        add(0);
        add(0);
        add(0);
        add(0);
    }};

    private MultiplayerRestClient multiplayerRestClient = MultiplayerRestClient.getInstance();

    private String playerName = PlayerReal.getInstance().getPlayerName();
    public void initialize() {
        waitingInfo.setVisible(false);
        task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!joinedRoom) {
                        System.out.println("RoomController: not join room task: " + playerName);
                        roomMap = (List<Integer>) multiplayerRestClient.getRoomList();
                        updateButtons();
                    } else {
                        System.out.println("RoomController: join room task: " + playerName + " room number: " + roomNumber);
                        LinkedHashMap<String,?> room = (LinkedHashMap<String, ?>) multiplayerRestClient.waitRoom(roomNumber);
                        if(room != null) {
                            joinedRoom = false;
                            roomNumber = 0;
                            updateButtons();
                            this.cancel();
                            GameLogic gameLogic = GameLogic.getInstance();
                            gameLogic.setGameId(Integer.parseInt(room.get("gameId").toString()));
                            gameLogic.setRoomId(Integer.parseInt(room.get("id").toString()));
                            gameLogic.addPlayer(PlayerReal.getInstance());

                            List<String> playerList = (List<String>) room.get("players");
                            for(String player : playerList) {
                                if(!player.equals(playerName)) {
                                    PlayerReal playerReal = new PlayerReal(player);
                                    playerReal.setRoomId(Integer.parseInt(room.get("id").toString()));
                                    gameLogic.addPlayer(playerReal);
                                }
                            }
                            gameLogic.sortPlayers();
                            gameLogic.setMultiplayer(true);
                            startGame.fire();
                        }
                    }
                });
            }
        };
        new java.util.Timer().schedule(task, 0, 2000);
    }

    @FXML
    public void handleRoom1Action(ActionEvent event) {
        if(!roomStatus.get("room1")) {
            roomStatus.put("room1", true);
            roomNumber = 1;
            joinedRoom = true;
            waitingInfo.setVisible(true);
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            updateButtons();
        } else {
            roomStatus.put("room1", false);
            leaveRoom();
        }
    }

    @FXML
    public void handleRoom2Action(ActionEvent event) {
        if(!roomStatus.get("room2")) {
            roomStatus.put("room2", true);
            roomNumber = 2;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            waitingInfo.setVisible(true);
            updateButtons();
        } else {
            roomStatus.put("room2", false);
            leaveRoom();
        }
    }

    @FXML
    public void handleRoom3Action(ActionEvent event) {
        if (!roomStatus.get("room3")) {
            roomStatus.put("room3", true);
            roomNumber = 3;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            waitingInfo.setVisible(true);
            updateButtons();
        } else {
            roomStatus.put("room3", false);
            leaveRoom();
        }
    }

    @FXML
    public void handleRoom4Action(ActionEvent event) {
        if (!roomStatus.get("room4")) {
            roomStatus.put("room4", true);
            roomNumber = 4;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            waitingInfo.setVisible(true);
            updateButtons();
        } else {
            roomStatus.put("room4", false);
            leaveRoom();
        }
    }

    @FXML
    public void handleRoom5Action(ActionEvent event) {
        if (!roomStatus.get("room5")) {
            roomStatus.put("room5", true);
            roomNumber = 5;
            roomMap = (List<Integer>) multiplayerRestClient.joinRoom(playerName, roomNumber);
            joinedRoom = true;
            waitingInfo.setVisible(true);
            updateButtons();
        } else {
            roomStatus.put("room5", false);
            leaveRoom();
        }
    }

    @FXML
    public void handleBackMenuAction(ActionEvent event) throws IOException {
        if(roomNumber!=0) leaveRoom();
        SceneController.switchToScene(event, MAIN_MENU);
        task.cancel();
    }

    private void leaveRoom(){
        roomMap = (List<Integer>) multiplayerRestClient.leaveRoom(playerName, roomNumber);
        waitingInfo.setVisible(false);
        joinedRoom = false;
        updateButtons();
    }

    public void handleStartGameAction(ActionEvent event) throws IOException {
        SceneController.switchToGameScene(event);
    }

    private void updateButtons() {
        room1.setText(roomMap.get(0).toString() + "/" + 2);
        room2.setText(roomMap.get(1).toString() + "/" + 3);
        room3.setText(roomMap.get(2).toString() + "/" + 4);
        room4.setText(roomMap.get(3).toString() + "/" + 5);
        room5.setText(roomMap.get(4).toString() + "/" + 6);
        room1.setDisable((!roomStatus.get("room1")&&joinedRoom));
        room2.setDisable((!roomStatus.get("room2")&&joinedRoom));
        room3.setDisable((!roomStatus.get("room3")&&joinedRoom));
        room4.setDisable((!roomStatus.get("room4")&&joinedRoom));
        room5.setDisable((!roomStatus.get("room5")&&joinedRoom));
    }
}
