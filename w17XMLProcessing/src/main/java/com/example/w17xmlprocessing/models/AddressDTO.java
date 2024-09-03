package com.example.w17xmlprocessing.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressDTO {

    private String country;
    private String city;

    public AddressDTO() {
    }

    public AddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
