package fr.astek.enib.dao;

import fr.astek.enib.exceptions.BookExistsException;
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
    void testAddBook() throws BookExistsException {
        Book newBook = new Book(999, "New Title", "New Author", "description", new ArrayList<>(), new Date(), 4.5f, 0);
        bookDao.addBook(newBook);

        assertTrue(bookDao.getBooks().contains(newBook), "The new book should be added to the list");
    }

    @Test
    void testAddBookExisting() throws BookExistsException {
        ArrayList<String> genres = new ArrayList<String>();
        genres.add("Biography");
        genres.add("Science Fiction");
        genres.add("Non-Fiction");
        Date date = new Date(1973,10,20);


        Book newBook = new Book(1, "Forward role", "Carol Gallagher", "Suffer give itself him score customer official.", genres, date, 2.9f, 84757);
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
    void testGetFilteredBooksByGenre() {
        List<Book> allBooks = bookDao.getBooks();
        String targetGenre = allBooks.getFirst().getGenre().getFirst();

        List<Book> filtered = bookDao.getFilteredBooks(targetGenre, null, null);

        assertNotNull(filtered, "The filtered list should not be null");
        assertFalse(filtered.isEmpty(), "The filtered list should not be empty");
        assertTrue(filtered.stream().allMatch(b -> b.getGenre().contains(targetGenre)),
                "All books should contain the requested genre");
    }

    @Test
    void testGetFilteredBooksByMinRating() {
        float minRating = 3.0f;
        List<Book> filtered = bookDao.getFilteredBooks(null, minRating, null);

        assertNotNull(filtered, "The filtered list should not be null");
        assertTrue(filtered.stream().allMatch(b -> b.getRating() >= minRating),
                "All books should have a rating >= " + minRating);
    }

    @Test
    void testGetFilteredBooksByMaxRating() {
        float maxRating = 4.0f;
        List<Book> filtered = bookDao.getFilteredBooks(null, null, maxRating);

        assertNotNull(filtered, "The filtered list should not be null");
        assertTrue(filtered.stream().allMatch(b -> b.getRating() <= maxRating),
                "All books should have a rating <= " + maxRating);
    }

    @Test
    void testGetFilteredBooksNoFilters() {
        List<Book> allBooks = bookDao.getBooks();
        List<Book> filtered = bookDao.getFilteredBooks(null, null, null);

        assertEquals(allBooks.size(), filtered.size(), "With no filters, all books should be returned");
    }

    @Test
    void testUpdateBookRating() {
        Book existingBook = bookDao.getBooks().getFirst();
        float newRating = 4.9f;

        Optional<Book> updated = bookDao.updateBookRating(existingBook.getId(), newRating);

        assertTrue(updated.isPresent(), "The updated book should be present");
        assertEquals(newRating, updated.get().getRating(), 0.001f, "The rating should be updated");
    }

    @Test
    void testUpdateBookRatingNotFound() {
        Optional<Book> updated = bookDao.updateBookRating(9999, 3.0f);

        assertFalse(updated.isPresent(), "Updating a non-existent book should return empty");
    }
}