package tqs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static tqs.Utils.mapToObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BookStepDefinitions {

    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @ParameterType(".*")
    public LocalDate myDateFormat(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        return LocalDate.parse(input, formatter);
    }

    @Given("the following books:")
    public void addBooks(DataTable bookTable) {
        List<Book> books = bookTable.asMaps(String.class, String.class)
                .stream()
                .map(bookMap -> mapToObject(bookMap, Book.class))
                .collect(Collectors.toList());
        library.addBooks(books);
    }

    @Given("a/another book with the title {string}, written by {string}, published in {myDateFormat}")
    public void addBook(final String title, final String author, final LocalDate published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("the customer removes the book titled {string}")
    public void removeBook(final String title) {
        library.removeBooks(title);
    }

    @When("the customer searches all books")
    public void setSearchParametersAllBooks()
    {
        result = library.findAllBooks();
    }

    @When("the customer searches for books published between {int} and {int}")
    public void setSearchParameters(int from, int to) {
        LocalDate fromDate = LocalDate.of(from, 1, 1);
        LocalDate toDate = LocalDate.of(to, 12, 31);
        result = library.findBooks(fromDate, toDate);
    }

    @Then("{int} book(s) should have been found")
    public void verifyAmountOfBooksFound(int booksFound) {
        assertEquals(booksFound, result.size());
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(int position, String title) {
        assertEquals(title, result.get(position - 1).getTitle());
    }
}
