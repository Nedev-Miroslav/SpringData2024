import entities.Department;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P12EmployeesMaximumSalaries {
    public static void main(String[] args) {


        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();


        List<Department> resultList = entityManager.createQuery("FROM Department", Department.class).getResultList();

        resultList.forEach(d -> {
            double departmentMaxSalary = d.getEmployees()
                    .stream()
                    .mapToDouble(e -> e.getSalary().doubleValue())
                    .max().orElse(0);

            if (departmentMaxSalary < 30000 || departmentMaxSalary > 70000) {
                System.out.printf("%s %.2f%n", d.getName(), departmentMaxSalary);
            }
        });


        entityManager.getTransaction().commit();

    }
}
