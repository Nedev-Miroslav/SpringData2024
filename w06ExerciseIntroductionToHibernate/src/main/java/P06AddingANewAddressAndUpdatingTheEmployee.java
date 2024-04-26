import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class P06AddingANewAddressAndUpdatingTheEmployee {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));



        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();


        Town town = entityManager.find(Town.class, 32);
        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);
        entityManager.persist(address);

        String lastName = reader.readLine();
        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getResultList();

        if(resultList.size() > 0){
            Employee employee = resultList.get(0);
            employee.setAddress(address);
            entityManager.persist(employee);
        }


        entityManager.getTransaction().commit();

    }
}
