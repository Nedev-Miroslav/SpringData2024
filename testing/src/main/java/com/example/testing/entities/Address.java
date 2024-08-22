package com.example.testing.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity{

    private String Street;

    private Town town;
    @ManyToOne
    @JoinColumn(name = "town", referencedColumnName = "id")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }





    public Address() {
    }



    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }
}
