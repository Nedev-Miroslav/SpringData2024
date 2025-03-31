package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.CarRootDto;
import softuni.exam.models.dto.xmls.CarSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.CarType;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.parsers.MyXmlParser;
import softuni.exam.util.validation.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CarsServiceImpl implements CarsService {
    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";

    private final CarsRepository carsRepository;
    private final MyXmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    public CarsServiceImpl(CarsRepository carsRepository, MyXmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carsRepository = carsRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(CARS_FILE_PATH)));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        CarRootDto carRootDto = xmlParser.parse(CarRootDto.class, CARS_FILE_PATH);

        for (CarSeedDto carSeedDto : carRootDto.getCarSeedDto()) {

            Optional<Car> optional = this.carsRepository.getByPlateNumber(carSeedDto.getPlateNumber());

            if(!this.validationUtil.isValid(carSeedDto) || optional.isPresent()){
                sb.append("Invalid car").append(System.lineSeparator());
                continue;

            }


            Car car = this.modelMapper.map(carSeedDto, Car.class);
            car.setCarType(CarType.valueOf(carSeedDto.getCarType()));

            this.carsRepository.saveAndFlush(car);

            sb.append(String.format("Successfully imported car %s - %s", car.getCarMake(), car.getCarModel())).append(System.lineSeparator());
        }


        return sb.toString();
    }
}
