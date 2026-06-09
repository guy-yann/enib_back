package fr.astek.enib.services;

import fr.astek.enib.model.Book;
import fr.astek.enib.model.BookFilterCriteria;

import java.util.List;

public interface BookFilterService {

    List<Book> getFilteredBooks(BookFilterCriteria criteria);

}
