package com.ceng453groupmerge.frontend.RestClients;

import com.ceng453groupmerge.frontend.PropertiesLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.ceng453groupmerge.frontend.Constants.URIConstants.*;



public class GameRestClient {

    private final WebClient webClient;

    private static GameRestClient gameRestClient;

    private GameRestClient() throws IOException {
        String backendURI = PropertiesLoader.loadProperties("application.properties").getProperty("spring.application.backend.service");
        this.webClient = WebClient.create(backendURI);
    }

    public static synchronized GameRestClient getInstance() throws IOException {
        if (gameRestClient == null)
            gameRestClient = new GameRestClient();
        return gameRestClient;
    }

    //return "saved" or throw error message
    public String save(String player, String opponent, String player_score, String opponent_score){
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("player", player);
        parameters.add("opponent", opponent);
        parameters.add("player_score", player_score);
        parameters.add("opponent_score", opponent_score);

        return webClient.post().uri(SAVE_GAME)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(parameters)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(String.class)
                .block();
    }

    //return games or throw error message
    public Object getAllGames(){
        return webClient.get().uri(GET_ALL_GAMES)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error requesting leaderboard."))))
                .bodyToMono(Object.class)
                .block();
    }


}
