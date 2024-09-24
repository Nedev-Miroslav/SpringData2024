package com.example.w18exercisexmlprocessing.service;

import jakarta.xml.bind.JAXBException;

public interface SupplierService {

    void seedSupplier() throws JAXBException;

    void exportLocalSuppliers() throws JAXBException;
}
