package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.ApartmentRootDto;
import softuni.exam.models.dto.xmls.ApartmentSeedDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.Validations.ValidationUtil;
import softuni.exam.util.xmlParser.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final String FILE_PATH = "src/main/resources/files/xml/apartments.xml";

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
//        Този код се използва ако нямам xmlParser
//        JAXBContext jaxbContext = JAXBContext.newInstance(ApartmentRootDto.class);
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        ApartmentRootDto apartmentRootDto = (ApartmentRootDto) unmarshaller.unmarshal(new File(FILE_PATH));


        ApartmentRootDto apartmentRootDto = xmlParser.parse(ApartmentRootDto.class, FILE_PATH);

        for (ApartmentSeedDto apartmentSeedDto : apartmentRootDto.getApartmentSeedDtoList()) {

            Optional<Town> townOptional = this.townRepository.findByTownName(apartmentSeedDto.getTown());
            Optional<Apartment> apartmentOptional = this.apartmentRepository.findByArea(apartmentSeedDto.getArea());

            if(!this.validationUtil.isValid(apartmentSeedDto) || (townOptional.isPresent() && apartmentOptional.isPresent())){
                sb.append("Invalid apartment").append(System.lineSeparator());
                continue;
            }

            Apartment apartment = this.modelMapper.map(apartmentSeedDto, Apartment.class);
            apartment.setTownAp(this.townRepository.findByTownName(apartmentSeedDto.getTown()).get());
            apartment.setApartmentType(ApartmentType.valueOf(apartmentSeedDto.getApartmentType()));
            this.apartmentRepository.saveAndFlush(apartment);

            sb.append(String.format("Successfully imported apartment %s - %.2f", apartmentSeedDto.getApartmentType(), apartment.getArea())).append(System.lineSeparator());
        }




        return sb.toString();
    }
}
