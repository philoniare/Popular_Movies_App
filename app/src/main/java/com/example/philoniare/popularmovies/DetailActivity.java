package com.example.philoniare.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.philoniare.popularmovies.MovieDBAPI.MovieDBClient;
import com.example.philoniare.popularmovies.MovieDBAPI.MovieDBServiceGenerator;
import com.example.philoniare.popularmovies.MovieDBAPI.Review;
import com.example.philoniare.popularmovies.MovieDBAPI.ReviewResult;
import com.example.philoniare.popularmovies.MovieDBAPI.Video;
import com.example.philoniare.popularmovies.MovieDBAPI.VideoResult;
import com.example.philoniare.popularmovies.adapter.ReviewAdapter;
import com.example.philoniare.popularmovies.adapter.TrailerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.play_fab) FloatingActionButton fab;
    @Bind(R.id.movie_poster) ImageView movie_poster;
    @Bind(R.id.title) TextView titleView;
    @Bind(R.id.description) TextView descriptionView;
    @Bind(R.id.detail_toolbar) Toolbar toolbar;
    @Bind(R.id.trailers) ListView trailersLV;
    @Bind(R.id.reviews) ListView reviewsLV;


    ArrayList<Review> reviews = new ArrayList<>();
    TrailerAdapter trailerAdapter;
    ArrayList<Video> trailers = new ArrayList<>();
    ReviewAdapter reviewAdapter;
    MovieDBClient client;
    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        toolbar.getBackground().setAlpha(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setImageResource(R.drawable.star_pressed);
                // Update the database with fav settings
            }
        });

        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch(NullPointerException e) {
            Log.e("Popular Movies: ", "SupportActionBar - " + e.toString());
        }


//        // Get the movie information from Realm
//        int movie_id = getIntent().getExtras().getInt("id");
//        Realm realm = Realm.getDefaultInstance();
//        Movie currMovie = realm.where(Movie.class)
//                .equalTo("id", movie_id).findFirst();
//
//        Log.d("CurrMovie: ", currMovie.getTitle());
        client = MovieDBServiceGenerator.createService(MovieDBClient.class);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String poster = bundle.getString("poster");
        String releaseDate = bundle.getString("releaseDate");
        String description = bundle.getString("description");
        String rating = bundle.getString("rating");
        String trailer = bundle.getString("trailer");
        movieId = bundle.getInt("id");

        // Test with bind here
        String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
        Picasso.with(this).load(BASE_IMAGE_URL + poster).into(movie_poster);
        titleView.setText(title);
        descriptionView.setText(description);

        trailerAdapter = new TrailerAdapter(this, trailers);
        reviewAdapter = new ReviewAdapter(this, reviews);
        trailersLV.setAdapter(trailerAdapter);
        reviewsLV.setAdapter(reviewAdapter);


        fetchReviews();
        fetchTrailers();
    }

    private void fetchReviews(){
        Call<ReviewResult> call = client.fetchReviews(movieId);
        call.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                if(response.isSuccess()) {
                    ReviewResult res = response.body();
                    for(Review review : res.getResults()) {
                        reviewAdapter.add(review);
                        Log.d("Popular Apps: ", review.getAuthor());
                    }
                } else {
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                    Log.e("Network Error: ", errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchTrailers(){
        Call<VideoResult> call = client.fetchVideos(movieId);
        call.enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                if(response.isSuccess()) {
                    VideoResult res = response.body();
                    for(Video trailer : res.getResults()) {
                        trailerAdapter.add(trailer);
                        Log.d("Popular Apps: ", trailer.getKey());
                    }
                } else {
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                    Log.e("Network Error: ", errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
