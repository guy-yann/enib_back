package fr.astek.enib.model;

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
    private Date releaseDate;
    private float rating;
    private int sales;

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor(){return author; }
    public List<String> getGenre(){return genre;}
    public Date getReleaseDate(){
        return releaseDate;
    }
    public float getRating(){
        return rating;
    }
    public int getSales(){
        return sales;
    }
    public String getDescription() {return description;}
}
