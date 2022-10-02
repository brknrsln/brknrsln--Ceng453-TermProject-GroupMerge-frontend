package com.ceng453groupmerge.frontend.RestClients;

import com.ceng453groupmerge.frontend.DTO.ScoreDTO;
import com.ceng453groupmerge.frontend.Utilities.PropertiesLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.ceng453groupmerge.frontend.Constants.URIConstants.*;

public class MultiplayerRestClient {

    private final WebClient webClient;

    private static MultiplayerRestClient instance;

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
        if (instance == null)
            instance = new MultiplayerRestClient();
        return instance;
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

    public void sendScore(ScoreDTO scoreDTO) {
        webClient.post().uri(SEND_SCORE)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(scoreDTO)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error sending score."))))
                .bodyToMono(Object.class)
                .block();
    }
}
