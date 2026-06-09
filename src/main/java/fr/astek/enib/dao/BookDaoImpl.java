package fr.astek.enib.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.astek.enib.model.Book;
import fr.astek.enib.model.BookFilterCriteria;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookDaoImpl implements BookDao {

    private final List<Book> datas = new ArrayList<>();

    public BookDaoImpl() {
        initBookList();
    }

    private void initBookList() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("MM-dd-yyyy"));

        try (InputStream inputStream = getClass().getResourceAsStream("/data/books.json")) {
            if (inputStream == null) {
                System.out.println("Le fichier JSON n'a pas été trouvé dans resources/data/books.json");
                return;
            }
            List<Book> booksFromJson = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            datas.addAll(booksFromJson);
        } catch (IOException e) {
            System.out.println("Échec du chargement des données de livres depuis JSON");
        }
    }

    @Override
    public List<Book> getBooks() {
        return datas;
    }

    @Override
    public Book addBook(Book book) {
        datas.add(book);
        return book;
    }

    @Override
    public Optional<Book> getBookById(final int idBook) {
        return datas.stream().filter(book -> idBook == book.getId()).findFirst();
    }

    @Override
    public Set<Book> getBooksByAuthor(String author) {
        return datas.stream()
                .filter(book -> author.equals(book.getAuthor()))
                .sorted(Comparator.comparingInt(Book::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public List<Book> getFilteredBooks(BookFilterCriteria criteria) {
        return datas.stream()
                .filter(book -> criteria.getAuthor() == null || criteria.getAuthor().isBlank()
                        || criteria.getAuthor().equals(book.getAuthor()))
                .filter(book -> criteria.getTitleKeyword() == null || criteria.getTitleKeyword().isBlank()
                        || book.getTitle().toLowerCase().contains(criteria.getTitleKeyword().toLowerCase()))
                .sorted(Comparator.comparingInt(Book::getId))
                .toList();
    }

    @Override
    public boolean deleteBook(int idBook) {
        return datas.removeIf(book -> book.getId() == idBook);
    }

}
