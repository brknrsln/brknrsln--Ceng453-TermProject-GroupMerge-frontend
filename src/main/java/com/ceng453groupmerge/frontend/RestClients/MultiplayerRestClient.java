package com.ceng453groupmerge.frontend.RestClients;

import com.ceng453groupmerge.frontend.DTO.PlayerDTO;
import com.ceng453groupmerge.frontend.Utilities.PropertiesLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import static com.ceng453groupmerge.frontend.Constants.URIConstants.*;

import java.io.IOException;

public class MultiplayerRestClient {




    private final WebClient webClient;

    private static MultiplayerRestClient multiplayerRestClient;

    private MultiplayerRestClient() {
        String backendURI = null;
        try {
            backendURI = PropertiesLoader.loadProperties("application.properties").getProperty("spring.application.backend.service");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.webClient = WebClient.create(backendURI);
    }

    public static synchronized MultiplayerRestClient getInstance() {
        if (multiplayerRestClient == null)
            multiplayerRestClient = new MultiplayerRestClient();
        return multiplayerRestClient;
    }

    public String updatePlayer(PlayerDTO playerDTO){
        return webClient.post().uri(UPDATE_PLAYER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(playerDTO)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(String.class)
                .block();
    }

    public String updateTile(PlayerDTO playerDTO){
        return webClient.post().uri(UPDATE_TILE)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(playerDTO)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(String.class)
                .block();
    }

    public Object getRoomList() {
        return webClient.get().uri(GET_ROOM_LIST)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(Object.class)
                .block();
    }

    public Object joinRoom(String player, Integer roomId) {
        return getObject(player, roomId, JOIN_ROOM);
    }

    public Object waitRoom(Integer roomId) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("room", roomId.toString());
        return webClient.post().uri(WAIT_ROOM)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(parameters)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(Object.class)
                .block();
    }

    public Object leaveRoom(String player, Integer roomId) {
        return getObject(player, roomId, LEAVE_ROOM);
    }

    private Object getObject(String player, Integer roomId, String leaveRoom) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("player", player);
        parameters.add("room", roomId.toString());

        return webClient.post().uri(leaveRoom)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(parameters)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(Object.class)
                .block();
    }
}
