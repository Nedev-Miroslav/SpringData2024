package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.TownSeedDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.Validations.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {

    private static final String FILE_PATH = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
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

            Optional<Town> optional = this.townRepository.findByTownName(townSeedDto.getTownName());

            if(!this.validationUtil.isValid(townSeedDto) || optional.isPresent()){
                sb.append("Invalid town").append(System.lineSeparator());
                continue;

            }

            Town town = this.modelMapper.map(townSeedDto, Town.class);
            this.townRepository.saveAndFlush(town);

            sb.append(String.format("Successfully imported town %s - %d", townSeedDto.getTownName(), townSeedDto.getPopulation())).append(System.lineSeparator());

        }



        return sb.toString();
    }
}
