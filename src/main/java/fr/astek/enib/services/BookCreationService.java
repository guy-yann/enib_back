package fr.astek.enib.services;

import fr.astek.enib.dto.BookCreateRequest;
import fr.astek.enib.dto.BookResponse;
import fr.astek.enib.exceptions.BookCreationException;

public interface BookCreationService {

    BookResponse createBook(BookCreateRequest request) throws BookCreationException;
}

