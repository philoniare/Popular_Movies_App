package com.example.philoniare.popularmovies.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.philoniare.popularmovies.R;

public class MainActivity extends AppCompatActivity  {
    public static boolean mTwoPane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_fragment) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_fragment, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
        MovieFragment movieFragment = ((MovieFragment) getSupportFragmentManager()
            .findFragmentById(R.id.movie_fragment));
    }
}
