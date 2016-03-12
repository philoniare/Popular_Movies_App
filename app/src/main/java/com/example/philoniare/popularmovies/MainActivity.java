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

import com.example.philoniare.popularmovies.MovieDBAPI.APIResult;
import com.example.philoniare.popularmovies.MovieDBAPI.MovieDBClient;
import com.example.philoniare.popularmovies.MovieDBAPI.MovieDBServiceGenerator;
import com.example.philoniare.popularmovies.MovieDBAPI.Result;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rView;
    private GridLayoutManager layoutManager;
    public static List<Result> movies;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab_rating,fab_popular;
    private Animation fab_open,fab_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Fab button used to select sorting method
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab_rating = (FloatingActionButton)findViewById(R.id.fab_rating);
        fab_popular = (FloatingActionButton)findViewById(R.id.fab_popular);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fab.setOnClickListener(this);
        fab_rating.setOnClickListener(this);
        fab_popular.setOnClickListener(this);

        movies = new ArrayList<Result>();

        layoutManager = new GridLayoutManager(MainActivity.this, 2);
        rView = (RecyclerView) findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(layoutManager);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, movies);
        rView.setAdapter(rcAdapter);

        // Fetch the most popular movies and populate the view
        fetchMovies("popular");
    }

    private void fetchMovies(String criteria){
        MovieDBClient client = MovieDBServiceGenerator.createService(MovieDBClient.class);

        Call<APIResult> call = client.fetchMovies(criteria);
        call.enqueue(new Callback<APIResult>() {
            @Override
            public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                if(response.isSuccess()) {
                    APIResult res = response.body();
                    for(Result movie : res.getResults()) {
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
            public void onFailure(Call<APIResult> call, Throwable t) {
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
            fab_rating.setClickable(true);
            fab_popular.setClickable(true);
            isFabOpen = true;
        }
    }

    private void closeFab() {
        fab_rating.startAnimation(fab_close);
        fab_popular.startAnimation(fab_close);
        fab_rating.setClickable(false);
        fab_popular.setClickable(false);
        isFabOpen = false;
    };

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
        }
    }
}
