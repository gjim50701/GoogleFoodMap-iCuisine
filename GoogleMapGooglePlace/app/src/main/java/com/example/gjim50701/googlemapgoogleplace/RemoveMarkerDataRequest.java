package com.example.gjim50701.googlemapgoogleplace;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gjim50701 on 2018/8/8.
 */

public class RemoveMarkerDataRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://192.168.1.108/php/Map_Delete.php";
    private Map<String,String> params;

    public RemoveMarkerDataRequest(Double lat,Double lon,String User_Id,Response.Listener<String> responseListener)
    {
        super(Request.Method.POST,REGISTER_REQUEST_URL,responseListener,null);
        params=new HashMap<>();
        params.put("lat",lat+"");
        params.put("lon",lon+"");
        params.put("User_Id",User_Id);
    }
    public Map<String, String> getParams() {
        return params;
    }
}