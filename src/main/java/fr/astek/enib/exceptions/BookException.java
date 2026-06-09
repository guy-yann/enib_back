package fr.astek.enib.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BookException extends Exception {

    public BookException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
    public abstract String getMessage();
}
