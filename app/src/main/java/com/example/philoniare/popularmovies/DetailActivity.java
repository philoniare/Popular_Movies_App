package com.example.philoniare.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.philoniare.popularmovies.RealmDB.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.play_fab) FloatingActionButton fab;
    @Bind(R.id.movie_poster) ImageView movie_poster;
    @Bind(R.id.title) TextView titleView;
    @Bind(R.id.description) TextView descriptionView;
    @Bind(R.id.detail_toolbar) Toolbar toolbar;

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


        // Get the movie information from Realm
        int movie_id = getIntent().getExtras().getInt("id");
        Realm realm = Realm.getDefaultInstance();
        Movie currMovie = realm.where(Movie.class)
                .equalTo("id", movie_id).findFirst();

        Log.d("CurrMovie: ", currMovie.getTitle());


        // Test with bind here
        String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
        Picasso.with(this).load(BASE_IMAGE_URL + currMovie.getPoster()).into(movie_poster);
        titleView.setText(currMovie.getTitle());
        descriptionView.setText(currMovie.getDescription());
    }
}
