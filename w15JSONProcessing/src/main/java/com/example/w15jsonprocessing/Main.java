package com.example.w15jsonprocessing;

import com.example.w15jsonprocessing.dtos.AddressDTO;
import com.example.w15jsonprocessing.dtos.PersonDTO;
import com.example.w15jsonprocessing.service.PersonService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Main implements CommandLineRunner {

    private final Gson gson;

    private final PersonService personService;

    @Autowired
    public Main(@Qualifier("withNULLs") Gson gson, PersonService personService) {
        this.gson = gson;
        this.personService = personService;
    }

    @Override
    public void run(String... args) throws Exception {



        List<PersonDTO> list = new ArrayList<>();


        PersonDTO personDTO = new PersonDTO("First",
                null,
                24,
                true,
                List.of(3, 6, 18, 24, 36, 42),
                new AddressDTO("BG", "Burgas"));

        PersonDTO personDTO2 = new PersonDTO("Second",
                null,
                25,
                false,
                List.of(3, 6, 18, 24, 36, 42),
                new AddressDTO("BG", "VT"));

        String json = gson.toJson(personDTO);

        list.add(personDTO);
        list.add(personDTO2);

        String toPrint = gson.toJson(list);


        System.out.println(toPrint);

        String jsonStr = """
                        [
                          {
                            "firstName": "First",
                            "lastName": null,
                            "age": 24,
                            "isMarried": true,
                            "lotteryNumbers": [
                              3,
                              6,
                              18,
                              24,
                              36,
                              42
                            ],
                            "address": {
                              "country": "BG",
                              "city": "Burgas"
                            }
                          },
                          {
                            "firstName": "Second",
                            "lastName": null,
                            "age": 25,
                            "isMarried": false,
                            "lotteryNumbers": [
                              3,
                              6,
                              18,
                              24,
                              36,
                              42
                            ],
                            "address": {
                              "country": "BG",
                              "city": "VT"
                            }
                          }
                        ]  
                     """;


//        PersonDTO[] personDTO1 = gson.fromJson(jsonStr, PersonDTO[].class);
//        for (PersonDTO dto : personDTO1) {
//            System.out.println(dto);
//        }


    }
}
