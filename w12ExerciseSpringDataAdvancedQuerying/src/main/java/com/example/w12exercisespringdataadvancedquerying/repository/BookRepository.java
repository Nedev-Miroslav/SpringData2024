package com.example.w12exercisespringdataadvancedquerying.repository;


import com.example.w12exercisespringdataadvancedquerying.model.entity.AgeRestriction;
import com.example.w12exercisespringdataadvancedquerying.model.entity.Book;
import com.example.w12exercisespringdataadvancedquerying.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);


    List<Book> findByAgeRestriction(AgeRestriction parsed);


    List<Book> findByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal first, BigDecimal second);


    List<Book> findByReleaseDateBeforeOrReleaseDateAfter(LocalDate year, LocalDate year2);

    List<Book> findByReleaseDateBefore(LocalDate before);


    List<Book> findByTitleContaining(String input);

    List<Book> findByAuthorLastNameStartingWith(String input);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :minLength")
    int countByTitleLengthGreaterThan(int minLength);

    BookInfo findByTitle(String title);

    @Query("UPDATE Book b " +
            "SET b.copies = b.copies + :additionalCopies " +
            "WHERE b.id = :id")
    @Modifying
    @Transactional
    int updateBookCopiesById(int id, int additionalCopies);


    @Query("UPDATE Book b SET b.copies = b.copies + :count WHERE b.releaseDate > :parse")
    @Modifying
    @Transactional
    int updatedWithNewCopiesAfterInputDate(LocalDate parse, int count);

    @Transactional
    int deleteByCopiesLessThan(int amount);
}
