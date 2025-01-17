package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.ForecastRootDto;
import softuni.exam.models.dto.xmls.ForecastSeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private static final String FILE_PATH = "src/main/resources/files/xml/forecasts.xml";

    private final ForecastRepository forecastRepository;

    private final CityRepository cityRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        ForecastRootDto forecastRootDto = xmlParser.parse(ForecastRootDto.class, FILE_PATH);

        for (ForecastSeedDto forecastSeedDto : forecastRootDto.getForecastSeedDto()) {
            String strDayOfWeek = String.valueOf(forecastSeedDto.getDayOfWeek());

            if (!strDayOfWeek.equals("FRIDAY") && !strDayOfWeek.equals("SATURDAY") && !strDayOfWeek.equals("SUNDAY")) {
                sb.append("Invalid forecast").append(System.lineSeparator());
                continue;
            }

            City city = this.cityRepository.getById(forecastSeedDto.getCity());

            Optional<Forecast> optionalForecast = this.forecastRepository.findByCityAndDayOfWeekLike(city, strDayOfWeek);



            if (!this.validationUtil.isValid(forecastSeedDto) || optionalForecast.isPresent()) {
                sb.append("Invalid forecast").append(System.lineSeparator());
                continue;

            }

            Forecast forecast = this.modelMapper.map(forecastSeedDto, Forecast.class);
            forecast.setCity(city);
            this.forecastRepository.saveAndFlush(forecast);


            sb.append(String.format("Successfully imported %s - %.2f",
                            forecast.getDayOfWeek(), forecast.getMaxTemperature()))
                    .append(System.lineSeparator());


        }


        return sb.toString();
    }

    @Override
    public String exportForecasts() {
        return this.forecastRepository.findAllByDayOfWeek_AndCity_PopulationOrderByMaxTemperatureDescIdAsc()
                .stream()
                .map(f -> String.format("City: %s:\n" +
                        "-min temperature: %.2f\n" +
                        "--max temperature: %.2f\n" +
                        "---sunrise: %s\n" +
                        "----sunset: %s\n",
                        f.getCity().getCityName()
                        , f.getMinTemperature()
                        , f.getMaxTemperature()
                        , f.getSunrise()
                        , f.getSunset()
                        ))

                .collect(Collectors.joining());
    }

}
