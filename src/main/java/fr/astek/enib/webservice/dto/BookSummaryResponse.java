package fr.astek.enib.webservice.dto;

import fr.astek.enib.model.Book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public record BookSummaryResponse(
        int id,
        String title,
        String author,
        String releaseDate,
        float rating,
        int sales
) {
    public static BookSummaryResponse from(Book book) {
        return new BookSummaryResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                formatDate(book.getReleaseDate()),
                book.getRating(),
                book.getSales()
        );
    }

    private static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }
}


