package fr.astek.enib.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.astek.enib.exceptions.BookNotExistsException;
import fr.astek.enib.model.Book;
import org.springframework.stereotype.Component;

import java.io.File;
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
    public boolean deleteBook(int idBook) {
        return datas.removeIf(book -> book.getId() == idBook);
    }

    @Override
    public Book updateBook(Book book) throws BookNotExistsException {

        Book existingBook = datas.stream()
                .filter(b -> b.getId() == book.getId())
                .findFirst()
                .orElseThrow(BookNotExistsException::new);
        if (!Objects.equals(existingBook.getTitle(), book.getTitle())) {
            existingBook.setTitle(book.getTitle());
        }

        if (!Objects.equals(existingBook.getAuthor(), book.getAuthor())) {
            existingBook.setAuthor(book.getAuthor());
        }

        if (!Objects.equals(existingBook.getDescription(), book.getDescription())) {
            existingBook.setDescription(book.getDescription());
        }
        if (!Objects.equals(existingBook.getGenre(), book.getGenre())) {
            existingBook.setGenre(book.getGenre());
        }
        if (!Objects.equals(existingBook.getReleaseDate(), book.getReleaseDate())) {
            existingBook.setReleaseDate(book.getReleaseDate());
        }
        if (existingBook.getRating() != book.getRating()) {
            existingBook.setRating(book.getRating());
        }

        if (existingBook.getSales() != book.getSales()) {
            existingBook.setSales(book.getSales());
        }
        saveToJson();
        return existingBook;

    }

    private void saveToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
            File file = new File("src/main/resources/data/books.json");
            file.getParentFile().mkdirs();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(file, datas);

        } catch (IOException e) {
            throw new RuntimeException("Error saving JSON file", e);
        }
    }


}
