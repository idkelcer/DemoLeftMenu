package com.example.kelvin.demomenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ClubRequestManager {

    private static ClubRequestManager mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private ClubRequestManager(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized ClubRequestManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ClubRequestManager(context);
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

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void performJsonRequest(int method, final String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        Cache cache = this.getRequestQueue().getCache();
        Cache.Entry entry = cache.get(method + ":" + url);

        if (!ConnectivityUtils.hasInternetAvailable(this.mCtx) && entry != null) {
            try {
                if (listener != null) {
                    String jsonString = new String(entry.data, HttpHeaderParser.parseCharset(entry.responseHeaders, "UTF-8"));
                    listener.onResponse(new JSONObject(jsonString));
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                if (errorListener != null) {
                    errorListener.onErrorResponse(new ParseError(e));
                }
            }
        } else {
            JsonObjectRequest request = new JsonObjectRequest(method, url, new JSONObject(), listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Ecclubsup-api-key", Constants.apiKey);
                    return params;
                }
            };
            request.setShouldCache(true);
            this.addToRequestQueue(request);
        }
    }
}
