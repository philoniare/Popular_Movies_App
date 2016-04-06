package com.example.philoniare.popularmovies;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.philoniare.popularmovies.MovieDBAPI.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.movie_poster) ImageView movie_poster;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent detailViewIntent = new Intent(view.getContext(), DetailActivity.class);

        Movie movie = MainActivity.movies.get(getAdapterPosition());
        detailViewIntent.putExtra("title", movie.getTitle())
                .putExtra("poster", movie.getPosterPath())
                .putExtra("releaseDate", movie.getReleaseDate())
                .putExtra("description", movie.getOverview())
                .putExtra("rating", movie.getVoteAverage().toString())
                .putExtra("trailer", movie.getVideo().toString());
        view.getContext().startActivity(detailViewIntent);
    }
}
