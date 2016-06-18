package com.example.philoniare.popularmovies;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class FavoriteMovie extends RealmObject {
    @PrimaryKey
    private int movieId;
    private String movieTitle;
    private String moviePoster;
    private String movieDescription;
    private String movieRating;

    public FavoriteMovie() {}

    public FavoriteMovie(int movieId, String movieTitle, String moviePoster, String movieDescription, String movieRating) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieDescription = movieDescription;
        this.movieRating = movieRating;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMovieId() {
        return this.movieId;
    }

    public void setMovieId(int inpMovieId){
        this.movieId = inpMovieId;
    }

}
