package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;
import java.util.Map;

public class ProductViewActivity extends AppCompatActivity {

    Button Next;
    TextView ProductName, ShopName, Price, Amount, Negotiable, ToolBarName, Mob;
    ImageView Image;
    ShareHolder shareHolder;
    ProgressDialog progressDialog;
    String EveryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        progressDialog = new ProgressDialog(ProductViewActivity.this);
        progressDialog.setMessage("PLease wait");
        progressDialog.setCancelable(false);
        Bundle Extra = getIntent().getExtras();
        ToolBarName = findViewById(R.id.ViewTootlBarName);
        ProductName = findViewById(R.id.ViewProductName);
        ShopName = findViewById(R.id.ViewShopName);
        Image = findViewById(R.id.ViewProductImage);
        Price = findViewById(R.id.ViewPrice);
        Mob = findViewById(R.id.ViewContactDetails);
        Amount = findViewById(R.id.ViewLeft);
        Negotiable = findViewById(R.id.Description);
        Next = findViewById(R.id.ViewButton);
        shareHolder = new ShareHolder(ProductViewActivity.this);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE)
                .setIsDesignedForFamilies(false).build();
        MobileAds.initialize(this, "ca-app-pub-6626107194157938~6507686342");
        mAdView.loadAd(adRequest);

        if (Extra != null) {

            EveryId = Extra.getString("To_Name");
            String Text = "";
            ProductName.setText(Extra.getString("ProductName"));

            ShopName.setText(Extra.getString("ShopName"));
            Price.setText(Extra.getString("Price"));
            Amount.setText(Extra.getString("Amount"));
            Log.d("DATA", Extra.getString("packed") + Extra.getString("cooked") + Extra.getString("station") + Extra.getString("electronic") + Extra.getString("movie") + Extra.getString("drugs"));
            if (Extra.getString("Mob").equals("")) {
                Mob.setText("No Contact Number..");
            } else {
                Mob.setText("Mob: " + Extra.getString("Mob"));
            }
            if (Extra.getString("packed").equals("true")) {
                Text = Text + "Product is Packed\n";
            }
            if (Extra.getString("cooked").equals("true")) {
                Text = Text + "Product will be served Cooked\n";
            }
            if (Extra.getString("station").equals("true")) {
                Text = Text + "Product is Stationary\n";
            }
            if (Extra.getString("electronic").equals("true")) {
                Text = Text + "Product is an electronic device (This app dosent give any Warranty/Guarantee for this)\n";
            }
            if (Extra.getString("movie").equals("true")) {
                Text = Text + "Product may be game,software,movie etc\n";
            }
            if (Extra.getString("drugs").equals("true")) {
                Text = Text + "Product contain drugs... please use inbuilt messaging system to be on the safe side\n";
            }
            if (Extra.getString("others").equals("true")) {
                Text = Text + "No idea about product...\n";
            }

            Negotiable.setText(Text);
            if (Extra.getString("IsSaved").equals("true")) {
                Image.setImageResource(Integer.valueOf(Extra.getString("Image")));
            } else {
                Glide.with(ProductViewActivity.this).load(getString(R.string.ImageLoader) + Extra.getString("Image")).into(Image);
            }

            ShopName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProductViewActivity.this, SearchActivity.class).putExtra("Name", ShopName.getText().toString()));
                    finish();
                }
            });

            ProductName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProductViewActivity.this, SearchActivity.class).putExtra("Name", ProductName.getText().toString()));
                    finish();
                }
            });

            Price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProductViewActivity.this, SearchActivity.class).putExtra("Name", Price.getText().toString().substring(4, Price.getText().toString().length())));
                    finish();
                }
            });

            if (EveryId.trim().equals(shareHolder.getId())) {
                Next.setVisibility(View.INVISIBLE);
            }

        }

        ToolBarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductViewActivity.this, MainActivity.class));
                finish();
            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shareHolder.getName().equals("")) {

                    new NameDialogBox(ProductViewActivity.this, getLayoutInflater()) {
                        @Override
                        public void DoNext(String Name) {
                            sendName(Name);
                        }
                    };

                } else {
                    Intent intent = new Intent(ProductViewActivity.this, MessageGoingActivity.class);
                    intent.putExtra("to_Name", EveryId);
                    intent.putExtra("NameMain",ShopName.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void sendName(final String name) {
        progressDialog.show();
        StringRequest sendName = new StringRequest(Request.Method.POST, getString(R.string.SetName), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shareHolder.SetName(name);
                progressDialog.dismiss();

                Intent intent = new Intent(ProductViewActivity.this, MessageGoingActivity.class);
                intent.putExtra("to_Name", EveryId);
                startActivity(intent);
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
        MySending.getInstance(ProductViewActivity.this).addToRequestQueue(sendName);

    }
}
