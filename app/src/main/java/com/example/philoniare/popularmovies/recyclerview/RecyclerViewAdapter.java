package com.example.philoniare.popularmovies.recyclerview;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.philoniare.popularmovies.R;
import com.example.philoniare.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Movie> movies;
    private Context context;
    private FragmentManager mFragmentManager;

    public RecyclerViewAdapter(Context context, List<Movie> movies, FragmentManager fragmentManager) {
        this.movies = movies;
        this.context = context;
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_list_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, mFragmentManager);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
        Picasso.with(context).load(BASE_IMAGE_URL + movies.get(position).getPosterPath()).into(holder.movie_poster);
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }
}
