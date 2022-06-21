package com.ceng453groupmerge.frontend.GameObjects;

import com.ceng453groupmerge.frontend.DTO.PlayerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;

import static com.ceng453groupmerge.frontend.Constants.URIConstants.SAVE_GAME;

public class GameState {

    private ArrayList<Player> players;
    private ArrayList<Tile> tiles;
    private Dice dice;
    private int currentPlayer;



}
