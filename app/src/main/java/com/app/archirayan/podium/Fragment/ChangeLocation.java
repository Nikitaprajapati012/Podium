package com.app.archirayan.podium.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.app.archirayan.podium.Adapter.LocationFragmentAdapter;
import com.app.archirayan.podium.Constant.Constant;
import com.app.archirayan.podium.Constant.Utils;
import com.app.archirayan.podium.R;
import com.app.archirayan.podium.Utils.ClickListener;
import com.app.archirayan.podium.Utils.RecyclerTouchListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by archi1 on 9/23/2016.
 */
public class ChangeLocation extends Fragment {
    public RecyclerView recyclerView;
    public Utils utils;
    public LocationFragmentAdapter LocationFragmentAdapter;
    public EditText locationEdt;
    ArrayList<String> locationArray, businessNameArray, locationId;
    ArrayList<String> locationSearchArray, businessNameSearchArray, locationSearchId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);
        locationEdt = (EditText) view.findViewById(R.id.fragment_location_edt);
        utils = new Utils(getActivity());
        locationSearchArray = new ArrayList<String>();
        businessNameSearchArray = new ArrayList<String>();
        locationSearchId = new ArrayList<String>();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Utils.WriteSharePrefrence(getActivity(), Constant.UserSelectedLocation, businessNameArray.get(position));
                Utils.WriteSharePrefrence(getActivity(), Constant.UserSelectedLocationAddress, locationArray.get(position));
                Utils.WriteSharePrefrence(getActivity(), Constant.UserSelectedLocationId, locationId.get(position));
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SendInviteFragment fragment = new SendInviteFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();

            }
        }));

        locationEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
                    LocationFragmentAdapter = new LocationFragmentAdapter(getActivity(), locationSearchArray, businessNameSearchArray, getActivity().getSupportFragmentManager(), locationSearchId);
                    recyclerView.setAdapter(LocationFragmentAdapter);

                } else {
                    LocationFragmentAdapter = new LocationFragmentAdapter(getActivity(), locationArray, businessNameArray, getActivity().getSupportFragmentManager(), locationId);
                    recyclerView.setAdapter(LocationFragmentAdapter);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (utils.isConnectingToInternet()) {
            new getLocation().execute();
        } else {
            Toast.makeText(getActivity(), getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
        }
    }

    private class getLocation extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            locationArray = new ArrayList<>();
            businessNameArray = new ArrayList<>();
            locationId = new ArrayList<>();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return utils.getResponseofGet(Constant.BASE_URL + "get_location.php?business_id=" + Utils.ReadSharePrefrence(getActivity(), Constant.UsersBusinessId));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            LocationFragmentAdapter = new LocationFragmentAdapter(getActivity(), locationArray, businessNameArray, getActivity().getSupportFragmentManager(), locationId);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(LocationFragmentAdapter);
            LocationFragmentAdapter.notifyDataSetChanged();
        }
    }


}
