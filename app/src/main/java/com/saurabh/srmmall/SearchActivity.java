package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    EditText SearchBox;
    ImageButton imageButton;
    ProgressDialog progressDialog;
    ListView MainList;
    private ArrayList<String> Every_id , PRODUCT_NAME, PRICE, SELLER_NAME , Mob , IMAGE, IsSaved, AMOUNT  , Packed , cooked ,  station , electronic , movie , drugs , others;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extra = getIntent().getExtras();

        progressDialog = new ProgressDialog(SearchActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        Every_id = new ArrayList<>();
        PRODUCT_NAME = new ArrayList<>();
        PRICE = new ArrayList<>();
        SELLER_NAME = new ArrayList<>();
        IMAGE = new ArrayList<>();
        IsSaved = new ArrayList<>();
        AMOUNT = new ArrayList<>();
        Mob = new ArrayList<>();

        Packed =  new ArrayList<>();
        cooked =  new ArrayList<>();
        station =  new ArrayList<>();
        electronic =  new ArrayList<>();
        movie =  new ArrayList<>();
        drugs =  new ArrayList<>();

        imageButton = findViewById(R.id.MainListSearchButton);
        SearchBox = findViewById(R.id.MainListSearchBox);
        MainList = findViewById(R.id.SearchMainList);

        mAdView = findViewById(R.id.adView);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE)
                .setIsDesignedForFamilies(false).build();
        MobileAds.initialize(this, "ca-app-pub-6626107194157938~6507686342");
        mAdView.loadAd(adRequest);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchMainFunc(SearchBox.getText().toString());
            }
        });


        if (extra != null) {
            SearchBox.setText(extra.getString("Name"));
            SearchMainFunc(extra.getString("Name"));
        }


    }

    private void SearchMainFunc(final String name) {
        progressDialog.show();
        StringRequest searchMain = new StringRequest(Request.Method.POST, getString(R.string.SearchMain), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Every_id = new ArrayList<>();
                    PRODUCT_NAME = new ArrayList<>();
                    PRICE = new ArrayList<>();
                    SELLER_NAME = new ArrayList<>();
                    IMAGE = new ArrayList<>();
                    IsSaved = new ArrayList<>();
                    AMOUNT = new ArrayList<>();
                    Mob = new ArrayList<>();

                    Packed =  new ArrayList<>();
                    cooked =  new ArrayList<>();
                    station =  new ArrayList<>();
                    electronic =  new ArrayList<>();
                    movie =  new ArrayList<>();
                    drugs =  new ArrayList<>();
                    others =  new ArrayList<>();

                    JSONArray array = new JSONArray(response);
                    if (array.length()==0){
                        Toast.makeText(SearchActivity.this, "No Search found..", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    for (int i = 0; i < array.length(); i++) {
                        JSONArray inner = array.getJSONArray(i);

                        SELLER_NAME.add(inner.getString(0));
                        PRODUCT_NAME.add(inner.getString(1));
                        IMAGE.add(String.valueOf(inner.get(2)));
                        IsSaved.add(inner.getString(3));
                        AMOUNT.add(inner.getString(4));
                        PRICE.add(inner.getString(5));
                        Every_id.add(inner.getString(6));
                        Packed.add(inner.getString(7));
                        cooked.add(inner.getString(8));
                        station.add(inner.getString(9));
                        electronic.add(inner.getString(10));
                        movie.add(inner.getString(11));
                        drugs.add(inner.getString(12));
                        Mob.add(inner.getString(13));
                        others.add(inner.getString(15));
                    }

                    MainList.setAdapter(new CustomAdapter());
                    progressDialog.dismiss();

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
                map.put("Key", name);
                return map;
            }
        };

        MySending.getInstance(SearchActivity.this).addToRequestQueue(searchMain);
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return PRODUCT_NAME.size();
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
            view = getLayoutInflater().inflate(R.layout.main_list_each, null);

            ImageButton delete = view.findViewById(R.id.MainListDelete);
            delete.setVisibility(View.INVISIBLE);
            ImageView ProductImage = view.findViewById(R.id.ProductImageMain);
            TextView GoodName = view.findViewById(R.id.GoodsName);
            TextView ShopName = view.findViewById(R.id.MainShopName);
            TextView GoodPrice = view.findViewById(R.id.MainProductPrice);

            GoodName.setText(PRODUCT_NAME.get(i));
            ShopName.setText(SELLER_NAME.get(i));
            GoodPrice.setText("Rs." + PRICE.get(i));
            if (IsSaved.get(i).equals("true")) {
                ProductImage.setImageResource(Integer.valueOf(IMAGE.get(i)));
            } else {
                Glide.with(SearchActivity.this).load(getString(R.string.ImageLoader) + IMAGE.get(i)).into(ProductImage);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchActivity.this, ProductViewActivity.class);
                    intent.putExtra("To_Name",Every_id.get(i));
                    intent.putExtra("ProductName", PRODUCT_NAME.get(i));
                    intent.putExtra("ShopName", SELLER_NAME.get(i));
                    intent.putExtra("Price", "Rs. " + PRICE.get(i));
                    intent.putExtra("Amount", "Only " + AMOUNT.get(i) + " Left");
                    intent.putExtra("Negotiable", "Yes");
                    intent.putExtra("IsSaved",IsSaved.get(i));
                    intent.putExtra("Image", IMAGE.get(i));
                    intent.putExtra("packed",Packed.get(i));
                    intent.putExtra("cooked",cooked.get(i));
                    intent.putExtra("station",station.get(i));
                    intent.putExtra("electronic",electronic.get(i));
                    intent.putExtra("movie",movie.get(i));
                    intent.putExtra("drugs",drugs.get(i));
                    intent.putExtra("Mob",Mob.get(i));
                    intent.putExtra("others",others.get(i));
                    startActivity(intent);
                    finish();
                }
            });
            return view;
        }
    }
}
