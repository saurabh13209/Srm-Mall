package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    EditText Name, hostel, floor , mob;
    ArrayList<String> Hostel = new ArrayList<>();
    Button button;
    ProgressDialog progressDialog;
    ShareHolder shareHolder;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        shareHolder = new ShareHolder(CreateAccountActivity.this);

        progressDialog = new ProgressDialog(CreateAccountActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        Hostel.add("Paari");
        Hostel.add("paari");
        Hostel.add("Pari");
        Hostel.add("pari");
        Hostel.add("parri");
        Hostel.add("Parri");
        Hostel.add("Kaari");
        Hostel.add("kaari");
        Hostel.add("Kari");
        Hostel.add("kari");
        Hostel.add("Ori");
        Hostel.add("ori");
        Hostel.add("Oori");
        Hostel.add("oori");
        Hostel.add("Andhyaman");
        Hostel.add("andhyaman");
        Hostel.add("Adhyamaan");
        Hostel.add("adhyamaan");
        Hostel.add("Adhyaman");
        Hostel.add("adhyaman");

        Name = findViewById(R.id.CreateName);
        hostel = findViewById(R.id.CreateRoomNumber);
        floor = findViewById(R.id.CreateFloor);
        button = findViewById(R.id.CreateButton);
        mob = findViewById(R.id.CreateMobileNuber);

        new EditBoxChange(Name, CreateAccountActivity.this);
        new EditBoxChange(hostel, CreateAccountActivity.this);
        new EditBoxChange(floor, CreateAccountActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!Name.equals("")) && (!hostel.equals( "")) && (!floor.equals(""))) {
                    if ((Hostel.contains(hostel.getText().toString())) && (Integer.valueOf(floor.getText().toString()) < 9) && (mob.getText().toString().equals("")||(Integer.valueOf(mob.getText().toString().length()) == 10)) ) {
                        if (isEdit) {
                            SendData(getString(R.string.EditProfile));
                        } else {
                            SendData(getString(R.string.CreateShopKeeper));
                        }
                    }else{
                        Toast.makeText(CreateAccountActivity.this, "Please Enter correct Information", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if (!shareHolder.ShopName().equals("")){
            Name.setText(shareHolder.ShopName());
            hostel.setText(shareHolder.getHostel());
            floor.setText(shareHolder.getFloor());
            mob.setText(shareHolder.GetMob());
        }

        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            isEdit = true;
            TextView Head = findViewById(R.id.CreateShopTextMain);
            Head.setText("Edit Profile");
        }
    }

    private void SendData(String URL) {
        progressDialog.show();
        StringRequest sendData = new StringRequest(Request.Method.POST,URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shareHolder.SetShopKeeper(Name.getText().toString() , floor.getText().toString() , hostel.getText().toString() , mob.getText().toString());
                progressDialog.dismiss();
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
                map.put("Id",shareHolder.getId());
                map.put("Name",shareHolder.getName());
                map.put("shop_name",Name.getText().toString());
                map.put("hostel",hostel.getText().toString());
                map.put("floor",floor.getText().toString());
                map.put("Mob",mob.getText().toString());
                return map;
            }
        };

        MySending.getInstance(CreateAccountActivity.this).addToRequestQueue(sendData);

    }
}
