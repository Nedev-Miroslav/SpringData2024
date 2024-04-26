import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class P08GetEmployeesWithProject {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(reader.readLine());

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();


        Employee foundEmployee = entityManager.createQuery("FROM Employee e WHERE e.id = ?1", Employee.class)
                .setParameter(1, input)
                .getSingleResult();

                System.out.printf("%s %s - %s%n", foundEmployee.getFirstName(), foundEmployee.getLastName(), foundEmployee.getJobTitle());

                foundEmployee.getProjects()
                        .stream()
                        .sorted(Comparator.comparing(Project::getName))
                        .forEach(p -> System.out.printf("      %s%n", p.getName()));




        entityManager.getTransaction().commit();


    }
}
