package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByFirstName(String firstName);

    Optional<Person> findByPhone(String phone);

    Optional<Person> findByEmail(String email);
}
