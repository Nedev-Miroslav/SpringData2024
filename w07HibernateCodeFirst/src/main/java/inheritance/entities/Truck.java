package inheritance.entities;

import composition.Driver;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle{
    private static final String TRUCK_TYPE = "TRUCK";
    @Column(name= "load_capacity")
    private double loadCap;

    @ManyToMany
     @JoinTable(
             name = "trucks_drivers",
             joinColumns =  @JoinColumn(name = "tuck_id", referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id")
     )
    private List<Driver> drivers;

    public Truck(){}

    public Truck(String model, BigDecimal price, String fuelType, double loadCap) {
        super(TRUCK_TYPE, model, price, fuelType);
        this.loadCap = loadCap;
    }
}
