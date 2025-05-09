package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.VolcanoSeedDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.enums.VolcanoType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private static final String FILE_PATH = "src/main/resources/files/json/volcanoes.json";


    private final VolcanoRepository volcanoRepository;

    private final CountryRepository countryRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();

        VolcanoSeedDto[] volcanoSeedDtos = this.gson.fromJson(readVolcanoesFileContent(), VolcanoSeedDto[].class);

        for (VolcanoSeedDto volcanoSeedDto : volcanoSeedDtos) {

            Optional<Volcano> optional = this.volcanoRepository.findByName(volcanoSeedDto.getName());

            if(!this.validationUtil.isValid(volcanoSeedDto) || optional.isPresent()){
                sb.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }


            Volcano volcano = this.modelMapper.map(volcanoSeedDto, Volcano.class);
            volcano.setVolcanoType(VolcanoType.valueOf(volcanoSeedDto.getVolcanoType()));
            volcano.setCountry(this.countryRepository.findById(volcanoSeedDto.getCountry()).get());
            this.volcanoRepository.saveAndFlush(volcano);

            sb.append(String.format("Successfully imported volcano %s of type %s", volcano.getName(), volcano.getVolcanoType())).append(System.lineSeparator());




        }


        return sb.toString();
    }

    @Override
    public String exportVolcanoes() {
        return this.volcanoRepository.findAllVolcanoThatINeed()
                .stream()
                .map(v -> String.format("Volcano: %s%n" +
                        "   *Located in: %s%n" +
                        "   **Elevation: %d%n" +
                        "   ***Last eruption on: %s%n"
                            , v.getName()
                            , v.getCountry().getName()
                            , v.getElevation()
                            , v.getLastEruption()))
                .collect(Collectors.joining());


    }
}