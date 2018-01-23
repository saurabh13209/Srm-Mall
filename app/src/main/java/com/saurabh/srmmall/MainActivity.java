package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;



public class MainActivity extends AppCompatActivity {

    LinearLayout MainList;
    FloatingActionButton Setting;
    ProgressDialog progressDialog;
    ShareHolder shareHolder;

    ImageButton SearchButton;
    ImageView MallIcon, PackedFilter, CookedFilter, StationFilter, ElectronicFilter, GamesFilter, DrugsFilter, QuestionFilter;

    EditText SearchBox;

    private ArrayList<String> Every_Id, PRODUCT_NAME, MOB, PRICE, SELLER_NAME, IMAGE, IsSaved, AMOUNT, Packed, cooked, station, electronic, movie, drugs, others;

    private AdView mAdView;

    @Override
    protected void onResume() {
        PRODUCT_NAME = new ArrayList<>();
        PRICE = new ArrayList<>();
        SELLER_NAME = new ArrayList<>();
        IMAGE = new ArrayList<>();
        IsSaved = new ArrayList<>();
        AMOUNT = new ArrayList<>();
        Every_Id = new ArrayList<>();

        Packed = new ArrayList<>();
        cooked = new ArrayList<>();
        station = new ArrayList<>();
        electronic = new ArrayList<>();
        movie = new ArrayList<>();
        drugs = new ArrayList<>();
        others = new ArrayList<>();
        MOB = new ArrayList<>();

        if (!shareHolder.getId().equals("")) {
            getInfo(getString(R.string.GetMarket), "");
        }


        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareHolder = new ShareHolder(MainActivity.this);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_MALE)
                .setIsDesignedForFamilies(false).build();
        MobileAds.initialize(this, "ca-app-pub-6626107194157938~6507686342");
        mAdView.loadAd(adRequest);

        SearchBox = findViewById(R.id.MainListSearchBo);
        SearchBox.setHintTextColor(getResources().getColor(R.color.White));
        SearchButton = findViewById(R.id.MainListSearchButto);

        MallIcon = findViewById(R.id.MallFilter);
        PackedFilter = findViewById(R.id.PackedFilter);
        CookedFilter = findViewById(R.id.CookedFilter);
        StationFilter = findViewById(R.id.StationFilter);
        ElectronicFilter = findViewById(R.id.ElectronicFilter);
        GamesFilter = findViewById(R.id.GamesFilter);
        DrugsFilter = findViewById(R.id.DrugsFilter);
        QuestionFilter = findViewById(R.id.QuestionFilter);

