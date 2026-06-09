package fr.astek.enib.exceptions;

import org.springframework.http.HttpStatus;

public class BookExistsException extends BookException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FOUND;
    }

    @Override
    public String getMessage() {
        return "Book already exists in database";
    }
}

