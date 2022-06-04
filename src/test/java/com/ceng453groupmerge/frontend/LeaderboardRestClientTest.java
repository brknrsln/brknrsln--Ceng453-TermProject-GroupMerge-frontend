package com.ceng453groupmerge.frontend;

import com.ceng453groupmerge.frontend.RestClients.LeaderboardRestClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LeaderboardRestClientTest{

    @Test
    void getAllTimeLeaderboardTest(){
        Object leaderboard = LeaderboardRestClient.getAllTimeLeaderboard();
        assertNotNull(leaderboard);
    }
    
    @Test
    void getMonthlyLeaderboardTest(){
        Object leaderboard = LeaderboardRestClient.getMonthlyLeaderboard();
        assertNotNull(leaderboard);
    }

    @Test
    void getWeeklyLeaderboardTest(){
        Object leaderboard = LeaderboardRestClient.getWeeklyLeaderboard();
        assertNotNull(leaderboard);
    }
}