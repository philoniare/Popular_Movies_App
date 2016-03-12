package com.example.philoniare.popularmovies;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.philoniare.popularmovies.MovieDBAPI.Result;

/**
 * Created by philoniare on 3/11/16.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView movie_poster;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        movie_poster = (ImageView) itemView.findViewById(R.id.movie_poster);
    }

    @Override
    public void onClick(View view) {
        Intent detailViewIntent = new Intent(view.getContext(), DetailActivity.class);


        Result movie = MainActivity.movies.get(getAdapterPosition());
        detailViewIntent.putExtra("title", movie.getTitle())
                .putExtra("poster", movie.getPosterPath())
                .putExtra("releaseDate", movie.getReleaseDate())
                .putExtra("description", movie.getOverview())
                .putExtra("rating", movie.getVoteAverage().toString())
                .putExtra("trailer", movie.getVideo().toString());
        view.getContext().startActivity(detailViewIntent);
    }
}
