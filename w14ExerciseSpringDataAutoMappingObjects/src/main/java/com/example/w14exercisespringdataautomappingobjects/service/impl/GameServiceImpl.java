package com.example.w14exercisespringdataautomappingobjects.service.impl;

import com.example.w14exercisespringdataautomappingobjects.data.entities.Game;
import com.example.w14exercisespringdataautomappingobjects.data.repositories.GameRepository;
import com.example.w14exercisespringdataautomappingobjects.service.GameService;
import com.example.w14exercisespringdataautomappingobjects.service.dtos.DetailGameDTO;
import com.example.w14exercisespringdataautomappingobjects.service.dtos.GameAddDTO;
import com.example.w14exercisespringdataautomappingobjects.service.dtos.GamesAllDTO;
import com.example.w14exercisespringdataautomappingobjects.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ValidatorService validatorService;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, ValidatorService validatorService, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.validatorService = validatorService;
        this.modelMapper = modelMapper;
    }


    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        if (this.validatorService.isValid(gameAddDTO)) {
            return this.validatorService.validate(gameAddDTO)
                    .stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        Game game = this.modelMapper.map(gameAddDTO, Game.class);
        this.gameRepository.saveAndFlush(game);

        return String.format("Added %s", game.getTitle());
    }

    @Override
    public String editGame(long id, Map<String, String> map) {
        Optional<Game> gameOptional = this.gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            return "No such game exists with given id";
        }

        Game game = gameOptional.get();
        String output = String.format("Edited %s", game.getTitle());

        for (Map.Entry<String, String> entry : map.entrySet()) {

            switch (entry.getKey()) {

                case "title":
                    game.setTitle(entry.getValue());
                    break;
                case "price":
                    game.setPrice(Double.parseDouble(entry.getValue()));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(entry.getValue()));
                    break;
                case "trailer":
                    game.setTrailer(entry.getValue());
                    break;
                case "thumbnail":
                    game.setThumbnail(entry.getValue());
                    break;
                case "description":
                    game.setDescription(entry.getValue());
                    break;
                case "releaseDate":
                    game.setReleaseDate(LocalDate.parse(entry.getValue(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    break;
            }
        }

        this.gameRepository.saveAndFlush(game);

        return output;
    }

    @Override
    public String deleteGame(long id) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return "No such game with given id";

        }
        String output = String.format("Delete %s", optionalGame.get().getTitle());
        this.gameRepository.delete(optionalGame.get());
        return output;
    }

    @Override
    public Set<GamesAllDTO> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GamesAllDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public String allGamesReadyForPrint() {
        return this.getAllGames().stream().map(GamesAllDTO::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String findGameByTitle(String token) {
        return
                this.gameRepository.findAll().stream().filter(g -> g.getTitle().equals(token))
                .map(g -> this.modelMapper.map(g, DetailGameDTO.class).toString())
                .collect(Collectors.joining(System.lineSeparator()));



    }


}
