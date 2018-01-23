package com.saurabh.srmmall;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class MySending {


    private static Context ctxt;
    private RequestQueue requestQueue;
    private static MySending mySending;

    private MySending(Context context) {
        ctxt = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctxt.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySending getInstance(Context context) {
        if (mySending == null) {
            mySending = new MySending(context);
        }
        return mySending;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
