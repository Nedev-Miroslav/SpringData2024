import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.List;

public class P09FindTheLatest10Projects {
    public static void main(String[] args) {


        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        List<Project> resultList = entityManager.createQuery("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        resultList
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                        .forEach(p -> System.out.printf("Project name: %s\n" +
                                " \tProject Description: %s\n" +
                                " \tProject Start Date: %s\n" +
                                " \tProject End Date: %s\n", p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate()));



        entityManager.getTransaction().commit();

    }
}



