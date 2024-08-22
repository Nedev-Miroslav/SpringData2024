package com.example.testing.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity{

    private String name;
    private String description;



    private Set<Employee> employees;

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @ManyToMany(mappedBy = "projects",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}

