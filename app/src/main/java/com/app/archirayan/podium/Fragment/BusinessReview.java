package com.app.archirayan.podium.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.archirayan.podium.Activity.ReviewActivity;
import com.app.archirayan.podium.Activity.SendInvite;
import com.app.archirayan.podium.Constant.Constant;
import com.app.archirayan.podium.Constant.Utils;
import com.app.archirayan.podium.R;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archi1 on 9/23/2016.
 */
public class BusinessReview extends Fragment {
    public StickyScrollView scrollView;
    public Utils utils;
    public ArrayList<String> rating, username, descriptoin;
    public LinearLayout listView;
    public LinearLayout halfLinearLayout, fullLinearLayout;
    public LayoutInflater inflater;
    public TextView titleTv, addressTv, stickyTitleTv;
    public Button overAllBtn, totalReview, thisWeekBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_get_review, container, false);
        scrollView = (StickyScrollView) view.findViewById(R.id.ScrollView);
        halfLinearLayout = (LinearLayout) view.findViewById(R.id.halfLinearLayout);
        fullLinearLayout = (LinearLayout) view.findViewById(R.id.fullLinearLayout);
        titleTv = ((TextView) view.findViewById(R.id.fragment_get_review_title));
        addressTv = ((TextView) view.findViewById(R.id.fragment_get_review_location));
        stickyTitleTv = (TextView) view.findViewById(R.id.fragment_get_review_titlesticky);
        overAllBtn = (Button) view.findViewById(R.id.fragment_get_review_overall_rating);
        totalReview = (Button) view.findViewById(R.id.fragment_get_review_total_review);
        thisWeekBtn = (Button) view.findViewById(R.id.fragment_get_review_review_this_week);

        setHasOptionsMenu(true);
        titleTv.setText(Utils.ReadSharePrefrence(getActivity(), Constant.UserSelectedLocation));
        addressTv.setText(Utils.ReadSharePrefrence(getActivity(), Constant.UserSelectedLocationAddress));
        stickyTitleTv.setText(Utils.ReadSharePrefrence(getActivity(), Constant.UserSelectedLocation));

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if (scrollY > 40) {
                    halfLinearLayout.setTag("sticky");
                    halfLinearLayout.setVisibility(View.VISIBLE);
                    fullLinearLayout.setVisibility(View.GONE);
                } else {
                    halfLinearLayout.setTag("");
                    halfLinearLayout.setVisibility(View.GONE);
                    fullLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

//        scrollView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(View v) {
//                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onViewDetachedFromWindow(View v) {
//                Toast.makeText(getActivity(), "E gayu", Toast.LENGTH_SHORT).show();
//            }
//        });


        listView = (LinearLayout) view.findViewById(R.id.fragment_get_review_list);
        utils = new Utils(getActivity());
        this.inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (utils.isConnectingToInternet()) {

            new getData().execute();

        } else {

        }


//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.d("Data", "" + scrollY + "-- " + oldScrollY);
//            }
//        });


        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.invite:

                Intent in = new Intent(getActivity(), SendInvite.class);
                startActivity(in);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.mail_menu, menu);
    }

    private class getData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rating = new ArrayList<>();
            username = new ArrayList<>();
            descriptoin = new ArrayList<>();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //http://181.224.157.105/~hirepeop/host1/podium/api/get_all_review.php?business_id=6
            return utils.getResponseofGet(Constant.BASE_URL + "get_all_review.php?business_id=" + Utils.ReadSharePrefrence(getActivity(), Constant.UserSelectedLocationId));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {

                    overAllBtn.setText(object.getString("rating") + " \n  " + Html.fromHtml("<b>  Overall Rating </b>"));
                    totalReview.setText(object.getString("totalreview") + " \n  " + Html.fromHtml("<b>  Total Rating </b>"));
                    thisWeekBtn.setText(object.getString("weekreviews") + " \n  " + Html.fromHtml("<b>  Reviews This Week </b>"));

                    JSONArray subArray = object.getJSONArray("data");
                    for (int i = 0; i < subArray.length(); i++) {

                        View view = inflater.inflate(R.layout.adapter_review, null);

                        final JSONObject subObject = subArray.getJSONObject(i);
                        username.add(subObject.getString("author_name"));
                        rating.add(subObject.getString("rating"));
                        descriptoin.add(subObject.getString("text"));
                        TextView usernameTv = (TextView) view.findViewById(R.id.adapter_review_username);
                        TextView descriptionTv = (TextView) view.findViewById(R.id.adapter_review_description);
                        TextView dateTv = (TextView) view.findViewById(R.id.adapter_review_date);
                        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.adapter_review_rating);
                        ImageView imageView = (ImageView) view.findViewById(R.id.adapter_review_image);
                        ratingBar.setStar(Float.valueOf(subObject.getString("rating")));

                        if (subObject.getString("status").equalsIgnoreCase("google")) {
                            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_google));
                        } else if (subObject.getString("status").equalsIgnoreCase("facebook")) {
                            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_facebook));
                        } else if (subObject.getString("status").equalsIgnoreCase("yelp")) {
                            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_yelp));
                        }
                        dateTv.setText(subObject.getString("time"));
                        usernameTv.setText(subObject.getString("author_name"));
                        descriptionTv.setText(subObject.getString("text"));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//ltrb
                        layoutParams.setMargins(10, 10, 10, 10);
                        final int temp = i;
                        final String username = subObject.getString("author_name");
                        final String description = subObject.getString("text");
                        final String rating = subObject.getString("rating");
                        final String status = subObject.getString("status");
                        final String time = subObject.getString("time");

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent in = new Intent(getActivity(), ReviewActivity.class);
                                in.putExtra("username", username);
                                in.putExtra("description", description);
                                in.putExtra("rating", rating);
                                in.putExtra("status", status);
                                in.putExtra("time", time);
                                startActivity(in);
                            }
                        });
                        listView.addView(view, layoutParams);
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
//            ReviewAdapter adapter = new ReviewAdapter(getActivity(), username, rating, descriptoin);
//            listView.setAdapter(adapter);
        }
    }
}
