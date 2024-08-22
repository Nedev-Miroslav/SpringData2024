package com.example.testing.entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    protected Integer id;

    protected BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
