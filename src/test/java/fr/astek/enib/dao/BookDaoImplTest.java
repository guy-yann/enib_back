package fr.astek.enib.dao;

import fr.astek.enib.model.Book;
import fr.astek.enib.model.BookFilterCriteria;
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

    @Test
    void testGetFilteredBooksByAuthor() {
        List<Book> filteredBooks = bookDao.getFilteredBooks(new BookFilterCriteria("John Hamilton", null));

        assertNotNull(filteredBooks, "The filtered list should not be null");
        assertFalse(filteredBooks.isEmpty(), "The filtered list should not be empty");
        assertTrue(filteredBooks.stream().allMatch(book -> "John Hamilton".equals(book.getAuthor())),
                "All books should match the author filter");
    }

    @Test
    void testGetFilteredBooksByTitleKeyword() {
        List<Book> filteredBooks = bookDao.getFilteredBooks(new BookFilterCriteria(null, "language"));

        assertNotNull(filteredBooks, "The filtered list should not be null");
        assertFalse(filteredBooks.isEmpty(), "The filtered list should not be empty");
        assertTrue(filteredBooks.stream().allMatch(book -> book.getTitle().toLowerCase().contains("language")),
                "All books should contain the title keyword");
    }
}