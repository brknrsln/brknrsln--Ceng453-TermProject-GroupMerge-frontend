package com.ceng453groupmerge.frontend.RestClients;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ceng453groupmerge.frontend.Constants.URIConstants.*;

public class LeaderboardRestClient {
    private static final WebClient webClient = WebClient.create("https://ceng453-termproject-groupMerge.herokuapp.com");


    public static Object getAllTimeLeaderboard(){
        return webClient.get().uri(ALL_TIME)
        .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error requesting leaderboard."))))
        .bodyToMono(Object.class)
        .block();
    }

    public static Object getMonthlyLeaderboard(){
        return webClient.get().uri(MONTHLY)
        .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error requesting leaderboard."))))
        .bodyToMono(Object.class)
        .block();
    }

    public static Object getWeeklyLeaderboard(){
        return webClient.get().uri(WEEKLY)
        .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error requesting leaderboard."))))
        .bodyToMono(Object.class)
        .block();
    }
    


}
