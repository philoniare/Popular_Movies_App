package com.example.philoniare.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.philoniare.popularmovies.MovieDBAPI.Video;
import com.example.philoniare.popularmovies.R;

import java.util.ArrayList;


public class TrailerAdapter extends ArrayAdapter<Video> {

    public TrailerAdapter(Context context, ArrayList<Video> trailers) {
        super(context, 0, trailers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video trailer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trailer, parent, false);
        }
        TextView reviewAuthor = (TextView) convertView.findViewById(R.id.name);
        reviewAuthor.setText(trailer.getName());
        return convertView;
    }
}
