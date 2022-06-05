//package com.ceng453groupmerge.frontend;
//
//import com.ceng453groupmerge.frontend.RestClients.AuthRestClient;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class AuthRestClientTest {
//
//    @Test
//    void login_success(){
//        String username = "AI_OVERLORD";
//        String password = "test";
//
//        String access_token = AuthRestClient.login(username, password);
//
//        System.out.println(access_token);
//        assertTrue(access_token != null);
//    }
//
//    @Test
//    void login_fail(){
//        String username = "AI_OVERLORD";
//        String password = "test1";
//
//        Assertions.assertThrows(Exception.class, ()-> AuthRestClient.login(username, password));
//    }
//
//    @Test
//    void register_success(){
//        String username = "Kelvin_Clave";
//        String email = "kelvin@clave.com";
//        String password = "kelvinkelvin";
//
//        Assertions.assertDoesNotThrow(() -> AuthRestClient.register(username, email, password));
//    }
//
//    @Test
//    void register_fail(){
//        String username = "Kelvin_Clave";
//        String email = "kelvin@clave.com";
//        String password = "kelvinkelvin";
//
//        Assertions.assertThrows(Exception.class, () -> AuthRestClient.register(username, email, password));
//    }
//}
