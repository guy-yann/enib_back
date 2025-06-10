package fr.astek.enib.exceptions;

import org.springframework.http.HttpStatus;


/**
 * Exception levée lorsque l'on tente d'ajouter un livre qui existe déjà dans la base de données.
 * <p>
 * Cette exception hérite de {@link BookException} et retourne le statut HTTP 409 CONFLICT.
 * </p>
 */
public class BookAlreadyExistsException extends BookException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessage() {
        return "Book already exists in database";
    }
}
