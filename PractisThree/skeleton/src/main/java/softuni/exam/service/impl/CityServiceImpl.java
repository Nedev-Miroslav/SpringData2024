package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.CitiesSeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private static final String FILE_PATH = "src/main/resources/files/json/cities.json";
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();


        CitiesSeedDto[] citiesSeedDtos = this.gson.fromJson(readCitiesFileContent(), CitiesSeedDto[].class);


        for (CitiesSeedDto citiesSeedDto : citiesSeedDtos) {

            Optional<City> optional = this.cityRepository.findByCityName(citiesSeedDto.getCityName());

            if(!this.validationUtil.isValid(citiesSeedDto) || optional.isPresent()){
                sb.append("Invalid city").append(System.lineSeparator());
                continue;
            }

            City city = this.modelMapper.map(citiesSeedDto, City.class);
            city.setCountry(this.countryRepository.findById(citiesSeedDto.getCountry()).get());
            cityRepository.saveAndFlush(city);

            sb.append(String.format("Successfully imported city %s - %d", city.getCityName(), city.getPopulation())).append(System.lineSeparator());

        }


        return sb.toString();
    }
}
