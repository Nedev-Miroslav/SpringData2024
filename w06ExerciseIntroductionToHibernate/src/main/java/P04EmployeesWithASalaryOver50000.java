import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

public class P04EmployeesWithASalaryOver50000 {
    public static void main(String[] args) throws IOException {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);


//        Друго решение на задачата
//        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class).getResultList();
//        resultList.forEach(e -> System.out.println(e.getFirstName()));



        entityManager.getTransaction().commit();



    }
}
