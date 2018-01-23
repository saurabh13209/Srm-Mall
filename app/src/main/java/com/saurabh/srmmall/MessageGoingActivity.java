package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MessageGoingActivity extends AppCompatActivity {
    LinearLayout layout;
    ImageButton Send;
    TextView UserName;
    EditText SendMessageText;
    ShareHolder shareHolder;
    String To_Name = null;
    ScrollView scrollView;

    boolean willSend = true;

    int a = Calendar.getInstance().getTime().getSeconds(), counter = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_going);

        progressDialog = new ProgressDialog(MessageGoingActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        progressDialog.show();

        UserName = findViewById(R.id.UserName);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            To_Name = bundle.getString("to_Name");
            UserName.setText(bundle.getString("NameMain"));
        }

        layout = findViewById(R.id.GoingLayout);
        new StartBackground().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Send = findViewById(R.id.SendMessage);
        scrollView = findViewById(R.id.MessageScroll);
        SendMessageText = findViewById(R.id.SendMessageMain);
        shareHolder = new ShareHolder(MessageGoingActivity.this);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });

    }

    private void SendMessage() {
        View view = getLayoutInflater().inflate(R.layout.outgoing_layout, null);
        final TextView messageText = view.findViewById(R.id.OutgoingMessage);
        messageText.setText(SendMessageText.getText().toString());
        layout.addView(view);
        scrollView.scrollTo(0,layout.getBottom());
        final String Main = SendMessageText.getText().toString();
        SendMessageText.setText("");
        MediaPlayer mediaPlayer = MediaPlayer.create(MessageGoingActivity.this, R.raw.sent_message);
        mediaPlayer.start();
        StringRequest sendMessage = new StringRequest(Request.Method.POST, getString(R.string.SendMessage), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("from_name", shareHolder.getId());
                map.put("to_name", To_Name);
                map.put("text", Main);
                return map;
            }
        };
        if (Main.equals("")){
            return;
        }
        MySending.getInstance(MessageGoingActivity.this).addToRequestQueue(sendMessage);
    }

    @Override
    protected void onDestroy() {
        willSend = false;
        super.onDestroy();
    }

    private void startSending() {

        while (willSend) {
            int n = Calendar.getInstance().getTime().getSeconds();
            if (n - a == 1) {
                counter++;
                a = n;

            } else if (a - n == 1) {
                counter++;
                a = n;
            } else {
                a = n;
            }

            if (counter == 10) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainRun();
                    }
                });
                counter = 0;
            }
        }

    }

    private void MainRun() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.GetAllMessage), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    layout.removeAllViews();
                    JSONArray array = new JSONArray(response);
                    for (int i=0 ; i<array.length() ; i++){
                        if (array.getJSONArray(i).get(0).toString().equals(shareHolder.getId())){
                            View view = getLayoutInflater().inflate(R.layout.outgoing_layout , null);
                            TextView textView = view.findViewById(R.id.OutgoingMessage);
                            textView.setText(array.getJSONArray(i).get(2).toString());
                            layout.addView(view);
                        }else{
                            View view = getLayoutInflater().inflate(R.layout.incoming_layout , null);
                            TextView textView = view.findViewById(R.id.IncomingMessage);
                            textView.setText(array.getJSONArray(i).get(2).toString());
                            layout.addView(view);
                        }
                    }
                    progressDialog.dismiss();
                    scrollView.scrollTo(0,layout.getBottom());
                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("from_name", To_Name);
                map.put("to_name", shareHolder.getId());
                return map;
            }
        };
        MySending.getInstance(MessageGoingActivity.this).addToRequestQueue(stringRequest);
    }


    public class StartBackground extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            startSending();
            return null;
        }
    }
}
