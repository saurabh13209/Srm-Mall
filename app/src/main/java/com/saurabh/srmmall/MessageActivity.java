package com.saurabh.srmmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.IDN;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    ShareHolder shareHolder;
    ArrayList Name, newList ;
    Map IdName = new HashMap();
    ArrayList<ArrayList> Old;
    ListView List;
    int a = Calendar.getInstance().getTime().getSeconds(), counter = 0;
    boolean isSending = true;
    ProgressDialog progressDialog;

    @Override
    protected void onDestroy() {
        isSending = false;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        progressDialog = new ProgressDialog(MessageActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        shareHolder = new ShareHolder(MessageActivity.this);
        Name = new ArrayList();
        Old = new ArrayList();
        newList = new ArrayList();
        Old = shareHolder.getMessageList();
        for (int i = 0; i < Old.size(); i++) {
            if (!Name.contains(Old.get(i).get(0))) {
                Name.add(Old.get(i).get(0));
            }
        }
        List = findViewById(R.id.MessageList);
        List.setAdapter(new CustomAdapter());
        progressDialog.show();
        Work work = new Work();
        work.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void getMessage() {
        while (true) {
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

            if (counter == 5) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSending){
                            SendRequest();
                        }
                    }
                });
                counter = 0;
            }
        }
    }

    private void SendRequest() {
        StringRequest getMessage = new StringRequest(Request.Method.POST, getString(R.string.GetMessage), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    Log.d("Main", response.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONArray inner = array.getJSONArray(i);
                        int res =0 ;
                        for (int j = 0; j < Old.size(); j++) {
                            IdName.put(inner.getString(0) , inner.getString(2));
                            if (Old.get(j).contains(inner.getString(1)) && Old.get(j).contains(inner.getString(0))) {
                                res=1;
                            }
                        }
                        if (res==0){
                            newList.add(inner.getString(0));
                            shareHolder.insertMessageList(inner.getString(0), inner.getString(1));
                        }
                        if (!Name.contains(inner.getString(0))){
                            Name.add(inner.getString(0));
                        }
                    }
                    List.setAdapter(new CustomAdapter());
                    progressDialog.dismiss();
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Main", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("id", shareHolder.getId());
                return map;
            }
        };
        MySending.getInstance(MessageActivity.this).addToRequestQueue(getMessage);
    }

    public class Work extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getMessage();
            return null;
        }
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Name.size();
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
            view = getLayoutInflater().inflate(R.layout.message_eachrow, null);
            TextView NameView = view.findViewById(R.id.MessageLayoutName);
            ImageView cir = view.findViewById(R.id.MessageLayout);
            NameView.setText(Name.get(i).toString());
            if (!newList.contains(Name.get(i).toString())) {
                cir.setVisibility(View.INVISIBLE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        startActivity(new Intent(MessageActivity.this , MessageGoingActivity.class)
                                .putExtra("to_Name" , IdName.get(Name.get(i)).toString())
                                .putExtra("NameMain",Name.get(i).toString()));

                    }catch (Exception e){
                        startActivity(new Intent(MessageActivity.this , MainActivity.class));
                        Toast.makeText(MessageActivity.this, "Sorry try again..", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            });
            return view;
        }
    }
}
