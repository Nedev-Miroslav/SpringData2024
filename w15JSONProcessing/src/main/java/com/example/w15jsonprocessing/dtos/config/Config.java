package com.example.w15jsonprocessing.dtos.config;

import com.example.w15jsonprocessing.service.AddressService;
import com.example.w15jsonprocessing.service.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {
@Bean("withNULLs")
@Primary
    public Gson gsonWithNull(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setPrettyPrinting()
                .create();


    }

    @Bean("withoutNULLs")
    public Gson gsonWithoutNULLs(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();


    }

    @Bean
    public PersonService personService(
            AddressService addressService,
            @Value("{yourproject.yourkey.config1}") String config1){
        System.out.println(config1);
        return new PersonService(addressService);
    }



}
