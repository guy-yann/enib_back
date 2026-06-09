package fr.astek.enib.dao;

import fr.astek.enib.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoImplTest {

    private BookDaoImpl bookDao;

    @BeforeEach
    void setUp() {
        bookDao = new BookDaoImpl();
    }

    @Test
    void testGetBooks() {
        List<Book> books = bookDao.getBooks();
        assertNotNull(books, "The list of books should not be null");
        assertFalse(books.isEmpty(), "The list of books should not be empty");
    }

    @Test
    void testAddBook() {
        Book newBook = new Book(999, "New Title", "New Author", "description", new ArrayList<>(), new Date(), 4.5f, 0);
        bookDao.addBook(newBook);

        assertTrue(bookDao.getBooks().contains(newBook), "The new book should be added to the list");
    }

    @Test
    void testGetBookById() {
        Book existingBook = bookDao.getBooks().getFirst();
        Optional<Book> retrievedBook = bookDao.getBookById(existingBook.getId());

        assertTrue(retrievedBook.isPresent(), "The book with the given ID should be found");
        assertEquals(existingBook, retrievedBook.get(), "The retrieved book should match the expected one");
    }

    @Test
    void testGetBooksByAuthor() {
        Book existingBook = bookDao.getBooks().getFirst();
        Set<Book> booksByAuthor = bookDao.getBooksByAuthor(existingBook.getAuthor());

        assertNotNull(booksByAuthor, "The set of books by author should not be null");
        assertFalse(booksByAuthor.isEmpty(), "The set of books by author should not be empty");
        assertTrue(booksByAuthor.contains(existingBook), "The set should contain the book by the specified author");
    }

    @Test
    void testGetBooksByFilter_AllCriteriaMatch() {
        // Arrange
        Book existingBook = bookDao.getBooks().getFirst();
        String testAuthor = "J.K. Rowling";
        List<String> testGenres = List.of("Fantasy");
        float testRating = 4.5f;
        int testSales = 1000000;

        Set<Book> filteredBooks = bookDao.getBooksByFilter(testAuthor, testGenres, testRating, testSales);

        assertNotNull(filteredBooks, "The set of filtered books should not be null");
        assertFalse(filteredBooks.isEmpty(), "The set of filtered books should not be empty");

        for (Book book : filteredBooks) {
            assertEquals(testAuthor, book.getAuthor(), "All books should be by the specified author");
            assertTrue(testGenres.contains(book.getGenre()), "All books should have one of the specified genres");
            assertTrue(book.getRating() >= testRating, "All books should have a rating >= " + testRating);
            assertTrue(book.getSales() >= testSales, "All books should have sales >= " + testSales);
        }
    }

    @Test
    void testGetBooksByFilter_NoMatch() {
        Book existingBook = bookDao.getBooks().getFirst();
        String testAuthor = "Unknown Author";
        List<String> testGenres = List.of("Horror");
        float testRating = 5.0f;
        int testSales = 10000000;

        Set<Book> filteredBooks = bookDao.getBooksByFilter(testAuthor, testGenres, testRating, testSales);

        assertNotNull(filteredBooks, "The set of filtered books should not be null");
        assertTrue(filteredBooks.isEmpty(), "The set should be empty if no books match the criteria");
    }

    @Test
    void testGetBooksByFilter_NullOrEmptyParameters() {
        Book existingBook = bookDao.getBooks().getFirst();

        Set<Book> filteredBooks = bookDao.getBooksByFilter(null, null, 0, 0);

        assertNotNull(filteredBooks, "The set of filtered books should not be null");
        assertFalse(filteredBooks.isEmpty(), "The set should contain all books if all filters are null or empty");
    }

    @Test
    void testDeleteBook() {
        Book existingBook = bookDao.getBooks().getFirst();
        boolean isDeleted = bookDao.deleteBook(existingBook.getId());

        assertTrue(isDeleted, "The book should be deleted successfully");
        assertFalse(bookDao.getBooks().contains(existingBook), "The deleted book should no longer be in the list");
    }

    @Test
    void testDeleteBookNonExistent() {
        boolean isDeleted = bookDao.deleteBook(9999);
        assertFalse(isDeleted, "Deleting a non-existent book should return false");
    }
}