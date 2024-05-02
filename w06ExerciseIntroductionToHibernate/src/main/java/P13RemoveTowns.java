import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class P13RemoveTowns {
    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = reader.readLine();

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        List<Town> resultList = entityManager.createQuery("FROM Town WHERE name = :name", Town.class)
                .setParameter("name", input)
                .getResultList();


        if (!resultList.isEmpty()) {
            Town town = resultList.get(0);
            List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a JOIN a.town t WHERE t.name = :name", Address.class)
                    .setParameter("name", town.getName())
                    .getResultList();

            addresses.forEach(a -> {
                a.getEmployees().forEach(e -> {
                    e.setAddress(null);
                    entityManager.persist(a);
                });
                entityManager.remove(a);
            });

            System.out.printf("%d address in %s deleted", addresses.size(), town.getName());
            entityManager.remove(town);
        }


        entityManager.getTransaction().commit();


    }
}
