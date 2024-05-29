package com.example.w12exercisespringdataadvancedquerying.service.impl;

import com.example.w12exercisespringdataadvancedquerying.model.entity.*;
import com.example.w12exercisespringdataadvancedquerying.repository.BookInfo;
import com.example.w12exercisespringdataadvancedquerying.repository.BookRepository;
import com.example.w12exercisespringdataadvancedquerying.service.AuthorService;
import com.example.w12exercisespringdataadvancedquerying.service.BookService;
import com.example.w12exercisespringdataadvancedquerying.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAgeRestriction(String inputCategory) {
        AgeRestriction parsed = AgeRestriction.valueOf(inputCategory);
        return this.bookRepository.findByAgeRestriction(parsed);
    }

    @Override
    public List<Book> selectAllBooksWithGoldenTypeAndLessThan5000Copies(EditionType editionType, int copies) {

        return this.bookRepository.findByEditionTypeAndCopiesLessThan(editionType, copies);
    }

    @Override
    public List<Book> selectAllBooksWithPriceLowerThan5OrHigherThan40(double lowBound, double highBound) {
        BigDecimal first = BigDecimal.valueOf(lowBound);
        BigDecimal second = BigDecimal.valueOf(highBound);
        return this.bookRepository.findByPriceLessThanOrPriceGreaterThan(first, second);
    }

    @Override
    public List<Book> selectBooksThatNotReleaseInGivenYear(String inputYear) {
        String startYear = String.format("%s-01-01", inputYear);
        String endYear = String.format("%s-12-31", inputYear);
        LocalDate year = LocalDate.parse(startYear);
        LocalDate year2 = LocalDate.parse(endYear);

        return this.bookRepository.findByReleaseDateBeforeOrReleaseDateAfter(year, year2);
    }

    @Override
    public List<Book> selectBooksByGivenDate(String inputYear) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate before = LocalDate.parse(inputYear, formatter);

        return this.bookRepository.findByReleaseDateBefore(before);
    }

    @Override
    public List<Book> selectByInputContaining(String input) {

        return this.bookRepository.findByTitleContaining(input);
    }

    @Override
    public List<Book> selectTitleByAuthorNameStartingWith(String input) {
        return this.bookRepository.findByAuthorLastNameStartingWith(input);
    }

    @Override
    public int findTitleCountLongerThan(int minLength) {
        return this.bookRepository.countByTitleLengthGreaterThan(minLength);
    }

    @Override
    public BookInfo findInfoByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public void sellCopies(int bookId, int copiesSold) {
        bookRepository.updateBookCopiesById(bookId, copiesSold);
    }

    @Override
    public int updateWithAddingNewCopies(String inputYear, int count) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate parse = LocalDate.parse(inputYear, formatter);


        return this.bookRepository.updatedWithNewCopiesAfterInputDate(parse, count);
    }

    @Override
    public int deleteWithCopiesLessThan(int amount) {
        return this.bookRepository.deleteByCopiesLessThan(amount);
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
