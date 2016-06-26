package com.example.philoniare.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.philoniare.popularmovies.model.Review;
import com.example.philoniare.popularmovies.R;

import java.util.ArrayList;


public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_review, parent, false);
        }
        TextView reviewAuthor = (TextView) convertView.findViewById(R.id.author);
        reviewAuthor.setText(review.getContent() + " by " + review.getAuthor());
        return convertView;
    }
}
