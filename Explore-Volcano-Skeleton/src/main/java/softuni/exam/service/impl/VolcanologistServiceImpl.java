package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.VolcanologistRootDto;
import softuni.exam.models.dto.xmls.VolcanologistSeedDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.parsers.MyXmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private static final String FILE_PATH = "src/main/resources/files/xml/volcanologists.xml";


    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoRepository volcanoRepository;

    private final MyXmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;



    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, VolcanoRepository volcanoRepository, MyXmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
        this.xmlParser = xmlParser;

        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();


        VolcanologistRootDto volcanologistRootDto = xmlParser.parse(VolcanologistRootDto.class, FILE_PATH);

        for (VolcanologistSeedDto volcanologistSeedDto : volcanologistRootDto.getVolcanologistSeedDto()) {
            Optional<Volcanologist> optionalVolcanologist = this.volcanologistRepository.findByFirstNameAndLastName(volcanologistSeedDto.getFirstName(), volcanologistSeedDto.getLastName());
            Optional<Volcano> optionalVolcano = this.volcanoRepository.findById(volcanologistSeedDto.getVolcano());

            if(!this.validationUtil.isValid(volcanologistSeedDto) || optionalVolcanologist.isPresent() || optionalVolcano.isEmpty()){
                sb.append("Invalid volcanologist").append(System.lineSeparator());
                continue;


            }


            Volcanologist volcanologist = this.modelMapper.map(volcanologistSeedDto, Volcanologist.class);
            volcanologist.setObserveVolcanoes(optionalVolcano.get());
            this.volcanologistRepository.saveAndFlush(volcanologist);


            sb.append(String.format("Successfully imported volcanologist %s %s",
                            volcanologist.getFirstName(), volcanologist.getLastName()))
                    .append(System.lineSeparator());

        }






        return sb.toString();
    }
}