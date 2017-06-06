package com.app.archirayan.podium.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.archirayan.podium.R;
import java.util.ArrayList;

/**
 * Created by archirayan on 23-Sep-16.
 */
public class LocationFragmentAdapter extends RecyclerView.Adapter<LocationFragmentAdapter.MyViewHolder> {
    ArrayList<String> locationArray, businessNameArray, locationId;
    Context context;
    FragmentManager fragmentManager;

    public LocationFragmentAdapter(Context context, ArrayList<String> locationArray, ArrayList<String> businessArray, FragmentManager supportFragmentManager, ArrayList<String> locationId) {
        this.context = context;
        this.locationId = locationId;
        this.fragmentManager = supportFragmentManager;
        this.locationArray = locationArray;
        this.businessNameArray = businessArray;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_business_location, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /*holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.WriteSharePrefrence(context, Constant.UserSelectedLocation, businessNameArray.get(position));
                Utils.WriteSharePrefrence(context, Constant.UserSelectedLocationAddress, locationArray.get(position));
                Utils.WriteSharePrefrence(context, Constant.UserSelectedLocationId, locationId.get(position));

                *//*BusinessReview fragment = new BusinessReview();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*//*

            }
        });
        */
        holder.title.setText(businessNameArray.get(position));
        holder.details.setText(locationArray.get(position));

    }

    @Override
    public int getItemCount() {
        return locationArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.adapter_business_location_linear);
            title = (TextView) view.findViewById(R.id.adapter_business_location_title);
            details = (TextView) view.findViewById(R.id.adapter_business_location_details);
        }
    }
}
