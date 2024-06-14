package com.example.w15jsonprocessing.dtos;

import com.google.gson.annotations.Expose;

public class AddressDTO {
    @Expose
    private String country;
    @Expose
    private String city;

    public AddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
