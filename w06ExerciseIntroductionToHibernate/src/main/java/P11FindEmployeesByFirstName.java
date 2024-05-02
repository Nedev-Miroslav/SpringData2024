import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class P11FindEmployeesByFirstName {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = reader.readLine();

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

       entityManager.createQuery("FROM Employee e WHERE e.firstName " +
                        "LIKE CONCAT(?1, '%')", Employee.class)
                .setParameter(1, input)
                .getResultStream().forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));


        entityManager.getTransaction().commit();
    }
}
