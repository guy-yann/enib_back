package fr.astek.enib.services;

import fr.astek.enib.dao.BookDao;
import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao bookDao;

    @Override
    public List<Book> getBook() {
        return bookDao.getBooks();
    }

    @Override
    public Book addBook(final Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public Book getBookById(final int idBook) throws BookNotExistsException {
        Optional<Book> bookById = bookDao.getBookById(idBook);
        if (bookById.isEmpty()) {
            throw new BookNotExistsException();
        }
        return bookById.get();
    }

    @Override
    public Set<Book> getBooksByAuthor(String author) {
        return bookDao.getBooksByAuthor(author);
    }

    @Override
    public Boolean deleteBook(int idBook) {
        return bookDao.deleteBook(idBook);
    }

    @Override
    public List<Book> getBooksbyFilter(String title,String author,String releaseDateStr,List<String> genre,String rating,String sales){
        LocalDate releaseDate = null;
        if (releaseDateStr != null) {
            releaseDate = LocalDate.parse(releaseDateStr);
        }
        return bookDao.getBooksByFilter(title, author, releaseDate, genre, rating, sales);
    }

}
