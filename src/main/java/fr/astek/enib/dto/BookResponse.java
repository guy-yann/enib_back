package fr.astek.enib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private int id;
    private String title;
    private String author;
    private String description;
    private List<String> genre;
    private String releaseDate;
    private float rating;
    private int sales;
}

