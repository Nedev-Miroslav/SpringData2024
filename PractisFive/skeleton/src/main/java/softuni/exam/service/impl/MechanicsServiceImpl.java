package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.MechanicSeedDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    private static final String MECHANIC_FILE_PATH = "src/main/resources/files/json/mechanics.json";

    private final MechanicsRepository mechanicsRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.mechanicsRepository = mechanicsRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(MECHANIC_FILE_PATH)));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();

        MechanicSeedDto[] mechanicSeedDtos = this.gson.fromJson(readMechanicsFromFile(), MechanicSeedDto[].class);

        for (MechanicSeedDto mechanicSeedDto : mechanicSeedDtos) {

            Optional<Mechanic> optionalMechanic = this.mechanicsRepository.findByFirstName(mechanicSeedDto.getFirstName());
            Optional<Mechanic> optionalEmail = this.mechanicsRepository.findByEmail(mechanicSeedDto.getEmail());
            Optional<Mechanic> optionalPhone = this.mechanicsRepository.findByPhone(mechanicSeedDto.getPhone());


            if(!this.validationUtil.isValid(mechanicSeedDto) || optionalMechanic.isPresent() || optionalEmail.isPresent() || optionalPhone.isPresent()) {
                sb.append("Invalid mechanic").append(System.lineSeparator());
                continue;
            }

            Mechanic mechanic = this.modelMapper.map(mechanicSeedDto, Mechanic.class);

            this.mechanicsRepository.saveAndFlush(mechanic);

            sb.append(String.format("Successfully imported mechanic %s %s", mechanic.getFirstName(), mechanic.getLastName())).append(System.lineSeparator());


        }




        return sb.toString();
    }
}
