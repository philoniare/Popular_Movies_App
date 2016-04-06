package com.example.philoniare.popularmovies.MovieDBAPI;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDBServiceGenerator {
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder retroBuilder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = retroBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
