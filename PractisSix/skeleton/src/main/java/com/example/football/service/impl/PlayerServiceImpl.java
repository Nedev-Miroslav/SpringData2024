package com.example.football.service.impl;

import com.example.football.models.dto.xmls.PlayerRootDto;
import com.example.football.models.dto.xmls.PlayerSeedDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Position;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.Validations.ValidationUtil;
import com.example.football.util.xmlParser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final String FILE_PATH = "src/main/resources/files/xml/players.xml";

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    private final PlayerRepository playerRepository;

    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;

    public PlayerServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser, PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importPlayers() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        PlayerRootDto playerRootDto = xmlParser.parse(PlayerRootDto.class, FILE_PATH);

        for (PlayerSeedDto playerSeedDto : playerRootDto.getPlayerSeedDtos()) {

            Optional<Player> optional = this.playerRepository.getByEmail(playerSeedDto.getEmail());

            if(!this.validationUtil.isValid(playerSeedDto) || optional.isPresent()) {
                sb.append("Invalid Player").append(System.lineSeparator());
                continue;


            }

            Player player = this.modelMapper.map(playerSeedDto, Player.class);
                player.setPosition(Position.valueOf(playerSeedDto.getPosition()));
                player.setTownPlayer(this.townRepository.findByName(playerSeedDto.getTown().getName()).get());
                player.setTeam(this.teamRepository.getByName(playerSeedDto.getTeam().getName()).get());
                player.setStat(this.statRepository.getById(playerSeedDto.getStat().getId()));

                this.playerRepository.saveAndFlush(player);

            sb.append(String.format("Successfully imported Player %s %s - %s", player.getFirstName(), player.getLastName(), player.getPosition())).append(System.lineSeparator());
        }


        return sb.toString();
    }

    @Override
    public String exportBestPlayers() {
        return this.playerRepository.findAllPlayer()
                .stream()
                .map(p -> String.format("Player - %s %s%n" +
                        "           Position - %s%n" +
                        "           Team - %s%n" +
                        "           Stadium - %s%n"
                        , p.getFirstName()
                        , p.getLastName()
                        , p.getPosition()
                        , p.getTeam().getName()
                        , p.getTeam().getStadiumName()
                        ))
                        .collect(Collectors.joining());
    }
}
