package com.ceng453groupmerge.frontend.RestClients;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ceng453groupmerge.frontend.Constants.AuthConstants.*;

public class AuthRestClient {
    private static final WebClient webClient = WebClient.create("https://ceng453-term-project-group15.herokuapp.com");

    //return the access token if success
    public static String login(String username, String password){
        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("username", username);
        credentials.add("password", password);

        return webClient.post().uri(LOGIN_AUTH)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(credentials)
            .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception(("Error logging in."))))
            .bodyToMono(String.class)
            .block();
    }

    public static String register(String username, String email, String password){
        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("username", username);
        credentials.add("email", email);
        credentials.add("password", password);

        return webClient.post().uri(REGISTER_AUTH)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(credentials)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception("Error registering.")))
                .bodyToMono(String.class)
                .block();
    }

    public static String forgot(String email){
        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("email", email);

        return webClient.post().uri(FORGOT_AUTH)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(credentials)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Exception("Error sending password reset link.")))
                .bodyToMono(String.class)
                .block();
    }
}