        SetFilterButtonCommand();

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class).putExtra("Name", SearchBox.getText().toString()));
            }
        });


        if (shareHolder.getName().equals("")) {

            progressDialog.show();

            final String id = Secure.getString(MainActivity.this.getContentResolver(), Secure.ANDROID_ID);
            if (!id.equals("")) {
                shareHolder.SetAndroidId(id);
                StringRequest sendId = new StringRequest(Request.Method.POST, getString(R.string.SetAndroidId), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            if (array.get(0).toString().equals("OLD")) {
                                if (array.length() == 7) {
                                    shareHolder.SetId(array.getString(1).toString());
                                    shareHolder.SetName(array.getString(2).toString());
                                    shareHolder.SetShopKeeper(array.getString(3).toString(), array.getString(5).toString(), array.getString(4).toString(), array.getString(6).toString());
                                    shareHolder.SetAndroidId(id);
                                    Toast.makeText(MainActivity.this, "Welcome back, " + array.getString(2).toString(), Toast.LENGTH_SHORT).show();
                                    getInfo(getString(R.string.GetMarket), "");
                                } else {
                                    shareHolder.SetId(array.getString(1).toString());
                                    shareHolder.SetName(array.getString(2).toString());
                                    shareHolder.SetAndroidId(id);
                                    getInfo(getString(R.string.GetMarket), "");
                                }
                            } else {
                                shareHolder.SetId(array.getString(1).toString());
                                shareHolder.SetAndroidId(id);
                                getInfo(getString(R.string.GetMarket), "");
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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map map = new HashMap();
                        map.put("id", id);
                        return map;
                    }
                };
                MySending.getInstance(MainActivity.this).addToRequestQueue(sendId);
            }
        }

        MainList = findViewById(R.id.MainList);
        Setting = findViewById(R.id.MainSetting);

        SettingClickFunction();
    }

    private void SetFilterButtonCommand() {


        MallIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.GetMarket), "");
            }
        });

        PackedFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.SearchMain), "Packed");
            }
        });

        CookedFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.SearchMain), "Cooked");
            }
        });

        StationFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.SearchMain), "Stationary");
            }
        });

        ElectronicFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.SearchMain), "Electronic");
            }
        });

        GamesFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.SearchMain), "movie");
            }
        });

        DrugsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.SearchMain), "Drugs");
            }
        });
        QuestionFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo(getString(R.string.SearchMain), "others");
            }
        });
    }

    private void SettingClickFunction() {
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    public void getInfo(final String URL, final String filter) {
        progressDialog.show();
        PRODUCT_NAME = new ArrayList<>();
        PRICE = new ArrayList<>();
        SELLER_NAME = new ArrayList<>();
        IMAGE = new ArrayList<>();
        IsSaved = new ArrayList<>();
        AMOUNT = new ArrayList<>();
        Every_Id = new ArrayList<>();
        MOB = new ArrayList<>();

        Packed = new ArrayList<>();
        cooked = new ArrayList<>();
        station = new ArrayList<>();
        electronic = new ArrayList<>();
        movie = new ArrayList<>();
        drugs = new ArrayList<>();
        others = new ArrayList<>();
        MOB = new ArrayList<>();
        StringRequest getInfo = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if (array.length() == 0) {
                        Toast.makeText(MainActivity.this, "No Product...", Toast.LENGTH_SHORT).show();
                        ListViewAddView();
                        progressDialog.dismiss();
                        return;
                    }
                    if (array.get(0).toString().equals("No")) {
                        progressDialog.dismiss();
                        new TextDialogBox(MainActivity.this, getLayoutInflater()) {
                            @Override
                            void AfterClick() {
                                finish();
                            }

                            @Override
                            String setHead1() {
                                return "Your have been blocked by developer";
                            }

                            @Override
                            String setHead2() {
                                return "Contact developer by ";
                            }

                            @Override
                            String setHead3() {
                                return "cool.developer13209@gmail.com";
                            }
                        };

                    } else {
                        if (array.getString(array.length() - 1).equals("true")) {
                            new TextDialogBox(MainActivity.this, getLayoutInflater()) {
                                @Override
                                void AfterClick() {
                                    startActivity(new Intent(MainActivity.this, MessageActivity.class));
                                }

                                @Override
                                String setHead1() {
                                    return "New Message";
                                }

                                @Override
                                String setHead2() {
                                    return "You have some new message ";
                                }

                                @Override
                                String setHead3() {
                                    return "Message is present in Srm Mall personal Messaging system";
                                }
                            };
                        }
                        int END;
                        if (URL.equals(getString(R.string.GetMarket))) {
                            END = array.length() - 1;
                        } else {
                            END = array.length();
                        }
                        for (int i = 0; i < END; i++) {
                            JSONArray inner = array.getJSONArray(i);

                            SELLER_NAME.add(inner.getString(0));
                            PRODUCT_NAME.add(inner.getString(1));
                            IMAGE.add(String.valueOf(inner.get(2)));
                            IsSaved.add(inner.getString(3));
                            AMOUNT.add(inner.getString(4));
                            PRICE.add(inner.getString(5));
                            Every_Id.add(inner.getString(6));
                            Packed.add(inner.getString(7));
                            cooked.add(inner.getString(8));
                            station.add(inner.getString(9));
                            electronic.add(inner.getString(10));
                            movie.add(inner.getString(11));
                            drugs.add(inner.getString(12));
                            MOB.add(inner.getString(13));
                            others.add(inner.getString(14));

                        }
                        progressDialog.dismiss();
                        ListViewAddView();
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
                map.put("id", shareHolder.getId());
                map.put("Key", filter);
                return map;
            }
        };

        MySending.getInstance(MainActivity.this).addToRequestQueue(getInfo);
    }

    private void ListViewAddView() {
        MainList.removeAllViews();
        View view;
        for (int i = 0; i < SELLER_NAME.size(); i++) {
            view = getLayoutInflater().inflate(R.layout.main_list_each, null);
            ImageButton delete = view.findViewById(R.id.MainListDelete);
            ImageView ProductImage = view.findViewById(R.id.ProductImageMain);
            TextView GoodName = view.findViewById(R.id.GoodsName);
            TextView ShopName = view.findViewById(R.id.MainShopName);
            TextView GoodPrice = view.findViewById(R.id.MainProductPrice);
            TextView GoodQnty = view.findViewById(R.id.Quantity);

            delete.setVisibility(View.INVISIBLE);
            GoodName.setText(PRODUCT_NAME.get(i));
            ShopName.setText(SELLER_NAME.get(i));
            GoodPrice.setText("Rs." + PRICE.get(i));
            GoodQnty.setText("Qty:" + AMOUNT.get(i));


            if (IsSaved.get(i).equals("true")) {
                try {
                    ProductImage.setImageResource(Integer.valueOf(IMAGE.get(i)));
                } catch (Exception e) {
                }

            } else {
                Glide.with(MainActivity.this).load(getString(R.string.ImageLoader) + IMAGE.get(i)).into(ProductImage);
            }
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ProductViewActivity.class);
                    intent.putExtra("To_Name", Every_Id.get(finalI).toString());
                    intent.putExtra("ProductName", PRODUCT_NAME.get(finalI));
                    intent.putExtra("ShopName", SELLER_NAME.get(finalI));
                    intent.putExtra("Price", "Rs. " + PRICE.get(finalI));
                    intent.putExtra("Amount", "Only " + AMOUNT.get(finalI) + " Left");
                    intent.putExtra("Image", IMAGE.get(finalI));
                    intent.putExtra("packed", Packed.get(finalI));
                    intent.putExtra("cooked", cooked.get(finalI));
                    intent.putExtra("station", station.get(finalI));
                    intent.putExtra("electronic", electronic.get(finalI));
                    intent.putExtra("movie", movie.get(finalI));
                    intent.putExtra("drugs", drugs.get(finalI));
                    intent.putExtra("others", others.get(finalI));
                    intent.putExtra("IsSaved", IsSaved.get(finalI));
                    intent.putExtra("Mob", MOB.get(finalI));
                    startActivity(intent);
                }
            });

            MainList.addView(view);

        }
        progressDialog.dismiss();
    }


}
