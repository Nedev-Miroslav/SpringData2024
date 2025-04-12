package com.example.football.service.impl;

import com.example.football.models.dto.jsons.TownSeedDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.Validations.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class TownServiceImpl implements TownService {
    private static final String FILE_PATH = "src/main/resources/files/json/towns.json";

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final TownRepository townRepository;

    public TownServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        TownSeedDto[] townSeedDtos = this.gson.fromJson(readTownsFileContent(), TownSeedDto[].class);


        for (TownSeedDto townSeedDto : townSeedDtos) {

            Optional<Town> optional = this.townRepository.findByName(townSeedDto.getName());

            if(!this.validationUtil.isValid(townSeedDto) || optional.isPresent()){
                sb.append("Invalid Town").append(System.lineSeparator());
                continue;

            }

            Town town = this.modelMapper.map(townSeedDto, Town.class);
            this.townRepository.saveAndFlush(town);

            sb.append(String.format("Successfully imported town %s - %d", townSeedDto.getName(), townSeedDto.getPopulation())).append(System.lineSeparator());


        }

        return sb.toString();
    }
}
