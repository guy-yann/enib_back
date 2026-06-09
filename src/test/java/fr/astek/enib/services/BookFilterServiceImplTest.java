package fr.astek.enib.services;

import fr.astek.enib.dao.BookDao;
import fr.astek.enib.model.Book;
import fr.astek.enib.model.BookFilterCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookFilterServiceImplTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookFilterServiceImpl bookFilterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilteredBooksByAuthor() {
        BookFilterCriteria criteria = new BookFilterCriteria("John Hamilton", null);
        List<Book> mockBooks = List.of(
                new Book(15, "Easy organization assume", "John Hamilton", "description", List.of("Fiction"), new Date(), 3.8f, 19619)
        );

        when(bookDao.getFilteredBooks(criteria)).thenReturn(mockBooks);

        List<Book> filteredBooks = bookFilterService.getFilteredBooks(criteria);

        assertNotNull(filteredBooks, "The filtered list should not be null");
        assertEquals(1, filteredBooks.size(), "The filtered list size should match");
        verify(bookDao, times(1)).getFilteredBooks(criteria);
    }

    @Test
    void testGetFilteredBooksByTitleKeyword() {
        BookFilterCriteria criteria = new BookFilterCriteria(null, "language");
        List<Book> mockBooks = List.of(
                new Book(4, "Language turn", "Rebecca King", "description", List.of("Mystery"), new Date(), 4.1f, 12345)
        );

        when(bookDao.getFilteredBooks(criteria)).thenReturn(mockBooks);

        List<Book> filteredBooks = bookFilterService.getFilteredBooks(criteria);

        assertNotNull(filteredBooks, "The filtered list should not be null");
        assertEquals(1, filteredBooks.size(), "The filtered list size should match");
        verify(bookDao, times(1)).getFilteredBooks(criteria);
    }

}
