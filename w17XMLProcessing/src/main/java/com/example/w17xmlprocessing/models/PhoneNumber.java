package com.example.w17xmlprocessing.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneNumber {
    private String name;
    private String number;

    public PhoneNumber() {
    }

    public String getNumber() {
        return number;
    }

    public PhoneNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
