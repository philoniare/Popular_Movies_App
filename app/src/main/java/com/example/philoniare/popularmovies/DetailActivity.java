package com.example.philoniare.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Will play the trailer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch(NullPointerException e) {
            Log.e("Popular Movies: ", "SupportActionBar - " + e.toString());
        }

        toolbar.getBackground().setAlpha(0);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String poster = bundle.getString("poster");
        String releaseDate = bundle.getString("releaseDate");
        String description = bundle.getString("description");
        String rating = bundle.getString("rating");
        String trailer = bundle.getString("trailer");

        // Test with bind here
        String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
        Picasso.with(this).load(BASE_IMAGE_URL + poster).into(movie_poster);
        titleView.setText(title);
        descriptionView.setText(description);
    }
}
