package com.example.w18exercisexmlprocessing.service.dto.exports;

import com.example.w18exercisexmlprocessing.util.adaptors.LocalDateTimeAdaptor;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerOrderedDto implements Serializable {

    @XmlElement
    private long id;
    @XmlElement
    private String name;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdaptor.class)
    private LocalDateTime birthDate;
    @XmlElement(name = "is-young-driver")
    private boolean isYoungDriver;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
