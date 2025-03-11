package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.CompanyRootDto;
import softuni.exam.models.dto.xmls.CompanySeedDto;
import softuni.exam.models.entity.Company;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.parsers.XmlParser;
import softuni.exam.util.validation.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final String FILE_PATH = "src/main/resources/files/xml/companies.xml";

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public CompanyServiceImpl(CompanyRepository companyRepository, CountryRepository countryRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

//        JAXBContext jaxbContext = JAXBContext.newInstance(CompanyRootDto.class);
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        CompanyRootDto companyRootDto = (CompanyRootDto) unmarshaller.unmarshal(new File(FILE_PATH));

        CompanyRootDto companyRootDto = xmlParser.parse(CompanyRootDto.class, FILE_PATH);



        for (CompanySeedDto companySeedDto : companyRootDto.getCompanySeedDto()) {


            Optional<Company> optionalCompany = this.companyRepository.findByName(companySeedDto.getName());


            if(!this.validationUtil.isValid(companySeedDto) || optionalCompany.isPresent()) {
                sb.append("Invalid company").append(System.lineSeparator());
                continue;

            }

            Company company = this.modelMapper.map(companySeedDto, Company.class);
            company.setCountry(this.countryRepository.getById(companySeedDto.getCountry()));
            company.setDateEstablished(companySeedDto.getLocalDate());
            company.setName(companySeedDto.getName());
            

            this.companyRepository.saveAndFlush(company);

            sb.append(String.format("Successfully imported company %s - %d", company.getName(), company.getCountry().getId())).append(System.lineSeparator());


        }



        return sb.toString();

    }
}
