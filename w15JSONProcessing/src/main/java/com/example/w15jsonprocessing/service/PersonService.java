package com.example.w15jsonprocessing.service;

import org.springframework.stereotype.Service;


public class PersonService {

    private AddressService addressService;

    public PersonService(AddressService addressService) {
        this.addressService = addressService;
    }
}
