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
    void testFilterBooksByAuthor() {
        Book existingBook = bookDao.getBooks().getFirst();
        List<Book> filtered = bookDao.filterBooks(existingBook.getAuthor(), null);

        assertFalse(filtered.isEmpty(), "Filtering by an existing author should return at least one book");
        assertTrue(filtered.stream()
                        .allMatch(book -> book.getAuthor().toLowerCase()
                                .contains(existingBook.getAuthor().toLowerCase())),
                "Every returned book should match the requested author");
    }

    @Test
    void testFilterBooksByTitleKeyword() {
        Book existingBook = bookDao.getBooks().getFirst();
        String keyword = existingBook.getTitle().split(" ")[0];
        List<Book> filtered = bookDao.filterBooks(null, keyword);

        assertFalse(filtered.isEmpty(), "Filtering by a known keyword should return at least one book");
        assertTrue(filtered.contains(existingBook), "The book whose title contains the keyword should be returned");
        assertTrue(filtered.stream()
                        .allMatch(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase())),
                "Every returned book title should contain the keyword");
    }

    @Test
    void testFilterBooksNoCriteriaReturnsAll() {
        List<Book> filtered = bookDao.filterBooks(null, null);
        assertEquals(bookDao.getBooks().size(), filtered.size(),
                "Filtering with no criteria should return all books");
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