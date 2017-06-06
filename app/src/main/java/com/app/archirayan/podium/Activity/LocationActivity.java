package com.app.archirayan.podium.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.app.archirayan.podium.Adapter.LocationAdapter;
import com.app.archirayan.podium.Adapter.LocationFragmentAdapter;
import com.app.archirayan.podium.Constant.Constant;
import com.app.archirayan.podium.Constant.Utils;
import com.app.archirayan.podium.Fragment.SendInviteFragment;
import com.app.archirayan.podium.R;
import com.app.archirayan.podium.Utils.ClickListener;
import com.app.archirayan.podium.Utils.RecyclerTouchListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by archirayan on 23-Sep-16.
 */
public class LocationActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public Utils utils;
    public LocationAdapter locationAdapter;
    public EditText locationEdt;
    public LocationFragmentAdapter LocationFragmentAdapter;
    ArrayList<String> locationArray, businessNameArray/*,businessId*/, locationId;
    ArrayList<String> locationSearchArray, businessNameSearchArray, locationSearchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = Utils.ReadSharePrefrence(LocationActivity.this, Constant.UserId);

        if (userId.equalsIgnoreCase("")) {

            Intent in = new Intent(LocationActivity.this, Login.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
            finish();

        } else {

            setContentView(R.layout.activity_location);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            locationEdt = (EditText) findViewById(R.id.activity_location_edt);
            utils = new Utils(LocationActivity.this);
            locationSearchArray = new ArrayList<String>();
            businessNameSearchArray = new ArrayList<String>();
            locationSearchId = new ArrayList<String>();

            if (utils.isConnectingToInternet()) {
                new getLocation().execute();
            }
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                    recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {

                    Utils.WriteSharePrefrence(LocationActivity.this, Constant.UserSelectedLocation, businessNameArray.get(position));
                    Utils.WriteSharePrefrence(LocationActivity.this, Constant.UserSelectedLocationAddress, locationArray.get(position));
                    Utils.WriteSharePrefrence(LocationActivity.this, Constant.UserSelectedLocationId, locationId.get(position));
                    Intent in = new Intent(LocationActivity.this, MainActivity.class);
                    in.putExtra("isFirstTime", "true");
                    Utils.WriteSharePrefrence(LocationActivity.this, Constant.ISFIRSTTMIE, "1");
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
            }));

            locationEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    /*if (s.length() > 0) {
                        locationSearchArray = new ArrayList<String>();
                        businessNameSearchArray = new ArrayList<String>();
                        locationSearchId = new ArrayList<String>();
                        for (int i = 0; i < locationArray.size(); i++) {
                            if (businessNameArray.get(i).contains(s.toString()) || locationArray.get(i).contains(s.toString())) {
                                Toast.makeText(LocationActivity.this, s, Toast.LENGTH_SHORT).show();
                                locationSearchArray.add(locationArray.get(i));
                                businessNameSearchArray.add(businessNameArray.get(i));
                                locationSearchId.add(locationId.get(i));
                            } else {
                            }
                        }
                        locationAdapter = new LocationAdapter(LocationActivity.this, locationSearchArray, businessNameSearchArray, locationSearchId*//*,businessNameSearchId*//*);
                        locationAdapter.notifyDataSetChanged();

                    } else {
//
                        locationSearchArray = new ArrayList<String>();
                        businessNameSearchArray = new ArrayList<String>();
                        locationSearchId = new ArrayList<String>();
                        locationAdapter = new LocationAdapter(LocationActivity.this, locationArray, businessNameArray, locationId*//*,businessNameSearchId*//*);
                        locationAdapter.notifyDataSetChanged();
//
                    }*/
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        for (int i = 0; i < locationArray.size(); i++) {
                            if(i==0){
                                locationSearchArray.clear();
                                businessNameSearchArray.clear();
                                locationSearchId.clear();  }
                            Log.d("BusinessName", "@" + businessNameArray.get(i) + " : " + s.toString());
                            if (businessNameArray.get(i).toLowerCase().contains(s.toString().toLowerCase())) {
                                locationSearchArray.add(locationArray.get(i));
                                businessNameSearchArray.add(businessNameArray.get(i));
                                locationSearchId.add(locationId.get(i));
                            }
                        }
                        LocationFragmentAdapter = new LocationFragmentAdapter(LocationActivity.this, locationSearchArray, businessNameSearchArray,getSupportFragmentManager(), locationSearchId);
                        recyclerView.setAdapter(LocationFragmentAdapter);

                    } else {
                        LocationFragmentAdapter = new LocationFragmentAdapter(LocationActivity.this, locationArray, businessNameArray, getSupportFragmentManager(), locationId);
                        recyclerView.setAdapter(LocationFragmentAdapter);
                    }
                }
            });
        }
    }

    private class getLocation extends AsyncTask<String, String, String> {
        public ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            locationArray = new ArrayList<>();
            businessNameArray = new ArrayList<>();
            locationId = new ArrayList<>();
            pd = new ProgressDialog(LocationActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return utils.getResponseofGet(Constant.BASE_URL + "get_location.php?business_id=" + utils.ReadSharePrefrence(LocationActivity.this, Constant.UsersBusinessId));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response", s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
                    JSONArray subArray = object.getJSONArray("data");
                    for (int i = 0; i < subArray.length(); i++) {
                        JSONObject locationObject = subArray.getJSONObject(i);
                        locationArray.add(locationObject.getString("location"));
                        businessNameArray.add(locationObject.getString("business_name"));
                        locationId.add(locationObject.getString("loc_id"));
                    }
                } else {
                    Toast.makeText(LocationActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                Toast.makeText(LocationActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            locationAdapter = new LocationAdapter(LocationActivity.this, locationArray, businessNameArray, locationId/*,businessId*/);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(locationAdapter);
            locationAdapter.notifyDataSetChanged();
        }
    }

}
