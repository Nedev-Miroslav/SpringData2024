package com.example.football.service.impl;

import com.example.football.models.dto.xmls.StatRootDto;
import com.example.football.models.dto.xmls.StatSeedDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.Validations.ValidationUtil;
import com.example.football.util.xmlParser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {
    private static final String FILE_PATH = "src/main/resources/files/xml/stats.xml";

    private final StatRepository statRepository;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public StatServiceImpl(StatRepository statRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importStats() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        StatRootDto statRootDto = xmlParser.parse(StatRootDto.class, FILE_PATH);

        for (StatSeedDto statSeedDto : statRootDto.getStatSeedDtos()) {

            Optional<Stat> optional = this.statRepository.findByShootingAndPassingAndEndurance(statSeedDto.getShooting(), statSeedDto.getPassing(), statSeedDto.getEndurance());
;


            if(!this.validationUtil.isValid(statSeedDto) || optional.isPresent()) {
                sb.append("Invalid Stat").append(System.lineSeparator());
                continue;
            }

            Stat stat = this.modelMapper.map(statSeedDto, Stat.class);

            this.statRepository.saveAndFlush(stat);

            sb.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f", stat.getShooting(), stat.getPassing(), stat.getEndurance())).append(System.lineSeparator());


        }


        return sb.toString();
    }
}
