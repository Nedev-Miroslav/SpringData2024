package com.example.w18exercisexmlprocessing.service.dto.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedRootDto implements Serializable {

    @XmlElement(name = "supplier")
    private List<SupplierSeedDto> supplierSeedDtoList;

    public List<SupplierSeedDto> getSupplierSeedDtoList() {
        return supplierSeedDtoList;
    }

    public void setSupplierSeedDtoList(List<SupplierSeedDto> supplierSeedDtoList) {
        this.supplierSeedDtoList = supplierSeedDtoList;
    }
}
