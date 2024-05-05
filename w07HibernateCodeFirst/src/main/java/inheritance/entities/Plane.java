package inheritance.entities;

import composition.Company;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle{
    private static final String PLANE_TYPE = "PLANE";
    @Column(name = "passenger_capacity")
    private int passengerCapacity;
    @ManyToOne
    private Company owner;

    public Plane(){}

    public Plane(String model, BigDecimal price, String fuelType, int passengerCapacity, Company owner) {
        super(PLANE_TYPE, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
        this.owner = owner;
    }
}
