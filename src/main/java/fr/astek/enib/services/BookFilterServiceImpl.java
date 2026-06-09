package fr.astek.enib.services;

import fr.astek.enib.dao.BookDao;
import fr.astek.enib.model.Book;
import fr.astek.enib.model.BookFilterCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookFilterServiceImpl implements BookFilterService {

    @Autowired
    BookDao bookDao;

    @Override
    public List<Book> getFilteredBooks(BookFilterCriteria criteria) {
        return bookDao.getFilteredBooks(criteria);
    }

}
