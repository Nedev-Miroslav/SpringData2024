package com.example.w12exercisespringdataadvancedquerying.repository;


import com.example.w12exercisespringdataadvancedquerying.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY SIZE(a.books) DESC")
    List<Author> findAllByBooksSizeDESC();


    List<Author> findByFirstNameEndingWith(String input);

    @Query("SELECT SUM(b.copies) FROM Author a JOIN a.books b " +
            "WHERE a.firstName = :firstName And a.lastName = :lastName")
    int countBookCopiesByAuthorName(String firstName, String lastName);
}
