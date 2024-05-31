package com.example.w13springdataautomappingobjects.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeInfoDTO {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;

    public EmployeeInfoDTO() {
    }

    public EmployeeInfoDTO(String firstName, String lastName, BigDecimal salary, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
