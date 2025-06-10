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

    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public List<String> getGenre(){
        return genre;
    }

    public void setGenre(List<String> genre){
        this.genre = genre;
    }

    public Date getReleaseDate(){
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate){
        this.releaseDate = releaseDate;
    }

    public float getRating(){
        return rating;
    }

    public void setRating(Float rating){
        this.rating = rating;
    }

    public int getSales(){
        return sales;
    }

    public void setSales(int sales){
        this.sales = sales;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
