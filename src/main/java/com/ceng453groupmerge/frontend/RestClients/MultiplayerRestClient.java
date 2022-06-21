package com.ceng453groupmerge.frontend.RestClients;

import com.ceng453groupmerge.frontend.DTO.PlayerDTO;
import com.ceng453groupmerge.frontend.Utilities.PropertiesLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

}
