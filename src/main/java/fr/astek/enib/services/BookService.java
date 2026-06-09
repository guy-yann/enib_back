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

    List<Book> getFilteredBooks(String genre, Float minRating, Float maxRating);

    Book updateBookRating(int idBook, float rating) throws BookNotExistsException;

    Boolean deleteBook(int idBook);

    Set<Book> getBooksFiltered(String author, String genre);
}
