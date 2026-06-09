package fr.astek.enib.exceptions;

import org.springframework.http.HttpStatus;

public class BookNotExistsException extends BookException {


    public BookNotExistsException() {
        super("Book does not exist in database");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "Book does not exists in database";
    }
}
