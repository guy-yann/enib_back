package fr.astek.enib.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.astek.enib.model.Book;
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
    public List<Book> getFilteredBooks(String genre, Float minRating, Float maxRating) {
        return datas.stream()
                .filter(book -> genre == null || book.getGenre().contains(genre))
                .filter(book -> minRating == null || book.getRating() >= minRating)
                .filter(book -> maxRating == null || book.getRating() <= maxRating)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> updateBookRating(int idBook, float rating) {
        return datas.stream()
                .filter(book -> book.getId() == idBook)
                .findFirst()
                .map(book -> {
                    book.setRating(rating);
                    return book;
                });
    }

    @Override
    public Set<Book> getBooksBygenre(String genre) {
        return datas.stream().filter(book -> book.getGenre().indexOf(genre)!=-1).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    @Override
    public boolean deleteBook(int id){
        return datas.removeIf(book -> id==book.getId());

    }
    @Override
    public Set<Book> getBooksFiltered(String author, String genre) {
        return datas.stream()
                .filter(book ->
                        (author == null || book.getAuthor().equalsIgnoreCase(author))
                                && (genre == null || book.getGenre().indexOf(genre)!=-1)
                )
                .collect(Collectors.toSet());
    }
    public List<Book> getDatas() {
        return datas;
    }
}
