package com.example.football.service.impl;

import com.example.football.models.dto.jsons.TeamSeedDto;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.Validations.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String FILE_PATH = "src/main/resources/files/json/teams.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TeamRepository teamRepository) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.teamRepository = teamRepository;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        TeamSeedDto[] teamSeedDtos = this.gson.fromJson(readTeamsFileContent(), TeamSeedDto[].class);


        for (TeamSeedDto teamSeedDto : teamSeedDtos) {

            Optional<Team> optional = this.teamRepository.getByName(teamSeedDto.getName());

            if(!this.validationUtil.isValid(teamSeedDto) || optional.isPresent()){
                sb.append("Invalid Team").append(System.lineSeparator());
                continue;

            }

            Team team = this.modelMapper.map(teamSeedDto, Team.class);
            team.setTown(this.townRepository.findByName(teamSeedDto.getTownName()).get());
            this.teamRepository.saveAndFlush(team);

            sb.append(String.format("Successfully imported Team - %s %d", team.getName(), team.getFanBase())).append(System.lineSeparator());


        }



        return sb.toString();
    }
}
