package com.ceng453groupmerge.frontend.RestClients;

import com.ceng453groupmerge.frontend.PropertiesLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static com.ceng453groupmerge.frontend.Constants.URIConstants.*;

public class AuthRestClient {



    private final WebClient webClient;

    private static AuthRestClient authRestClient;

    private AuthRestClient() throws IOException {
        String backendURI = PropertiesLoader.loadProperties("application.properties").getProperty("spring.application.backend.service");
        this.webClient = WebClient.create(backendURI);

    }

    public static synchronized AuthRestClient getInstance() {
        if (authRestClient == null) {
            try {
                authRestClient = new AuthRestClient();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return authRestClient;
    }

    //return the access token if success, error message otherwise
    public String login(String username, String password){
        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("username", username);
        credentials.add("password", password);
        return webClient.post().uri(LOGIN_AUTH)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(credentials)
            .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
            .bodyToMono(String.class)
            .block();
    }

    //return "saved" or throw error message
    public String register(String username, String email, String password){
        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("username", username);
        credentials.add("email", email);
        credentials.add("password", password);

        return webClient.post().uri(REGISTER_AUTH)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(credentials)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(String.class)
                .block();
    }

    //return ok or throw error message
    public String forgot(String email){
        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("email", email);

        return webClient.post().uri(FORGOT_AUTH)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(credentials)
                .retrieve().onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new Exception(body)))
                .bodyToMono(String.class)
                .block();
    }
}
