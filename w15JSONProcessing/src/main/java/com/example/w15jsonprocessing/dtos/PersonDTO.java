package com.example.w15jsonprocessing.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class PersonDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private boolean isMarried;
    @Expose
    private List<Integer> lotteryNumbers;
    @Expose
    private AddressDTO address;


    public PersonDTO(String firstName, String lastName, int age, boolean isMarried, List<Integer> lotteryNumbers, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMarried = isMarried;
        this.lotteryNumbers = lotteryNumbers;
        this.address = address;


    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isMarried=" + isMarried +
                ", lotteryNumbers=" + lotteryNumbers +
                ", address=" + address +
                '}';
    }

}
