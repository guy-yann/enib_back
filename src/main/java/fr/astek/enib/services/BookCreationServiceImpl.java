package fr.astek.enib.services;

import fr.astek.enib.dao.BookDao;
import fr.astek.enib.dto.BookCreateRequest;
import fr.astek.enib.dto.BookResponse;
import fr.astek.enib.exceptions.BookCreationException;
import fr.astek.enib.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookCreationServiceImpl implements BookCreationService {

    private static final String DATE_PATTERN = "MM-dd-yyyy";

    private final BookDao bookDao;
    private final BookJsonFileService bookJsonFileService;

    @Autowired
    public BookCreationServiceImpl(final BookDao bookDao, final BookJsonFileService bookJsonFileService) {
        this.bookDao = bookDao;
        this.bookJsonFileService = bookJsonFileService;
    }

    @Override
    public BookResponse createBook(final BookCreateRequest request) throws BookCreationException {
        Book book = toBook(request);
        List<Book> updatedBooks = new ArrayList<>(bookDao.getBooks());
        updatedBooks.add(book);

        try {
            bookJsonFileService.saveBooks(updatedBooks);
            bookDao.addBook(book);
            return toResponse(book);
        } catch (IOException exception) {
            throw new BookCreationException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to persist the new book");
        }
    }

    private Book toBook(final BookCreateRequest request) throws BookCreationException {
        try {
            int nextId = bookDao.getBooks().stream()
                    .mapToInt(Book::getId)
                    .max()
                    .orElse(0) + 1;
            return new Book(
                    nextId,
                    request.getTitle(),
                    request.getAuthor(),
                    request.getDescription(),
                    request.getGenre(),
                    parseDate(request.getReleaseDate()),
                    request.getRating(),
                    request.getSales()
            );
        } catch (ParseException exception) {
            throw new BookCreationException(HttpStatus.BAD_REQUEST, "Invalid releaseDate format. Expected MM-dd-yyyy");
        }
    }

    private Date parseDate(final String releaseDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        dateFormat.setLenient(false);
        return dateFormat.parse(releaseDate);
    }

    private BookResponse toResponse(final Book book) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getGenre(),
                dateFormat.format(book.getReleaseDate()),
                book.getRating(),
                book.getSales()
        );
    }
}

