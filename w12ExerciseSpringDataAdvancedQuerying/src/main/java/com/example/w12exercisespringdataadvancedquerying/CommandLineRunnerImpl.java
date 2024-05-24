package com.example.w12exercisespringdataadvancedquerying;

import com.example.w12exercisespringdataadvancedquerying.model.entity.Author;
import com.example.w12exercisespringdataadvancedquerying.model.entity.Book;
import com.example.w12exercisespringdataadvancedquerying.model.entity.EditionType;
import com.example.w12exercisespringdataadvancedquerying.repository.BookInfo;
import com.example.w12exercisespringdataadvancedquerying.service.AuthorService;
import com.example.w12exercisespringdataadvancedquerying.service.BookService;
import com.example.w12exercisespringdataadvancedquerying.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        Scanner scanner = new Scanner(System.in);
//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");


//        P01BooksTitlesByAgeRestriction(scanner);
//        P02GoldenBooks();
//        P03BooksByPrice();
//        P04NotReleasedBooks(scanner);
//        P05BooksReleasedBeforeDate(scanner);
//        P06AuthorsSearch(scanner);
//        P07BooksSearch(scanner);
//        P08BookTitlesSearch(scanner);
//        P09CountBooks(scanner);
//        P10TotalBookCopies(scanner);
//        P11ReducedBook(scanner);
//        ExampleForUpdate();
//        P12IncreaseBookCopies(scanner);
//        P13RemoveBooks(scanner);


    }

    private void P13RemoveBooks(Scanner scanner) {
        int amount = Integer.parseInt(scanner.nextLine());
        int deleteCount = this.bookService.deleteWithCopiesLessThan(amount);
        System.out.println(deleteCount + " books were deleted.");
    }

    private void P12IncreaseBookCopies(Scanner scanner) {
        String inputYear = scanner.nextLine();
        int count = Integer.parseInt(scanner.nextLine());
        int addedCopies = bookService.updateWithAddingNewCopies(inputYear, count);

        System.out.printf("%d books are released after %s, so total of %d book copies were added.%n",
                addedCopies, inputYear, count * addedCopies);
    }

    private void ExampleForUpdate() {
        bookService.sellCopies(1, 1000);
    }

    private void P11ReducedBook(Scanner scanner) {
        String title = scanner.nextLine();
        BookInfo info = bookService.findInfoByTitle(title);

        System.out.printf("%s %s %s %.2f%n", info.getTitle(), info.getEditionType(), info.getAgeRestriction(), info.getPrice());
    }

    private void P10TotalBookCopies(Scanner scanner) {
        String[] authorName = scanner.nextLine().split("\\s+");
        int count = authorService.getTotalCopiesCountFor(authorName[0], authorName[1]);
        System.out.printf("%s %s - %d%n", authorName[0], authorName[1], count);
    }

    private void P09CountBooks(Scanner scanner) {
        int minLength = Integer.parseInt(scanner.nextLine());
        int count = bookService.findTitleCountLongerThan(minLength);
        System.out.printf("There are %d books with longer titles than %d symbols.%n", count, minLength);
    }

    private void P08BookTitlesSearch(Scanner scanner) {
        String input = scanner.nextLine();
        List<Book> books = bookService.selectTitleByAuthorNameStartingWith(input);
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    private void P07BooksSearch(Scanner scanner) {
        String input = scanner.nextLine();
        List<Book> books = bookService.selectByInputContaining(input);
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    private void P06AuthorsSearch(Scanner scanner) {
        String input = scanner.nextLine();
        List<Author> authors = authorService.selectAuthorNameStartingWith(input);
        authors.forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));
    }

    private void P05BooksReleasedBeforeDate(Scanner scanner) {
        String inputYear = scanner.nextLine();
        List<Book> books = bookService.selectBooksByGivenDate(inputYear);
        books.forEach(b -> System.out.printf("%s %s $%.2f%n", b.getTitle(), b.getEditionType(), b.getPrice()));
    }


    private void P04NotReleasedBooks(Scanner scanner) {
        String inputYear = scanner.nextLine();

        List<Book> books = bookService.selectBooksThatNotReleaseInGivenYear(inputYear);
        books.forEach(b -> System.out.println(b.getTitle()));
    }


    private void P03BooksByPrice() {
        List<Book> books = bookService.selectAllBooksWithPriceLowerThan5OrHigherThan40(5, 40);
        books.forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }


    private void P02GoldenBooks() {

        List<Book> books = bookService.selectAllBooksWithGoldenTypeAndLessThan5000Copies(EditionType.GOLD, 5000);
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    private void P01BooksTitlesByAgeRestriction(Scanner scanner) {
        String inputCategory = scanner.nextLine().toUpperCase();
        List<Book> books = bookService.findByAgeRestriction(inputCategory);
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
