package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.OfferRootDto;
import softuni.exam.models.dto.xmls.OfferSeedDtos;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.Validations.ValidationUtil;
import softuni.exam.util.xmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;

    private final ApartmentRepository apartmentRepository;

    private final AgentRepository agentRepository;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public OfferServiceImpl(OfferRepository offerRepository, ApartmentRepository apartmentRepository, AgentRepository agentRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.apartmentRepository = apartmentRepository;
        this.agentRepository = agentRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

//        JAXBContext jaxbContext = JAXBContext.newInstance(OfferRootDto.class);
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        OfferRootDto offerRootDto  = (OfferRootDto) unmarshaller.unmarshal(new File(FILE_PATH));


        OfferRootDto offerRootDto = xmlParser.parse(OfferRootDto.class, FILE_PATH);

        for (OfferSeedDtos offerSeedDto : offerRootDto.getOffers()) {

            Optional<Agent> optionalAgent = this.agentRepository.findByFirstName(offerSeedDto.getAgent().getName());
            Optional<Apartment> apartmentOptional = this.apartmentRepository.findById(offerSeedDto.getApartment().getId());

            if(!this.validationUtil.isValid(offerSeedDto) || optionalAgent.isEmpty() || apartmentOptional.isEmpty()){
                sb.append("Invalid offer").append(System.lineSeparator());
                continue;

            }


            Offer offer = this.modelMapper.map(offerSeedDto, Offer.class);
            offer.setAgent(optionalAgent.get());
            offer.setApartment(apartmentOptional.get());
            this.offerRepository.saveAndFlush(offer);



            sb.append(String.format("Successfully imported offer %.2f", offer.getPrice())).append(System.lineSeparator());

        }



        return sb.toString();

    }

    @Override
    public String exportOffers() {
        return this.offerRepository.findAllByApartmentType()
                .stream()
                .map(o -> String.format("Agent %s %s with offer №%d:\n" +
                        "   -Apartment area: %.2f\n" +
                        "   --Town: %s\n" +
                        "   ---Price: %.2f$\n", o.getAgent().getFirstName()
                , o.getAgent().getLastName()
                , o.getId()
                , o.getApartment().getArea()
                , o.getApartment().getTownAp().getTownName()
                , o.getPrice()))
                .collect(Collectors.joining());


//         Решение без Query
//        return this.offerRepository.findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc(ApartmentType.three_rooms)
//                .stream()
//                .map(o -> String.format("Agent %s %s with offer №%d:\n" +
//                                "   -Apartment area: %.2f\n" +
//                                "   --Town: %s\n" +
//                                "   ---Price: %.2f$\n", o.getAgent().getFirstName()
//                        , o.getAgent().getLastName()
//                        , o.getId()
//                        , o.getApartment().getArea()
//                        , o.getApartment().getTownAp().getTownName()
//                        , o.getPrice()))
//                .collect(Collectors.joining());



    }
}
