package com.example.philoniare.popularmovies.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.philoniare.popularmovies.view.DetailFragment;
import com.example.philoniare.popularmovies.view.MainActivity;
import com.example.philoniare.popularmovies.view.MovieFragment;
import com.example.philoniare.popularmovies.R;
import com.example.philoniare.popularmovies.model.Movie;
import com.example.philoniare.popularmovies.view.DetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.movie_poster) ImageView movie_poster;
    private FragmentManager mFragmentManager;

    public RecyclerViewHolders(View itemView, FragmentManager fragmentManager) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View view) {
        boolean mTwoPane = MainActivity.mTwoPane;

        Movie movie = MovieFragment.movies.get(getAdapterPosition());
        Bundle arguments = new Bundle();
        arguments.putString("title", movie.getTitle());
        arguments.putString("poster", movie.getPosterPath());
        arguments.putString("releaseDate", movie.getReleaseDate());
        arguments.putString("description", movie.getOverview());
        arguments.putString("rating", movie.getVoteAverage().toString());
        arguments.putString("trailer", movie.getVideo().toString());
        arguments.putInt("id", movie.getId());

        if (mTwoPane) {
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(arguments);
            mFragmentManager.beginTransaction()
                    .replace(R.id.movie_detail_fragment, detailFragment)
                    .commit();
        } else {
            Intent detailViewIntent = new Intent(view.getContext(), DetailActivity.class);
            detailViewIntent.putExtras(arguments);
            view.getContext().startActivity(detailViewIntent);
        }
    }
}
