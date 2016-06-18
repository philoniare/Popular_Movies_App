package com.example.philoniare.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
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
    Realm realm;
    boolean isFavorite;
    FavoriteMovie currFavMovie;
    RealmObject realmCurrFavMovie;  // Realm managed object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();
        toolbar.getBackground().setAlpha(0);
        isFavorite = false;

        // Fetch data passed from MainActivity
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String poster = bundle.getString("poster");
        String releaseDate = bundle.getString("releaseDate");
        String description = bundle.getString("description");
        String rating = bundle.getString("rating");
        String trailer = bundle.getString("trailer");
        movieId = bundle.getInt("id");
        currFavMovie = new FavoriteMovie(movieId, title, poster, description, rating);


        // Find if movie is already favorited
        final RealmResults<FavoriteMovie> favMovies = realm.where(FavoriteMovie.class).findAll();
        for (FavoriteMovie favMovie : favMovies) {
            if (favMovie.getMovieId() == movieId) {
                realmCurrFavMovie = favMovie;
                fab.setImageResource(R.drawable.star_pressed);
                isFavorite = true;
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    fab.setImageResource(R.drawable.star_outline);
                    realm.beginTransaction();
                    realmCurrFavMovie.deleteFromRealm();
                    realm.commitTransaction();
                    isFavorite = false;
                } else {
                    fab.setImageResource(R.drawable.star_pressed);
                    realm.beginTransaction();
                    final FavoriteMovie favMovie = realm.copyToRealm(currFavMovie);
                    realm.commitTransaction();
                    isFavorite = true;
                }
            }
        });

        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch(NullPointerException e) {
            Log.e("Popular Movies: ", "SupportActionBar - " + e.toString());
        }

        client = MovieDBServiceGenerator.createService(MovieDBClient.class);

        // Test with bind here
        String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
        Picasso.with(this).load(BASE_IMAGE_URL + poster).into(movie_poster);
        String movie_title = title + "(Rating: " + rating + ")";
        titleView.setText(movie_title);
        descriptionView.setText(description);

        trailerAdapter = new TrailerAdapter(this, trailers);
        reviewAdapter = new ReviewAdapter(this, reviews);
        trailersLV.setAdapter(trailerAdapter);
        trailersLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String YOUTUBE_URL = "https://www.youtube.com/watch?v="
                        + trailers.get(position).getKey();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_URL));
                startActivity(i);
            }
        });
        reviewsLV.setAdapter(reviewAdapter);
        reviewsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String REVIEW_URL = reviews.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(REVIEW_URL));
                startActivity(i);
            }
        });

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
