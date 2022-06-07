package com.ceng453groupmerge.frontend;

import com.ceng453groupmerge.frontend.RestClients.GameRestClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameRestClientTest {
    private GameRestClient gameRestClient = GameRestClient.getInstance();



    public GameRestClientTest() throws IOException {
    }

    @Test
    void save_success(){
        String player = "Onaterdem";
        String opponent = "AI_OVERLORD";
        String player_score = "1000";
        String opponent_score = "2000";

        String isSaved = gameRestClient.save(player, opponent, player_score, opponent_score);

        assertEquals("Saved", isSaved);
    }


    @Test
    void get_all_games(){
        assertNotNull(gameRestClient.getAllGames());
    }

}
