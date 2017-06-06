package com.app.archirayan.podium.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by archirayan on 22-Sep-16.
 */
public class SendInvite extends Activity {
    public EditText emailEdt, nameEdt;
    public String email, name;
    public Utils utils;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        emailEdt = (EditText) findViewById(R.id.activity_invite_email);
        nameEdt = (EditText) findViewById(R.id.activity_invite_password);
        submitBtn = (Button) findViewById(R.id.activity_invite_submit);

       /* ((ImageView) findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
*/
        utils = new Utils(SendInvite.this);

        emailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.darkgreen));
                } else {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.lightgreen));
                }
            }
        });
        nameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (emailEdt.length() > 0) {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.darkgreen));
                } else {
                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.lightgreen));
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEdt.getText().toString().trim();
                name = nameEdt.getText().toString().trim();

                if (email.length() > 0) {

                    new getData().execute();

                }

            }
        });

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.activity_invite, container, false);
//        emailEdt = (EditText) view.findViewById(R.id.activity_invite_email);
//        nameEdt = (EditText) view.findViewById(R.id.activity_invite_password);
//        submitBtn = (Button) view.findViewById(R.id.activity_invite_submit);
//
//        utils = new Utils(SendInvite.this);
//
//        emailEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() > 0 && nameEdt.length() > 0) {
//                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.darkgreen));
//                } else {
//                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.lightgreen));
//                }
//            }
//        });
//        nameEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() > 0 && emailEdt.length() > 0) {
//                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.darkgreen));
//                } else {
//                    submitBtn.setBackgroundColor(ContextCompat.getColor(SendInvite.this, R.color.lightgreen));
//                }
//            }
//        });
//
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                email = emailEdt.getText().toString();
//                name = nameEdt.getText().toString();
//
//                if (name.length() > 0 && email.length() > 0) {
//
//
//                    new getData().execute();
//
//                }
//
//            }
//        });
//
//        return view;
//    }

    private class getData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SendInvite.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String data = "";
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//    http://181.224.157.105/~hirepeop/host1/podium/api/send_invite.php?phone_no=917990028079&name=nik&bussiness_id=27
                try {
                    data = utils.getResponseofGet(Constant.BASE_URL + "send_invite.php?email=" + email + "&name=" + URLEncoder.encode(name, "UTF-8") + "&bussiness_id=" + Utils.ReadSharePrefrence(SendInvite.this, Constant.UsersBusinessId));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else if (android.util.Patterns.PHONE.matcher(email).matches()) {
//                http://181.224.157.105/~hirepeop/host1/podium/api/send_invite.php?phone_no=+918866793181&name=abcd&buisness=genttaltoyota
                try {
                    data = utils.getResponseofGet(Constant.BASE_URL + "send_invite.php?phone_no=" + email + "&name=" + URLEncoder.encode(name, "UTF-8") + "&bussiness_id=" + Utils.ReadSharePrefrence(SendInvite.this, Constant.UsersBusinessId));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
                    Toast.makeText(SendInvite.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                    emailEdt.setText("");
                    nameEdt.setText("");
                } else {
                    Toast.makeText(SendInvite.this,object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
