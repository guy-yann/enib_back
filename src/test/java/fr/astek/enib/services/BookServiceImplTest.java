package fr.astek.enib.services;

import fr.astek.enib.dao.BookDao;
import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBook() {
        List<Book> mockBooks = Arrays.asList(
                new Book(1, "Title1", "Author1", "description", List.of("Genre1"), new Date(), 4.5f, 100),
                new Book(2, "Title2", "Author2", "description", List.of("Genre2"), new Date(), 4.0f, 200)
        );

        when(bookDao.getBooks()).thenReturn(mockBooks);

        List<Book> books = bookService.getBook();

        assertNotNull(books, "The list of books should not be null");
        assertEquals(2, books.size(), "The size of the book list should match");
        verify(bookDao, times(1)).getBooks();
    }

    @Test
    void testAddBookNull() {
        Book addedBook = bookService.addBook(null);

        assertNull(addedBook, "Adding a null book should return null");
    }

    @Test
    void testGetBookById() throws BookNotExistsException {
        Book mockBook = new Book(1, "Title1", "Author1", "description", List.of("Genre1"), new Date(), 4.5f, 100);

        when(bookDao.getBookById(1)).thenReturn(Optional.of(mockBook));

        Book book = bookService.getBookById(1);

        assertNotNull(book, "The book should not be null");
        assertEquals(mockBook, book, "The retrieved book should match the expected book");
        verify(bookDao, times(1)).getBookById(1);
    }

    @Test
    void testGetBookByIdNotExists() {
        when(bookDao.getBookById(999)).thenReturn(Optional.empty());

        assertThrows(BookNotExistsException.class, () -> bookService.getBookById(999),
                "Retrieving a non-existent book should throw BookNotExistsException");
        verify(bookDao, times(1)).getBookById(999);
    }

    @Test
    void testGetBooksByAuthor() {
        Set<Book> mockBooks = new HashSet<>(
                Arrays.asList(
                        new Book(1, "Title1", "Author1", "description", List.of("Genre1"), new Date(), 4.5f, 100),
                        new Book(2, "Title2", "Author1", "description", List.of("Genre2"), new Date(), 4.0f, 200)
                )
        );

        when(bookDao.getBooksByAuthor("Author1")).thenReturn(mockBooks);

        Set<Book> booksByAuthor = bookService.getBooksByAuthor("Author1");

        assertNotNull(booksByAuthor, "The set of books by author should not be null");
        assertEquals(2, booksByAuthor.size(), "The size of the set should match");
        verify(bookDao, times(1)).getBooksByAuthor("Author1");
    }

    @Test
    void testFilterBooks() {
        List<Book> mockBooks = List.of(
                new Book(1, "Forward role", "Author1", "description", List.of("Genre1"), new Date(), 4.5f, 100)
        );

        when(bookDao.filterBooks("Author1", "role")).thenReturn(mockBooks);

        List<Book> filtered = bookService.filterBooks("Author1", "role");

        assertNotNull(filtered, "The filtered list should not be null");
        assertEquals(1, filtered.size(), "The filtered list size should match");
        verify(bookDao, times(1)).filterBooks("Author1", "role");
    }

    @Test
    void testDeleteBook() {
        when(bookDao.deleteBook(1)).thenReturn(true);

        Boolean isDeleted = bookService.deleteBook(1);

        assertTrue(isDeleted, "The book should be deleted successfully");
        verify(bookDao, times(1)).deleteBook(1);
    }

    @Test
    void testDeleteBookNotExists() {
        when(bookDao.deleteBook(999)).thenReturn(false);

        Boolean isDeleted = bookService.deleteBook(999);

        assertFalse(isDeleted, "Deleting a non-existent book should return false");
        verify(bookDao, times(1)).deleteBook(999);
    }

}
