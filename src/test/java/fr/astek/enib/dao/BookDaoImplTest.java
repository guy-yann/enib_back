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

        Book newBook = new Book(999, "NewTitle", "NewAuthor", "description", new ArrayList<>(), new Date(), 4.5f, 0);
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
    void testGetBooksByFilter() {
        Book book1 = new Book(1, "FilterTitle1", "FilterAuthor", "desc", List.of("Genre1"), new Date(), 4.0f, 100);
        Book book2 = new Book(2, "FilterTitle2", "FilterAuthor", "desc", List.of("Genre2"), new Date(), 4.5f, 200);
        Book book3 = new Book(3, "AnotherTitle", "AnotherAuthor", "desc", List.of("Genre3"), new Date(), 3.5f, 300);
        bookDao.addBook(book1);
        bookDao.addBook(book2);
        bookDao.addBook(book3);
        List<Book> filteredBooks = new ArrayList<>(bookDao.getBooksByFilter(null, "FilterAuthor", null, null, null, null));
        assertNotNull(filteredBooks, "Filtered books list should not be null");
        assertEquals(2, filteredBooks.size(), "There should be 2 books by 'FilterAuthor'");
        assertTrue(filteredBooks.contains(book1), "Filtered list should contain book1");
        assertTrue(filteredBooks.contains(book2), "Filtered list should contain book2");
        assertFalse(filteredBooks.contains(book3), "Filtered list should not contain book3");
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