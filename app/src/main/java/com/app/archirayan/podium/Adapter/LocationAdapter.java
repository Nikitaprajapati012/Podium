package com.app.archirayan.podium.Adapter;

import android.content.Context;
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
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    ArrayList<String> locationArray, businessNameArray, locationId/*,businessNameId*/;
    Context context;

    public LocationAdapter(Context context, ArrayList<String> locationArray, ArrayList<String> businessArray, ArrayList<String> locationId/*ArrayList<String> businessId*/) {
        this.context = context;
        this.locationArray = locationArray;
        this.businessNameArray = businessArray;
        this.locationId = locationId;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_business_location, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
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
