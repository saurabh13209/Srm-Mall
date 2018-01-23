package com.saurabh.srmmall;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProductActivity extends AppCompatActivity {

    static boolean isImageSaved = true;
    EditText ProductName, ProductPrice, Amount;
    Button next;
    Bitmap photo = null;
    ImageView imageView, Delete;
    ImageButton Right, Left;
    ShareHolder shareHolder;
    ProgressDialog progressDialog;
    boolean isEdit;
    String ProductNumber = new String();
    int POSITION = 0;

    InterstitialAd mInterstitialAd;
    CheckBox PackedFood, CookedFood, Station, Electronic, Games, Drugs , Others;

    ArrayList<Integer> ImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        progressDialog = new ProgressDialog(ProductActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        shareHolder = new ShareHolder(ProductActivity.this);
        imageView = findViewById(R.id.ProductImage);
        ProductName = findViewById(R.id.ProductName);
        ProductPrice = findViewById(R.id.ProductPrice);
        Amount = findViewById(R.id.ProductNumber);
        next = findViewById(R.id.ProductButton);
        Delete = findViewById(R.id.ProductDeleteImage);

        PackedFood = findViewById(R.id.ProductPacked);
        CookedFood = findViewById(R.id.ProductCooked);
        Station = findViewById(R.id.ProductStation);
        Electronic = findViewById(R.id.ProductElectronic);
        Games = findViewById(R.id.ProductMovie);
        Drugs = findViewById(R.id.ProductDrug);
        Others = findViewById(R.id.ProductOther);
        Right = findViewById(R.id.ImageRight);
        Left = findViewById(R.id.ImageLeft);

        new EditBoxChange(ProductName, ProductActivity.this);
        new EditBoxChange(ProductPrice, ProductActivity.this);
        new EditBoxChange(Amount, ProductActivity.this);


        ProductName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!view.isFocused()) {
                    if (isImageSaved) {
                        imageView.setTag(R.drawable.camera);
                        ImageList = new ProductPlacement(imageView, ProductName.getText().toString(), ProductActivity.this).ImageList;
                        Right.setVisibility(View.INVISIBLE);
                        Left.setVisibility(View.INVISIBLE);
                        if (ImageList.size() == 0) {
                            return;
                        }
                        Right.setVisibility(View.VISIBLE);
                        Left.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImageSaved = true;
                Delete.setVisibility(View.INVISIBLE);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.camera));
                imageView.setTag(R.drawable.camera);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    sendData(getString(R.string.EditProduct));
                } else {
                    if (!(ProductName.getText().equals("")) && !(ProductPrice.getText().equals("")) && !(Amount.getText().equals(""))) {
                        sendData(getString(R.string.ProductLaunch));
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ProductActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    ChangeImage();
                } else {
                    ActivityCompat.requestPermissions(ProductActivity.this, new String[]{Manifest.permission.CAMERA}, 1001);
                }

            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            if (extra.getString("isEdit").equals("true")) {
                isEdit = true;
            }
            ProductNumber = extra.getString("ProductNumber");
            ProductName.setText(extra.getString("ProductName"));
            ProductPrice.setText(extra.getString("Price"));
            Amount.setText(extra.getString("Amount"));

            if (extra.getString("packed").equals("true")) {
                PackedFood.setChecked(true);
            }
            if (extra.getString("cooked").equals("true")) {
                CookedFood.setChecked(true);
            }
            if (extra.getString("station").equals("true")) {
                Station.setChecked(true);
            }
            if (extra.getString("electronic").equals("true")) {
                Electronic.setChecked(true);
            }
            if (extra.getString("movie").equals("true")) {
                Games.setChecked(true);
            }
            if (extra.getString("drugs").equals("true")) {
                Drugs.setChecked(true);
            }
            if (extra.getString("others").equals("true")) {
                Others.setChecked(true);
            }

            if (extra.getString("IsSaved").equals("true")) {
                imageView.setImageResource(Integer.valueOf(extra.getString("Image")));
                imageView.setTag(Integer.valueOf(extra.getString("Image")));
                isImageSaved = true;
            } else {
                Glide.with(ProductActivity.this).load(getString(R.string.ImageLoader) + extra.getString("Image")).into(imageView);
                Delete.setVisibility(View.VISIBLE);
                isImageSaved = false;
            }

        }


        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POSITION++;
                if (POSITION >= ImageList.size()) {
                    POSITION = ImageList.size() - 1;
                }
                imageView.setImageResource(ImageList.get(POSITION));
                imageView.setTag(ImageList.get(POSITION));
            }
        });

        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POSITION--;
                if (POSITION < 0) {
                    POSITION = 0;
                }
                imageView.setImageResource(ImageList.get(POSITION));
                imageView.setTag(ImageList.get(POSITION));
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ChangeImage();
            } else {
                ActivityCompat.requestPermissions(ProductActivity.this, new String[]{Manifest.permission.CAMERA}, 1001);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void ChangeImage() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 102);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 102 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
            isImageSaved = false;
            Delete.setVisibility(View.VISIBLE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String isCheckedMain(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            return "true";
        } else {
            return "false";
        }
    }

    @Override
    public void onBackPressed() {
        isImageSaved = true;
        super.onBackPressed();
    }

    private void sendData(String URL) {
        progressDialog.show();
        StringRequest sendProduct;
        if (isImageSaved) {
            sendProduct = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    finish();
                    MobileAds.initialize(ProductActivity.this,
                            "ca-app-pub-6626107194157938~6507686342");

                    mInterstitialAd = new InterstitialAd(ProductActivity.this);
                    mInterstitialAd.setAdUnitId("ca-app-pub-6626107194157938/3853672659");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(ProductActivity.this, "Something went wrong ... Please try again..", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.d("ERROR", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("every_id", shareHolder.getId());
                    map.put("Name", shareHolder.getName());
                    map.put("shop_name", shareHolder.ShopName());
                    map.put("Mob", shareHolder.GetMob());
                    map.put("Product_name", ProductName.getText().toString());
                    map.put("isSaved", "true");
                    map.put("amount", Amount.getText().toString());
                    map.put("price", ProductPrice.getText().toString());
                    map.put("image_data", imageView.getTag().toString());
                    map.put("ProductNumber", ProductNumber.toString().trim());
                    map.put("packed", isCheckedMain(PackedFood));
                    map.put("cooked", isCheckedMain(CookedFood));
                    map.put("station", isCheckedMain(Station));
                    map.put("electronic", isCheckedMain(Electronic));
                    map.put("movie", isCheckedMain(Games));
                    map.put("drug", isCheckedMain(Drugs));
                    map.put("others",isCheckedMain(Others));

                    return map;
                }
            };
        } else {
            final int Num = new Random().nextInt(10000000);
            sendProduct = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();
                    Toast.makeText(ProductActivity.this, "Something went wrong ... Please try again..", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.d("ERROR", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("every_id", shareHolder.getId());
                    map.put("Name", shareHolder.getName());
                    map.put("shop_name", shareHolder.ShopName());
                    map.put("Mob", shareHolder.GetMob());
                    map.put("Product_name", ProductName.getText().toString());
                    map.put("isSaved", "false");
                    map.put("image_data", ImageToString());
                    map.put("path", "UploadImage/" + Num);
                    map.put("amount", Amount.getText().toString());
                    map.put("price", ProductPrice.getText().toString());
                    map.put("ProductNumber", ProductNumber.toString().trim());
                    map.put("packed", isCheckedMain(PackedFood));
                    map.put("cooked", isCheckedMain(CookedFood));
                    map.put("station", isCheckedMain(Station));
                    map.put("electronic", isCheckedMain(Electronic));
                    map.put("movie", isCheckedMain(Games));
                    map.put("drug", isCheckedMain(Drugs));
                    map.put("others",isCheckedMain(Others));

                    return map;
                }
            };
        }
        MySending.getInstance(ProductActivity.this).addToRequestQueue(sendProduct);
    }

    private String ImageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            photo = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } catch (Exception e) {
           photo = ((GlideBitmapDrawable) imageView.getDrawable().getCurrent()).getBitmap();
        }
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(array, Base64.DEFAULT);
    }
}
