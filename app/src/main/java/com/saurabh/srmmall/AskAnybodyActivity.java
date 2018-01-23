package com.saurabh.srmmall;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class AskAnybodyActivity extends AppCompatActivity {
    LinearLayout listView;
    View view;
    FloatingActionButton floatingActionButton ,AskSelf;
    ProgressDialog progressDialog;

    @Override
    protected void onResume() {
        if (ActivityCompat.checkSelfPermission(AskAnybodyActivity.this , Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            GetInfo();
        }else{
            ActivityCompat.requestPermissions(AskAnybodyActivity.this , new String[]{Manifest.permission.CALL_PHONE} , 120);
        }super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_anybody);


        progressDialog = new ProgressDialog(AskAnybodyActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        listView = findViewById(R.id.AskList);
        floatingActionButton = findViewById(R.id.AskAdd);
        AskSelf = findViewById(R.id.AskSelf);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskAnybodyActivity.this , AskAddActivity.class));
            }
        });

        AskSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskAnybodyActivity.this ,AskSelfActivity.class));
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE)
                .setIsDesignedForFamilies(false).build();
        MobileAds.initialize(this, "ca-app-pub-6626107194157938~6507686342");
        mAdView.loadAd(adRequest);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==120){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                GetInfo();
            }else {
                ActivityCompat.requestPermissions(AskAnybodyActivity.this , new String[]{Manifest.permission.CALL_PHONE} , 120);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void GetInfo(){
        progressDialog.show();
        listView.removeAllViews();
        StringRequest getrequest =  new StringRequest(Request.Method.POST, getString(R.string.GetRequest), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i=0 ; i<array.length() ; i++){
                        JSONArray inner = array.getJSONArray(i);
                        view = getLayoutInflater().inflate(R.layout.anybody_each_list , null);
                        final TextView Name , Mob , Text;
                        Name = view.findViewById(R.id.AskName);
                        Mob = view.findViewById(R.id.AskMob);
                        Text = view.findViewById(R.id.AskText);

                        Name.setText(inner.getString(0));
                        Mob.setText(inner.getString(1));
                        Text.setText(inner.getString(2).toString().replace("$","\n"));

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String dial = "tel:" + Mob.getText().toString();
                                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                            }
                        });
                        listView.addView(view);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySending.getInstance(AskAnybodyActivity.this).addToRequestQueue(getrequest);
    }
}
