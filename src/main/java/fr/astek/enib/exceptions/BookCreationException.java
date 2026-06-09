package fr.astek.enib.exceptions;

import org.springframework.http.HttpStatus;

public class BookCreationException extends BookException {

    private final HttpStatus status;
    private final String message;

    public BookCreationException(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

