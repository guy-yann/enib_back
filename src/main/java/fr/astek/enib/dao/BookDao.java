package fr.astek.enib.dao;

import fr.astek.enib.model.Book;
import fr.astek.enib.model.BookFilterCriteria;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookDao {
    List<Book> getBooks();

    Book addBook(Book book);

    Optional<Book> getBookById(int idBook);

    Set<Book> getBooksByAuthor(String owner);

    List<Book> getFilteredBooks(BookFilterCriteria criteria);

    boolean deleteBook(int idBook);
}
