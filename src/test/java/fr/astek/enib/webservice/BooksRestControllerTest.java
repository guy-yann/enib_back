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
}


