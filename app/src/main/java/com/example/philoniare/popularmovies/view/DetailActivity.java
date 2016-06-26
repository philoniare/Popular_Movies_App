package com.example.philoniare.popularmovies.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.philoniare.popularmovies.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        if(savedInstanceState == null) {
            DetailFragment detailFragment = new DetailFragment();

            Bundle bundle = this.getIntent().getExtras();
            Bundle arguments = new Bundle();
            arguments.putString("title", bundle.getString("title"));
            arguments.putString("poster", bundle.getString("poster"));
            arguments.putString("releaseDate", bundle.getString("releaseDate"));
            arguments.putString("description", bundle.getString("description"));
            arguments.putString("rating", bundle.getString("rating"));
            arguments.putString("trailer", bundle.getString("trailer"));
            arguments.putInt("id", bundle.getInt("id"));
            detailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, detailFragment)
                    .commit();
        }
    }
}
