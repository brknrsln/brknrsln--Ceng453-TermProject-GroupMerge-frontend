package com.ceng453groupmerge.frontend.RestClients;

import com.ceng453groupmerge.frontend.Utilities.PropertiesLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.ceng453groupmerge.frontend.Constants.URIConstants.*;

public class LeaderboardRestClient {

    private final WebClient webClient;

    private static LeaderboardRestClient leaderboardRestClient;

    private LeaderboardRestClient() throws IOException {
        String backendURI = PropertiesLoader.loadProperties("application.properties").getProperty("spring.application.backend.service");
        this.webClient = WebClient.create(backendURI);

    }

    public static synchronized LeaderboardRestClient getInstance() throws IOException {
        if (leaderboardRestClient == null)
            leaderboardRestClient = new LeaderboardRestClient();
        return leaderboardRestClient;
    }

    public Object getAllTimeLeaderboard(){
        return webClient.get().uri(ALL_TIME)
        .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error requesting leaderboard."))))
        .bodyToMono(Object.class)
        .block();
    }

    public Object getMonthlyLeaderboard(){
        return webClient.get().uri(MONTHLY)
        .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error requesting leaderboard."))))
        .bodyToMono(Object.class)
        .block();
    }

    public Object getWeeklyLeaderboard(){
        return webClient.get().uri(WEEKLY)
        .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error requesting leaderboard."))))
        .bodyToMono(Object.class)
        .block();
    }
    


}
