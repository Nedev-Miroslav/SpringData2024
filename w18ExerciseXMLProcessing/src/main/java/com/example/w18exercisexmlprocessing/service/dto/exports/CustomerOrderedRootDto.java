package com.example.w18exercisexmlprocessing.service.dto.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerOrderedRootDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerOrderedDto> customerOrderedDto;


    public List<CustomerOrderedDto> getCustomerOrderedDto() {
        return customerOrderedDto;
    }

    public void setCustomerOrderedDto(List<CustomerOrderedDto> customerOrderedDto) {
        this.customerOrderedDto = customerOrderedDto;
    }
}
