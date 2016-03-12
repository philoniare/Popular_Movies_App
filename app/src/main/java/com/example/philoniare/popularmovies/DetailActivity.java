package com.example.philoniare.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by philoniare on 3/11/16.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.play_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Will play the trailer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbar.getBackground().setAlpha(0);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String poster = bundle.getString("poster");
        String releaseDate = bundle.getString("releaseDate");
        String description = bundle.getString("description");
        String rating = bundle.getString("rating");
        String trailer = bundle.getString("trailer");

        ImageView movie_poster = (ImageView) findViewById(R.id.movie_poster);
        String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
        Picasso.with(this).load(BASE_IMAGE_URL + poster).into(movie_poster);

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(title);

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(description);
    }
}
