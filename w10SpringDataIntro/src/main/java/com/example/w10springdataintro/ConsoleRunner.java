package com.example.w10springdataintro;

import com.example.w10springdataintro.entities.Author;
import com.example.w10springdataintro.entities.Book;
import com.example.w10springdataintro.repositories.AuthorRepository;
import com.example.w10springdataintro.repositories.BookRepository;
import com.example.w10springdataintro.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }



    @Override
    public void run(String... args) throws Exception {

        //  this.seedService.seedAuthors();
        //  this.seedService.seedCategories();
        //  this.seedService.seedAll();

        //  this.p01BooksAfter2000();
        //  this.p02AllAuthorsWithBookBefore1990();
        //  this.p03AllAuthorsOrderByBookCount();
        this.p04GetAllBookFromGeorgePowell();


    }


    private void p01BooksAfter2000() {
        LocalDate year2000= LocalDate.of(2000, 12, 31);
        List<Book> books = this.bookRepository.findByReleaseDateAfter(year2000);

        books.forEach(b -> System.out.println(b.getTitle()));
    }

    private void p02AllAuthorsWithBookBefore1990() {
        LocalDate before1990 = LocalDate.of(1990, 1, 1);
        List<Author> authors = this.authorRepository.findDistinctByBooksReleaseDateBefore(before1990);

        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));

    }

    private void p03AllAuthorsOrderByBookCount() {
        List<Author> authors = this.authorRepository.findAll();

        authors
                .stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(a -> System.out.printf("%s %s -> %d%n", a.getLastName(), a.getLastName(), a.getBooks().size()));



    }


    private void p04GetAllBookFromGeorgePowell() {
        String firstName = "George";
        String lastName = "Powell";

        List<Book> books = this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(firstName, lastName);

        books.forEach(b -> System.out.printf("%s %s %d%n", b.getTitle(), b.getReleaseDate(), b.getCopies()));

    }



}
