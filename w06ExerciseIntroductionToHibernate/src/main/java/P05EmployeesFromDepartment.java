import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P05EmployeesFromDepartment {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();


        List<Employee> resultList = entityManager.createQuery("SELECT e FROM Employee e JOIN e.department d " +
                " WHERE d.id = 6" +
                " ORDER BY e.salary, e.id", Employee.class).getResultList();

        resultList.forEach(e -> System.out.printf("%s %s from Research and Development - $%.2f%n",
                e.getFirstName(), e.getLastName(), e.getSalary()));


        entityManager.getTransaction().commit();

    }
}
