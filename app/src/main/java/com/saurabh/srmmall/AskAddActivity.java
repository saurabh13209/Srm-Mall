package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AskAddActivity extends AppCompatActivity {
    EditText Name , Mob , Text;
    ShareHolder shareHolder;
    Button next;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_add);

        shareHolder = new ShareHolder(AskAddActivity.this);

        progressDialog = new ProgressDialog(AskAddActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        Name = findViewById(R.id.AddName);
        Mob = findViewById(R.id.AddMob);
        Text = findViewById(R.id.AddText);

        Name.setText(shareHolder.getName().toString());
        Mob.setText(shareHolder.GetMob().toString());

        next = findViewById(R.id.AskNext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendRequset();
            }
        });
    }

    private void SendRequset() {
        progressDialog.show();
        StringRequest sendRequest =  new StringRequest(Request.Method.POST, getString(R.string.SendRequest), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(AskAddActivity.this, "Requested Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("Name",Name.getText().toString());
                map.put("Mob",Mob.getText().toString());
                map.put("Text",Text.getText().toString().replace("\n","$"));
                map.put("every_id",shareHolder.getId()  );
                return map;
            }
        };

        MySending.getInstance(AskAddActivity.this).addToRequestQueue(sendRequest);
    }
}
