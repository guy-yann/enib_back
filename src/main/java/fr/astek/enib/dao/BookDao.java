package fr.astek.enib.dao;

import fr.astek.enib.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookDao {
    List<Book> getBooks();

    Book addBook(Book book);

    Optional<Book> getBookById(int idBook);

    Set<Book> getBooksByAuthor(String owner);

    List<Book> getFilteredBooks(String genre, Float minRating, Float maxRating);

    Optional<Book> updateBookRating(int idBook, float rating);

    Set<Book> getBooksBygenre(String genre);
    Set<Book> getBooksFiltered(String author, String genre);
    boolean deleteBook(int idBook);
}
