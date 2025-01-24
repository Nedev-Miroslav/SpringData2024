package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Job;

import java.util.Optional;
import java.util.Set;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "SELECT j from Job j WHERE j.salary >= 5000 AND j.hoursAWeek <= 30 ORDER BY j.salary DESC")
    Set<Job> findBestJobs();

    Optional<Job> findByTitle(String jobTitle);
}
