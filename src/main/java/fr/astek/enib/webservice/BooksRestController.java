package fr.astek.enib.webservice;

import fr.astek.enib.exceptions.BookException;
import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
import fr.astek.enib.model.RatingUpdateRequest;
import fr.astek.enib.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BooksRestController {

    @Autowired
    BookService bookService;

    @GetMapping()
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<>(bookService.getBook(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody Book Book) {
        return new ResponseEntity<>(bookService.addBook(Book), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int idBook) throws BookNotExistsException {
        return new ResponseEntity<>(bookService.getBookById(idBook), HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<Set<Book>> getBooksByOwner(@RequestParam("author") String author) {
        return new ResponseEntity<>(bookService.getBooksByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Book>> getFilteredBooks(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float maxRating) {
        return new ResponseEntity<>(bookService.getFilteredBooks(genre, minRating, maxRating), HttpStatus.OK);
    }

    @PatchMapping(value = "/id/{id}/rating", consumes = "application/json")
    public ResponseEntity<Book> updateBookRating(
            @PathVariable("id") int idBook,
            @RequestBody RatingUpdateRequest request) throws BookNotExistsException {
        return new ResponseEntity<>(bookService.updateBookRating(idBook, request.getRating()), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable("id") int idBook) {
        return new ResponseEntity<>(bookService.deleteBook(idBook), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<Set<Book>> getBooksFiltered(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre) {

        return ResponseEntity.ok(
                bookService.getBooksFiltered(author, genre)
        );
    }

    @ExceptionHandler(BookException.class)
    private ResponseEntity<String> BookExceptionExists(BookException paee) {
        return new ResponseEntity<>(paee.getMessage(), paee.getStatus());
    }

}
