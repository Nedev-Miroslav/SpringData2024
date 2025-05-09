package com.example.w18exercisexmlprocessing.service.dto.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesRootDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerTotalSalesDto> customerTotalSalesDtos;

    public List<CustomerTotalSalesDto> getCustomerTotalSalesDtos() {
        return customerTotalSalesDtos;
    }

    public void setCustomerTotalSalesDtos(List<CustomerTotalSalesDto> customerTotalSalesDtos) {
        this.customerTotalSalesDtos = customerTotalSalesDtos;
    }
}