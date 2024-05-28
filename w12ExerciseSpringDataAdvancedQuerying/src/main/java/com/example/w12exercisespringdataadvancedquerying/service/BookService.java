package com.example.w12exercisespringdataadvancedquerying.service;


import com.example.w12exercisespringdataadvancedquerying.model.entity.Book;
import com.example.w12exercisespringdataadvancedquerying.model.entity.EditionType;
import com.example.w12exercisespringdataadvancedquerying.repository.BookInfo;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);


    List<Book> findByAgeRestriction(String inputCategory);


    List<Book> selectAllBooksWithGoldenTypeAndLessThan5000Copies(EditionType editionType, int copies);

    List<Book> selectAllBooksWithPriceLowerThan5OrHigherThan40(double lowBound, double highBound);

    List<Book> selectBooksThatNotReleaseInGivenYear(String inputYear);

    List<Book> selectBooksByGivenDate(String inputYear);

    List<Book> selectByInputContaining(String input);

    List<Book> selectTitleByAuthorNameStartingWith(String input);

    int findTitleCountLongerThan(int minLength);

    BookInfo findInfoByTitle(String title);

    void sellCopies(int bookId, int copiesSold);

    int updateWithAddingNewCopies(String inputYear, int count);

    int deleteWithCopiesLessThan(int amount);
}
