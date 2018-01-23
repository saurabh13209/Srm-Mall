package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    ListView SettingList;
    ShareHolder shareHolder;
    ProgressDialog progressDialog;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        progressDialog = new ProgressDialog(SettingActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        shareHolder = new ShareHolder(SettingActivity.this);
        SettingList = findViewById(R.id.SettingList);
        SettingList.setAdapter(new CustomAdapter());

        mAdView = findViewById(R.id.adView);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE)
                .setIsDesignedForFamilies(false).build();
        MobileAds.initialize(this, "ca-app-pub-6626107194157938~6507686342");
        mAdView.loadAd(adRequest);

    }

    private void sendName(final String name, final boolean isMessage) {
        progressDialog.show();
        StringRequest sendName = new StringRequest(Request.Method.POST, getString(R.string.SetName), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shareHolder.SetName(name);
                progressDialog.dismiss();
                if (isMessage) {
                    startActivity(new Intent(SettingActivity.this, MessageActivity.class));
                } else {
                    startActivity(new Intent(SettingActivity.this, CreateAccountActivity.class));
                }
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("basic", shareHolder.getAndroidId());
                map.put("name", name);
                return map;
            }
        };
        MySending.getInstance(SettingActivity.this).addToRequestQueue(sendName);
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            if (shareHolder.ShopName().equals("")) {
                return 4;
            } else {
                return 6;
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.setting_each_row, null);
            TextView Name = view.findViewById(R.id.SettingEachText);
            ImageView image = view.findViewById(R.id.SettingEachImage);

            switch (i) {
                case 0:
                    Name.setText("Message");
                    image.setImageResource(R.mipmap.ic_action_message);
                    break;
                case 1:
                    if (!shareHolder.ShopName().equals("")) {
                        Name.setText("Sell Product");
                    }
                    break;
                case 2:
                    if (shareHolder.getName().equals("")) {
                        Name.setText("Ask Anybody");
                        image.setImageResource(R.mipmap.ic_group);
                    } else {
                        Name.setText("Edit Products");
                        image.setImageResource(R.mipmap.ic_action_edit_product);
                    }
                    break;
                case 3:
                    if (shareHolder.getName().equals("")) {
                        Name.setText("Disclaimer");
                        image.setImageResource(R.mipmap.ic_des);
                        break;
                    } else {
                        Name.setText("Edit Profile");
                        image.setImageResource(R.mipmap.ic_action_edit_profile);
                    }
                    break;
                case 4:
                    Name.setText("Ask Anybody");
                    image.setImageResource(R.mipmap.ic_group);
                    break;
                case 5:
                    Name.setText("Disclaimer");
                    image.setImageResource(R.mipmap.ic_des);
                    break;
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (i) {
                        case 0:
                            if (!shareHolder.getName().equals("")) {
                                startActivity(new Intent(SettingActivity.this, MessageActivity.class));
                                finish();
                            } else {
                                new NameDialogBox(SettingActivity.this , getLayoutInflater()) {
                                    @Override
                                    public void DoNext(String Name) {
                                        sendName(Name, true);
                                    }
                                };
                            }
                            break;
                        case 1:
                            if (shareHolder.ShopName().equals("")) {
                                if (!shareHolder.getName().equals("")) {
                                    startActivity(new Intent(SettingActivity.this, CreateAccountActivity.class));
                                    finish();
                                } else {
                                    new NameDialogBox(SettingActivity.this , getLayoutInflater()) {
                                        @Override
                                        public void DoNext(String Name) {
                                            sendName(Name, false);
                                        }
                                    };
                                }

                            } else {
                                startActivity(new Intent(SettingActivity.this, ProductActivity.class));
                                finish();
                            }
                            break;
                        case 2:
                            if (shareHolder.ShopName().equals("")) {
                                startActivity(new Intent(SettingActivity.this , AskAnybodyActivity.class));
                            } else {
                                startActivity(new Intent(SettingActivity.this, EditProductActivity.class));
                                finish();
                            }
                            break;
                        case 3:
                            if (shareHolder.ShopName().equals("")) {
                                startActivity(new Intent(SettingActivity.this , AboutActivity.class));
                            } else {
                                startActivity(new Intent(SettingActivity.this, CreateAccountActivity.class).putExtra("Edit", ""));
                                finish();
                            }
                            break;
                        case 4:
                            startActivity(new Intent(SettingActivity.this , AskAnybodyActivity.class));
                            finish();
                            break;
                        case 5:
                            startActivity(new Intent(SettingActivity.this , AboutActivity.class));
                            finish();
                            break;
                    }
                }
            });
            return view;
        }
    }
}

