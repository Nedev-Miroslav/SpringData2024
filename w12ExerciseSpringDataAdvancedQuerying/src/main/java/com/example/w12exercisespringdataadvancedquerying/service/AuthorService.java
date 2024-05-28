package com.example.w12exercisespringdataadvancedquerying.service;



import com.example.w12exercisespringdataadvancedquerying.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<Author> selectAuthorNameStartingWith(String input);

    int getTotalCopiesCountFor(String firstName, String lastName);
}
