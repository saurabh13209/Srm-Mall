package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AskSelfActivity extends AppCompatActivity {
    LinearLayout list, Edit;
    View view;
    ShareHolder shareHolder;
    ArrayList SrNo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_self);
        progressDialog = new ProgressDialog(AskSelfActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE)
                .setIsDesignedForFamilies(false).build();
        MobileAds.initialize(this, "ca-app-pub-6626107194157938~6507686342");
        mAdView.loadAd(adRequest);

        shareHolder = new ShareHolder(AskSelfActivity.this);
        list = findViewById(R.id.AskSelfList);
        SrNo = new ArrayList();
        GetInfo();
    }

    private void GetInfo() {
        list.removeAllViews();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.GetSelfRequest), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONArray array = new JSONArray(response);
                    for (int i=0 ; i<array.length() ; i++){
                        JSONArray inner = array.getJSONArray(i);
                        view = getLayoutInflater().inflate(R.layout.anybody_each_list , null);
                        Edit = view.findViewById(R.id.AskEditLayout);
                        Edit.setVisibility(View.VISIBLE );
                        final TextView Name , Mob , Text;
                        Name = view.findViewById(R.id.AskName);
                        Mob = view.findViewById(R.id.AskMob);
                        Text = view.findViewById(R.id.AskText);

                        Name.setText(inner.getString(0));
                        Mob.setText(inner.getString(1));
                        Text.setText(inner.getString(2).toString().replace("$","\n"));
                        SrNo.add(inner.getString(3));

                        ImageView Refresh ,Del;
                        Refresh = view.findViewById(R.id.AskRefresh);
                        Del = view.findViewById(R.id.AskDelelte);

                        final int finalI = i;
                        Del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SetDataEdit(getString(R.string.DeleteRequest) , Name.getText().toString() , Mob.getText().toString() , Text.getText().toString() , SrNo.get(finalI).toString() , shareHolder.getId());
                            }
                        });

                        Refresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SetDataEdit(getString(R.string.RefreshRequest) , Name.getText().toString() , Mob.getText().toString() , Text.getText().toString() , SrNo.get(finalI).toString() , shareHolder.getId());
                            }
                        });

                        list.addView(view);

                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("Id", shareHolder.getId());
                return map;
            }
        };
        MySending.getInstance(AskSelfActivity.this).addToRequestQueue(stringRequest);
    }

    private void SetDataEdit(String URL , final String Name , final String Mob , final String Text , final String SrNo , final String every_id){
        progressDialog.show();
        StringRequest EditRequest =  new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GetInfo();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("SrNo" , SrNo );
                map.put("Name",Name);
                map.put("Mob",Mob);
                map.put("Text",Text);
                map.put("every_id",every_id);
                return map;
            }
        };
        MySending.getInstance(AskSelfActivity.this).addToRequestQueue(EditRequest);
    }
}
