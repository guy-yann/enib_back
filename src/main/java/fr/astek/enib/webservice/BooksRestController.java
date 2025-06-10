package fr.astek.enib.webservice;

import fr.astek.enib.exceptions.BookException;
import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
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

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable("id") int idBook) {
        return new ResponseEntity<>(bookService.deleteBook(idBook), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Book>> filteredBooks(@RequestParam(value = "title", required = false) String title,
                                                    @RequestParam(value = "author", required = false) String author,
                                                    @RequestParam(value = "genre", required = false) List<String> genre,
                                                    @RequestParam(value = "releaseDate" , required = false) String releaseDate,
                                                    @RequestParam(value = "rating", required = false) String rating,
                                                    @RequestParam(value = "sales", required = false) String sales) {

        return new ResponseEntity<>(bookService.getBooksbyFilter(title, author, releaseDate, genre, rating, sales), HttpStatus.OK);
    }

    @ExceptionHandler(BookException.class)
    private ResponseEntity<String> BookExceptionExists(BookException paee) {
        return new ResponseEntity<>(paee.getMessage(), paee.getStatus());
    }
}
