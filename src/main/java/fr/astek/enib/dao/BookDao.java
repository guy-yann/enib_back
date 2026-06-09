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

    boolean deleteBook(int idBook);

    Set<Book> getBooksByFilter(String owner, List<String> genre, float rating, int sales);

}
