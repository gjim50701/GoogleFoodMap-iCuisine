package com.example.gjim50701.googlemapgoogleplace;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gjim50701 on 2018/8/26.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME="info";
    private SharedPrefManager(Context context){
        mCtx = context;
    }
    public static synchronized SharedPrefManager getmInstance(Context context){
        if(mInstance==null){
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}