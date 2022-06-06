package com.ceng453groupmerge.frontend;

import com.ceng453groupmerge.frontend.RestClients.AuthRestClient;
import com.ceng453groupmerge.frontend.RestClients.LeaderboardRestClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LeaderboardRestClientTest{
    private LeaderboardRestClient leaderboardRestClient = LeaderboardRestClient.getInstance();

    public LeaderboardRestClientTest() throws IOException {
    }

    @Test
    void getAllTimeLeaderboardTest(){
        Object leaderboard = leaderboardRestClient.getAllTimeLeaderboard();
        assertNotNull(leaderboard);
    }
    
    @Test
    void getMonthlyLeaderboardTest(){
        Object leaderboard = leaderboardRestClient.getMonthlyLeaderboard();
        assertNotNull(leaderboard);
    }

    @Test
    void getWeeklyLeaderboardTest(){
        Object leaderboard = leaderboardRestClient.getWeeklyLeaderboard();
        assertNotNull(leaderboard);
    }
}