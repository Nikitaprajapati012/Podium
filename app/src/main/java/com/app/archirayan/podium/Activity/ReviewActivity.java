package com.app.archirayan.podium.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.archirayan.podium.R;
import com.hedgehog.ratingbar.RatingBar;


/**
 * Created by archirayan on 22-Oct-16.
 */

public class ReviewActivity extends Activity {

    public String username, time, description, status, rating;
    public TextView usernameTv, descriptionTv, timeTv;
    public ImageView imageView;
    public RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        /*((ImageView) findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("username");
            description = getIntent().getExtras().getString("description");
            rating = getIntent().getExtras().getString("rating");
            status = getIntent().getExtras().getString("status");
            time = getIntent().getExtras().getString("time");
        }

        usernameTv = (TextView) findViewById(R.id.activity_review_username);
        descriptionTv = (TextView) findViewById(R.id.activity_review_description);
        timeTv = (TextView) findViewById(R.id.activity_review_date);
        imageView = (ImageView) findViewById(R.id.activity_review_image);
        ratingBar = (RatingBar) findViewById(R.id.activity_review_rating);



//        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        usernameTv.setText(username);
        descriptionTv.setText(description);
        descriptionTv.setMovementMethod(new ScrollingMovementMethod());
        timeTv.setText(time);
//        if (android.os.Build.VERSION.SDK_INT > 22) {
//
//            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//            stars.getDrawable(0).setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(1).setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(2).setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_ATOP);
//
//        } else {
//
//        }


        if (status.equalsIgnoreCase("google")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(ReviewActivity.this, R.drawable.ic_google));
        } else if (status.equalsIgnoreCase("facebook")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(ReviewActivity.this, R.drawable.ic_facebook));
        } else if (status.equalsIgnoreCase("yelp")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(ReviewActivity.this, R.drawable.ic_yelp));
        }

        ratingBar.setStar(Float.valueOf(rating));
//        Log.d("Rating", String.valueOf(rating));
        //ratingBar.setrating(Float.valueOf(rating));
//        ratingBar.setRating(Integer.parseInt(rating));
//        ratingBar.setRating(1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show();
    }
}
