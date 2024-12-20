package com.example.w18exercisexmlprocessing.service.dto.imports;

import com.example.w18exercisexmlprocessing.util.adaptors.LocalDateTimeAdaptor;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedDto implements Serializable {

        @XmlAttribute
        private String name;
        @XmlElement(name = "birth-date")
        @XmlJavaTypeAdapter(LocalDateTimeAdaptor.class)
        private LocalDateTime birthDate;
        @XmlElement(name = "is-young-driver")
        private boolean isYoungDriver;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
