import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class P07AddressesWithEmployeeCount {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();


        entityManager.createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a -> System.out.printf("%s, %s - %d employees%n",
                                a.getText(), a.getTown().getName(), a.getEmployees().size()));






        entityManager.getTransaction().commit();



    }
}
