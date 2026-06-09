package fr.astek.enib.webservice;

import fr.astek.enib.dto.BookCreateRequest;
import fr.astek.enib.dto.BookResponse;
import fr.astek.enib.exceptions.BookCreationException;
import fr.astek.enib.exceptions.BookException;
import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
import fr.astek.enib.services.BookCreationService;
import fr.astek.enib.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BooksRestController {

    @Autowired
    BookService bookService;

    @Autowired
    BookCreationService bookCreationService;

    @GetMapping()
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<>(bookService.getBook(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookCreateRequest request) throws BookCreationException {
        return new ResponseEntity<>(bookCreationService.createBook(request), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int idBook) throws BookNotExistsException {
        return new ResponseEntity<>(bookService.getBookById(idBook), HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<Set<Book>> getBooksByOwner(@RequestParam("author") String author) {
        return new ResponseEntity<>(bookService.getBooksByAuthor(author), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable("id") int idBook) {
        return new ResponseEntity<>(bookService.deleteBook(idBook), HttpStatus.OK);
    }

    @ExceptionHandler(BookException.class)
    private ResponseEntity<String> BookExceptionExists(BookException paee) {
        return new ResponseEntity<>(paee.getMessage(), paee.getStatus());
    }
}
