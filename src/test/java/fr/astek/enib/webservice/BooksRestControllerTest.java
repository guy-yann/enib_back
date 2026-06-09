package fr.astek.enib.webservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.astek.enib.dto.BookCreateRequest;
import fr.astek.enib.dto.BookResponse;
import fr.astek.enib.services.BookCreationService;
import fr.astek.enib.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksRestController.class)
class BooksRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    @SuppressWarnings("unused")
    private BookService bookService;

    @MockBean
    private BookCreationService bookCreationService;

    @Test
    void addBookShouldReturnCreatedBookDto() throws Exception {
        BookCreateRequest request = new BookCreateRequest(
                "New Book",
                "New Author",
                "New description",
                List.of("Fantasy", "Thriller"),
                "10-20-2024",
                4.5f,
                321
        );
        BookResponse response = new BookResponse(
                42,
                "New Book",
                "New Author",
                "New description",
                List.of("Fantasy", "Thriller"),
                "10-20-2024",
                4.5f,
                321
        );

        when(bookCreationService.createBook(any(BookCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.author").value("New Author"))
                .andExpect(jsonPath("$.releaseDate").value("10-20-2024"))
                .andExpect(jsonPath("$.genre[0]").value("Fantasy"))
                .andExpect(jsonPath("$.genre[1]").value("Thriller"));
    }
}


