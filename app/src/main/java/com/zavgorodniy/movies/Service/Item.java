package com.zavgorodniy.movies.Service;

public class Item {

    private String name;
    private String genre;
    private String date;
    private String rating;
    private String description;
    private String imageId;


    public Item(String name,String genre,String date,String rating,String description,String imageId){
        setName(name);
        setGenre(genre);
        setDate(date);
        setRating(rating);
        setDescription(description);
        setImageId(imageId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
