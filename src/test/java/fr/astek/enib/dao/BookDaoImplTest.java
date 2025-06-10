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
    void testFilterBooks() {
        Book book1 = new Book(1, "The Lord of the Rings", "Tolkien", "description", List.of("Fantasy"), new Date(), 4.9f, 500);
        Book book2 = new Book(2, "The Hobbit", "Tolkien", "description", List.of("Fantasy"), new Date(), 4.8f, 400);
        Book book3 = new Book(3, "1984", "Orwell", "description", List.of("Dystopia"), new Date(), 4.7f, 300);

        bookDao.addBook(book1);
        bookDao.addBook(book2);
        bookDao.addBook(book3);

        List<Book> filteredByAuthor = bookDao.filterBooks("Tolkien", null);
        assertEquals(2, filteredByAuthor.size(), "Should return 2 books by Tolkien");

        List<Book> filteredByTitle = bookDao.filterBooks(null, "hobbit");
        assertEquals(1, filteredByTitle.size(), "Should return 1 book with 'hobbit' in title");

        List<Book> filteredByBoth = bookDao.filterBooks("Tolkien", "rings");
        assertEquals(1, filteredByBoth.size(), "Should return 1 book by Tolkien with 'rings' in title");

        List<Book> noMatch = bookDao.filterBooks("Unknown", "xyz");
        assertTrue(noMatch.isEmpty(), "Should return no books");
    }

}