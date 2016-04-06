package com.example.philoniare.popularmovies.MovieDBAPI;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDBClient {
    String API_KEY = "392c01ca59dd8e41800fed31485b99a9";
    @GET("{criteria}?api_key=" + API_KEY)
    Call<MoviesResult> fetchMovies(@Path("criteria") String criteria);

    @GET("{id}/videos?api_key=" + API_KEY)
    Call<VideoResult> fetchVideos(@Path("id") int id);

    @GET("{id}/reviews?api_key=" + API_KEY)
    Call<ReviewResult> fetchReviews(@Path("id") int id);
}