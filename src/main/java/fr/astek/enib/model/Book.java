package fr.astek.enib.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int id;
    private String title;
    private String author;
    private String description;
    private List<String> genre;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date releaseDate;
    private float rating;
    private int sales;
}
