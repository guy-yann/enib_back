package fr.astek.enib.webservice;

import fr.astek.enib.exceptions.BookAlreadyExistsException;
import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
import fr.astek.enib.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BooksRestControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BooksRestController booksRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookService.getBook()).thenReturn(Arrays.asList(book1, book2));

        ResponseEntity<List<Book>> response = booksRestController.listBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void addBook() throws BookAlreadyExistsException {
        Book book = new Book();
        when(bookService.getBook()).thenReturn(Arrays.asList());
        when(bookService.addBook(book)).thenReturn(book);

        ResponseEntity<Book> response = booksRestController.addBook(book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void addBookAlreadyExists() {
        Book book = new Book();
        when(bookService.getBook()).thenReturn(Arrays.asList(book));

        assertThrows(BookAlreadyExistsException.class, () -> {
            booksRestController.addBook(book);
        });
    }

    @Test
    void getBookById() throws BookNotExistsException {
        Book book = new Book();
        when(bookService.getBookById(1)).thenReturn(book);

        ResponseEntity<Book> response = booksRestController.getBookById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void getBooksByOwner() {
        // Create and initialize Book objects properly
        Book book1 = new Book();
        book1.setId(1);
        book1.setAuthor("author");

        Book book2 = new Book();
        book2.setId(2);
        book2.setAuthor("author");

        Set<Book> books = Set.of(book1, book2);

        // Mock the service call
        when(bookService.getBooksByAuthor("author")).thenReturn(books);

        // Call the controller method
        ResponseEntity<Set<Book>> response = booksRestController.getBooksByOwner("author");

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }




    @Test
    void deleteBook() {
        when(bookService.deleteBook(1)).thenReturn(true);

        ResponseEntity<Boolean> response = booksRestController.deleteBook(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }
}
