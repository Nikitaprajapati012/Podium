package com.app.archirayan.podium.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.archirayan.podium.Constant.Constant;
import com.app.archirayan.podium.Constant.Utils;
import com.app.archirayan.podium.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Activity {
    public EditText userNameEdt, passwordEdt;
    public Button submitBtn;
    public String userName, password;
    public Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        utils = new Utils(Login.this);
        userNameEdt = (EditText) findViewById(R.id.activity_login_email);
        passwordEdt = (EditText) findViewById(R.id.activity_login_password);
        submitBtn = (Button) findViewById(R.id.activity_login_submit);

        //((ImageView) findViewById(R.id.actionBarBackImage)).setVisibility(View.GONE);

        init();

    }

    private void init() {
        userNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && passwordEdt.length() > 0) {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.darkgreen));
                } else {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.lightgreen));
                }
            }
        });
        passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && userNameEdt.length() > 0) {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.darkgreen));
                } else {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.lightgreen));
                }
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = userNameEdt.getText().toString();
                Log.e("uname",userName);
                password = passwordEdt.getText().toString();
                Log.e("pwd",password);
                if (utils.isConnectingToInternet()) {

                    new getLogin().execute();

                }


            }
        });


    }


    private class getLogin extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Login.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("LOGIN",Constant.BASE_URL + "login.php?username=" + userName + "&password=" + password);
            return utils.getResponseofGet(Constant.BASE_URL + "login.php?username=" + userName + "&password=" + password);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {

                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
                    JSONObject subObject = object.getJSONObject("data");
                    Utils.WriteSharePrefrence(Login.this, Constant.UserId, subObject.getString("id"));
                    Utils.WriteSharePrefrence(Login.this, Constant.UsersBusinessId, subObject.getString("business_id"));
                    Intent in = new Intent(Login.this, LocationActivity.class);
                   // Intent in = new Intent(Login.this, SendInvite.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(Login.this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
