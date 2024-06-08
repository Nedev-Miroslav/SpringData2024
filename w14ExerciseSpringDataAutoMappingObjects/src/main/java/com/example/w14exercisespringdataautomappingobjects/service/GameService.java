package com.example.w14exercisespringdataautomappingobjects.service;

import com.example.w14exercisespringdataautomappingobjects.service.dtos.GameAddDTO;
import com.example.w14exercisespringdataautomappingobjects.service.dtos.GamesAllDTO;

import java.util.Map;
import java.util.Set;

public interface GameService {
    String addGame(GameAddDTO gameAddDTO);

    String editGame(long id, Map<String, String> map);

    String deleteGame(long id);

    Set<GamesAllDTO> getAllGames();

    String allGamesReadyForPrint();


    String findGameByTitle(String token);
}
