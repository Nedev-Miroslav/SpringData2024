package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.PersonSeedDto;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.validation.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private static final String FILE_PATH = "src/main/resources/files/json/people.json";
    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;



    public PersonServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, PersonRepository personRepository, CountryRepository countryRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));

    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();


        PersonSeedDto[] personSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), PersonSeedDto[].class);


        for (PersonSeedDto personSeedDto : personSeedDtos) {
            Optional<Person> optionalName = this.personRepository.findByFirstName(personSeedDto.getFirstName());
            Optional<Person> optionalPhone = this.personRepository.findByPhone(personSeedDto.getPhone());
            Optional<Person> optionalEmail = this.personRepository.findByEmail(personSeedDto.getEmail());


            if(!this.validationUtil.isValid(personSeedDto) || optionalName.isPresent() || optionalEmail.isPresent() || optionalPhone.isPresent()) {

                sb.append("Invalid person").append(System.lineSeparator());
                continue;

            }


            Person person = this.modelMapper.map(personSeedDto, Person.class);
            person.setCountry(this.countryRepository.findById(personSeedDto.getCountry()).get());

            this.personRepository.saveAndFlush(person);




            sb.append(String.format("Successfully imported person %s %s", person.getFirstName(), person.getLastName())).append(System.lineSeparator());




        }



        return sb.toString();

    }
}
