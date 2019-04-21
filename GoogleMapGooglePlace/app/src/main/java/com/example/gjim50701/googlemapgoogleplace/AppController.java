package com.example.gjim50701.googlemapgoogleplace;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import static com.android.volley.toolbox.Volley.newRequestQueue;

/**
 * Created by gjim50701 on 2018/8/8.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private String User_Id;
    private String User_Name;
    private String User_Password;
    private String User_Image;

    public void setUserId(String user_Id) {
        this.User_Id = user_Id;
    }
    public String getUserId() {
        return User_Id;
    }
    public void setUserName(String user_Name) {
        this.User_Name = user_Name;
    }
    public String getUserName() {
        return User_Name;
    }
    public void setUserPass(String user_Pass) {
        this.User_Password = user_Pass;
    }
    public String getUserPass() {
        return User_Password;
    }
    public void setUserImage(String user_Image) {
        this.User_Image = user_Image;
    }
    public String getUserImage() {
        return User_Image;
    }
}