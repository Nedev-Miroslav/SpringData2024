package com.example.w18exercisexmlprocessing.service;

import jakarta.xml.bind.JAXBException;

public interface CustomerService {

    void seedCustomers() throws JAXBException;

    void exportOrderedCustomers() throws JAXBException;

    void exportCustomersWithBoughtCars() throws JAXBException;
}
