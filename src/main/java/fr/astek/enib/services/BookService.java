package fr.astek.enib.services;

import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getBook();

    Book addBook(Book book);

    Book getBookById(int idBook) throws BookNotExistsException;

    Set<Book> getBooksByAuthor(String author);

    Boolean deleteBook(int idBook);

    Book updateBookById(Book myUpdatedDataBook) throws BookNotExistsException;
}
