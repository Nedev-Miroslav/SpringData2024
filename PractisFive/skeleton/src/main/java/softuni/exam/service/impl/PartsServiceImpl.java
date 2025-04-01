package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.PartSeedDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Optional;

@Service
public class PartsServiceImpl implements PartsService {
    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final PartsRepository partsRepository;

    public PartsServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, PartsRepository partsRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partsRepository = partsRepository;
    }


    @Override
    public boolean areImported() {
        return this.partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(PARTS_FILE_PATH)));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();


        PartSeedDto[] partSeedDtos = this.gson.fromJson(readPartsFileContent(), PartSeedDto[].class);

        for (PartSeedDto partSeedDto : partSeedDtos) {

            Optional<Part> optional = this.partsRepository.getByPartName(partSeedDto.getPartName());

            if(!this.validationUtil.isValid(partSeedDto) || optional.isPresent()){

                sb.append("Invalid part").append(System.lineSeparator());
                continue;

            }

            Part part = this.modelMapper.map(partSeedDto, Part.class);
            this.partsRepository.saveAndFlush(part);


            DecimalFormat df = new DecimalFormat("0.0#");

            sb.append(String.format("Successfully imported part %s - %s", part.getPartName(), df.format(part.getPrice()))).append(System.lineSeparator());





        }


        return sb.toString();

    }
}
