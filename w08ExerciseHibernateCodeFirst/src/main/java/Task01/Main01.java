package Task01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main01 {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("Main");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();




        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
