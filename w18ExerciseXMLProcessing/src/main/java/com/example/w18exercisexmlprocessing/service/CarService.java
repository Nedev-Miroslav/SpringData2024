package com.example.w18exercisexmlprocessing.service;

import jakarta.xml.bind.JAXBException;

public interface CarService {

    void seedCars() throws JAXBException;

    void exportToyotaCars() throws JAXBException;

    void exportCarsAndParts() throws JAXBException;
}
