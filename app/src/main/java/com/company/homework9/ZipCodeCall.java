package com.company.homework9;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ZipCodeCall {
    private static ZipCodeCall mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public ZipCodeCall(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ZipCodeCall getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ZipCodeCall(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static void make(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "http://searchproducts.us-east-2.elasticbeanstalk.com/getZipCode?id=" + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        ZipCodeCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
