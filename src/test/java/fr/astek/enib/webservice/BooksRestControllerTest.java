package fr.astek.enib.webservice;

import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
import fr.astek.enib.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksRestController.class)
class BooksRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getBookSummaryByIdReturnsDefinedSummaryOutput() throws Exception {
        Book book = new Book(1, "Title1", "Author1", "description", List.of("Genre1"), Date.from(Instant.parse("1973-10-20T00:00:00Z")), 4.5f, 100);
        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(get("/books/1/summary").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.releaseDate").value("10-20-1973"))
                .andExpect(jsonPath("$.rating").value(4.5))
                .andExpect(jsonPath("$.sales").value(100));
    }

    @Test
    void getBookSummaryByIdReturnsNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.getBookById(999)).thenThrow(new BookNotExistsException());

        mockMvc.perform(get("/books/999/summary"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book does not exists in database"));
    }

    @Test
    void getBooksByOwnerReturnsBooksForTheSameAuthor() throws Exception {
        Book first = new Book(1, "Title1", "Carol Gallagher", "description", List.of("Genre1"), Date.from(Instant.parse("1973-10-20T00:00:00Z")), 4.5f, 100);
        Book second = new Book(2, "Title2", "Carol Gallagher", "description", List.of("Genre2"), Date.from(Instant.parse("1974-10-20T00:00:00Z")), 4.0f, 200);
        Set<Book> books = new LinkedHashSet<>(List.of(first, second));
        when(bookService.getBooksByAuthor("Carol Gallagher")).thenReturn(books);

        mockMvc.perform(get("/books/author").param("author", "Carol Gallagher").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Carol Gallagher"))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    void getBooksByTitleKeywordReturnsMatchingBooks() throws Exception {
        Book first = new Book(1, "Forward role", "Author1", "description", List.of("Genre1"), Date.from(Instant.parse("1973-10-20T00:00:00Z")), 4.5f, 100);
        Book second = new Book(2, "Out role one", "Author2", "description", List.of("Genre2"), Date.from(Instant.parse("1974-10-20T00:00:00Z")), 4.0f, 200);
        when(bookService.getBooksByTitleKeyword("role")).thenReturn(List.of(first, second));

        mockMvc.perform(get("/books/title").param("keyword", "role").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Forward role"))
                .andExpect(jsonPath("$[1].title").value("Out role one"));
    }
}


