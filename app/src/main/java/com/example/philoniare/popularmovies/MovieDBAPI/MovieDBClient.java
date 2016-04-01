package com.example.philoniare.popularmovies.MovieDBAPI;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by philoniare on 3/11/16.
 */
public interface MovieDBClient {
    @GET("{criteria}?api_key=392c01ca59dd8e41800fed31485b99a9")
    Call<APIResult> fetchMovies(@Path("criteria") String criteria);
}