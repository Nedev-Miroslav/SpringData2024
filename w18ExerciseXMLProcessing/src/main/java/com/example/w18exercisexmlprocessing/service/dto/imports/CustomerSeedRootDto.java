package com.example.w18exercisexmlprocessing.service.dto.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerSeedDto> customerSeedDto;

    public List<CustomerSeedDto> getCustomerSeedDto() {
        return customerSeedDto;
    }

    public void setCustomerSeedDto(List<CustomerSeedDto> customerSeedDto) {
        this.customerSeedDto = customerSeedDto;
    }
}
