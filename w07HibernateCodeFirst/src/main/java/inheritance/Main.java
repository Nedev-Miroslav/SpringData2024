package inheritance;

import composition.Company;
import composition.PlateNumber;
import inheritance.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("Main");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        PlateNumber plate = new PlateNumber("asdasd");

        Company company = new Company("Airline1");

        Vehicle car = new Car("Corsa", BigDecimal.TEN, "Petrol", 5, plate);
        Vehicle bike = new Bike("BMX", BigDecimal.ONE, "None");
        Plane plane = new Plane("Boeing", BigDecimal.TEN, "Kerosine", 100, company);
        Vehicle truck = new Truck("Scania",BigDecimal.ONE, "Diesel", 40);


        entityManager.persist(company);
        entityManager.persist(plate);
        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(truck);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
