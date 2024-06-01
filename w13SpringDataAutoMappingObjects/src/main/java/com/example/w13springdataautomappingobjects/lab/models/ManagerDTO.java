package com.example.w13springdataautomappingobjects.lab.models;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerDTO {
    private String firstName;
    private String lastName;
    private List<EmployeeDTO> employees;

    public ManagerDTO() {
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

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        String employeesStr = employees.stream()
                .map(e -> " - " + e.toString())
                .collect(Collectors.joining());

        return String.format("%s %s | Employees: %d%n%s",
                firstName, lastName, employees.size(), employeesStr);
    }
}
