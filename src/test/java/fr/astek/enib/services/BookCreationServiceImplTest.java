package fr.astek.enib.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.astek.enib.dao.BookDao;
import fr.astek.enib.dto.BookCreateRequest;
import fr.astek.enib.dto.BookResponse;
import fr.astek.enib.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookCreationServiceImplTest {

    @TempDir
    Path tempDir;

    @Mock
    private BookDao bookDao;

    private BookCreationServiceImpl bookCreationService;

    @BeforeEach
    void setUp() {
        bookCreationService = new BookCreationServiceImpl(bookDao, new BookJsonFileService(tempDir.resolve("books.json").toString()));
    }

    @Test
    void createBookShouldPersistBookAndReturnDto() throws Exception {
        List<Book> existingBooks = new ArrayList<>(List.of(
                new Book(1, "Existing 1", "Author 1", "description", List.of("Fantasy"), new Date(), 4.2f, 100),
                new Book(5, "Existing 5", "Author 5", "description", List.of("Mystery"), new Date(), 3.8f, 200)
        ));
        when(bookDao.getBooks()).thenReturn(existingBooks);
        when(bookDao.addBook(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookCreateRequest request = new BookCreateRequest(
                "New Book",
                "New Author",
                "New description",
                List.of("Fantasy", "Thriller"),
                "10-20-2024",
                4.5f,
                321
        );

        BookResponse response = bookCreationService.createBook(request);

        assertNotNull(response);
        assertEquals(6, response.getId());
        assertEquals("New Book", response.getTitle());
        assertEquals("10-20-2024", response.getReleaseDate());
        assertEquals(4.5f, response.getRating());
        assertEquals(321, response.getSales());
        verify(bookDao).addBook(any(Book.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("MM-dd-yyyy"));
        List<Book> savedBooks = objectMapper.readValue(tempDir.resolve("books.json").toFile(), new TypeReference<>() {
        });

        assertEquals(3, savedBooks.size());
        assertEquals(6, savedBooks.get(2).getId());
        assertEquals("New Author", savedBooks.get(2).getAuthor());
    }
}


