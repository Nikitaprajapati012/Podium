package com.app.archirayan.podium.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.archirayan.podium.R;

import java.util.ArrayList;

/**
 * Created by archi1 on 9/23/2016.
 */
public class ReviewAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> username, rate, description;
    LayoutInflater inflater;

    public ReviewAdapter(Context context, ArrayList<String> username, ArrayList<String> rate, ArrayList<String> description) {
        this.context = context;
        this.username = username;
        this.rate = rate;
        this.description = description;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return username.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_review, null);

        TextView usernameTv = (TextView) view.findViewById(R.id.adapter_review_username);
        TextView descriptionTv = (TextView) view.findViewById(R.id.adapter_review_description);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.adapter_review_rating);

//        if (android.os.Build.VERSION.SDK_INT < 21) {
//
//            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//            stars.getDrawable(0).setColorFilter(FULL_COLOR, PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(1).setColorFilter(PARTIAL_COLOR, PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(2).setColorFilter(NONE_COLOR, PorterDuff.Mode.SRC_ATOP);
//
//        }

        usernameTv.setText(username.get(position));
        descriptionTv.setText(description.get(position));
        ratingBar.setRating(Float.parseFloat(rate.get(position)));

        return view;
    }
}
