package com.example.philoniare.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.philoniare.popularmovies.MovieDBAPI.Movie;
import com.example.philoniare.popularmovies.MovieDBAPI.MovieDBClient;
import com.example.philoniare.popularmovies.MovieDBAPI.MovieDBServiceGenerator;
import com.example.philoniare.popularmovies.MovieDBAPI.MoviesResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GridLayoutManager layoutManager;
    private Boolean isFabOpen = false;
    private Animation fab_open,fab_close;
    public static List<Movie> movies;
    Realm realm;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.fab_rating) FloatingActionButton fab_rating;
    @Bind(R.id.fab_popular) FloatingActionButton fab_popular;
    @Bind(R.id.fab_favorites) FloatingActionButton fab_favorites;
    @Bind(R.id.text_view_favorites) TextView text_view_favorites;
    @Bind(R.id.text_view_popular) TextView text_view_popular;
    @Bind(R.id.text_view_rating) TextView text_view_rating;

    @Bind(R.id.recyclerView) RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();

        // Fab button used to select sorting method
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        fab.setOnClickListener(this);
        fab_rating.setOnClickListener(this);
        fab_popular.setOnClickListener(this);
        fab_favorites.setOnClickListener(this);

        movies = new ArrayList<> ();
        layoutManager = new GridLayoutManager(MainActivity.this, 2);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(layoutManager);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, movies);
        rView.setAdapter(rcAdapter);

        // Fetch the most popular movies and populate the view
        fetchMovies("popular");
    }

    private void fetchMovies(String criteria){
        MovieDBClient client = MovieDBServiceGenerator.createService(MovieDBClient.class);

        Call<MoviesResult> call = client.fetchMovies(criteria);
        call.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                if(response.isSuccess()) {
                    MoviesResult res = response.body();
                    for(Movie movie : res.getResults()) {
                        movies.add(movie);
                    }
                    rView.getAdapter().notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                    Log.e("Network Error: ", errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void animateFab() {
        if (isFabOpen) {
            closeFab();
        } else {
            fab_rating.startAnimation(fab_open);
            fab_popular.startAnimation(fab_open);
            fab_favorites.startAnimation(fab_open);
            text_view_favorites.startAnimation(fab_open);
            text_view_popular.startAnimation(fab_open);
            text_view_rating.startAnimation(fab_open);
            fab_rating.setClickable(true);
            fab_popular.setClickable(true);
            fab_favorites.setClickable(true);
            isFabOpen = true;
        }
    }

    private void closeFab() {
        fab_rating.startAnimation(fab_close);
        fab_popular.startAnimation(fab_close);
        fab_favorites.startAnimation(fab_close);
        text_view_favorites.startAnimation(fab_close);
        text_view_popular.startAnimation(fab_close);
        text_view_rating.startAnimation(fab_close);
        fab_rating.setClickable(false);
        fab_popular.setClickable(false);
        fab_favorites.setClickable(false);
        isFabOpen = false;
    }

    private void updateMovies(String criteria) {
        movies.clear();
        fetchMovies(criteria);
        rView.getAdapter().notifyDataSetChanged();
    }

    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.fab:
                animateFab();
                break;
            case R.id.fab_rating:
                updateMovies("top_rated");
                closeFab();
                break;
            case R.id.fab_popular:
                updateMovies("popular");
                closeFab();
                break;
            case R.id.fab_favorites:
                // Change the listView content to fetch favorite movies from Realm
                movies.clear();
                final RealmResults<FavoriteMovie> favMovies = realm.where(FavoriteMovie.class).findAll();
                for (FavoriteMovie favMovie: favMovies) {
                    Movie movie = new Movie();
                    movie.setTitle(favMovie.getMovieTitle());
                    movie.setVoteAverage(Double.parseDouble(favMovie.getMovieRating()));
                    movie.setPosterPath(favMovie.getMoviePoster());
                    movie.setOverview(favMovie.getMovieDescription());
                    movie.setId(favMovie.getMovieId());
                    movies.add(movie);
                }
                rView.getAdapter().notifyDataSetChanged();
                closeFab();
                break;
        }
    }
}
