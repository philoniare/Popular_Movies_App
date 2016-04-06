package com.example.philoniare.popularmovies.RealmDB;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Movie extends RealmObject {
    private int id;
    private boolean fav;
    private String title;
    private String poster;
    private String description;
    private Double rating;
    private RealmList<Review> reviews;
    private RealmList<Trailer> trailers;
    private RealmList<Movie> allMovies;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public boolean getFav() {return fav;}
    public void setFav(boolean fav) {this.fav = fav;}

    public String getPoster() {return poster;}
    public void setPoster(String poster) {this.poster = poster;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Double getRating() {return rating;}
    public void setRating(Double rating) {this.rating = rating;}

    public RealmList<Review> getReviews() {return reviews;}
    public void setReviews(RealmList<Review> reviews) {this.reviews = reviews;}

    public RealmList<Trailer> getTrailers() {return trailers;}
    public void setTrailers(RealmList<Trailer> trailers) {this.trailers = trailers;}

    public RealmList<Movie> getAllMovies() {return allMovies;}
    public void setAllMovies(RealmList<Movie> allMovies) {this.allMovies = allMovies;}
}
