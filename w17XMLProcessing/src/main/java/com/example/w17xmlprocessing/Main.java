package com.example.w17xmlprocessing;

import com.example.w17xmlprocessing.models.AddressDTO;
import com.example.w17xmlprocessing.models.PersonDTO;
import com.example.w17xmlprocessing.models.PhoneBook;
import com.example.w17xmlprocessing.models.PhoneNumber;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        JAXBContext personContext = JAXBContext.newInstance(PersonDTO.class);
        Marshaller personMarshaller = personContext.createMarshaller();
        personMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        AddressDTO address = new AddressDTO("BG", "Plovdiv");
        PersonDTO person = new PersonDTO("First", "Last", 24, address);

        personMarshaller.marshal(person, System.out);


        Unmarshaller personUnmarshaller = personContext.createUnmarshaller();
        PersonDTO parsed = (PersonDTO) personUnmarshaller.unmarshal(System.in);

        System.out.println(parsed);

        JAXBContext bookContext = JAXBContext.newInstance(PhoneBook.class);
        Marshaller phoneBookMarshaller = bookContext.createMarshaller();
        phoneBookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        PhoneNumber phoneNumber1 = new PhoneNumber("Gosho", "12131");
        PhoneNumber phoneNumber2 = new PhoneNumber("Tosho", "12111");
        PhoneNumber phoneNumber3 = new PhoneNumber("Pesho", "12781");

        PhoneBook book = new PhoneBook("Pesho", List.of("First", "Second", "Third"), List.of(phoneNumber1, phoneNumber2, phoneNumber3));
        phoneBookMarshaller.marshal(book, System.out);


        Unmarshaller bookUnmarshaller = bookContext.createUnmarshaller();
        PhoneBook parsedBook = (PhoneBook) bookUnmarshaller.unmarshal(System.in);


    }
}
