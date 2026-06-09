package fr.astek.enib.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.astek.enib.model.Book;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        Path sourceFile = Paths.get("src/main/resources/data/books.json");

        try (InputStream inputStream = Files.exists(sourceFile)
                ? Files.newInputStream(sourceFile)
                : getClass().getResourceAsStream("/data/books.json")) {
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
    public boolean deleteBook(int idBook) {
        return datas.removeIf(book -> book.getId() == idBook);
    }

}
