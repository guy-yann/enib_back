package fr.astek.enib.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.astek.enib.model.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class BookJsonFileService {

    private String booksFilePath = "src/main/resources/data/books.json";

    public BookJsonFileService() {
    }

    public BookJsonFileService(final String booksFilePath) {
        this.booksFilePath = booksFilePath;
    }

    public void saveBooks(final List<Book> books) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("MM-dd-yyyy"));

        Path path = Paths.get(booksFilePath);
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (var writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, books);
        }
    }
}

