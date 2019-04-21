package com.example.gjim50701.googlemapgoogleplace;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gjim50701 on 2018/8/8.
 */

public class NewMarkerRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://192.168.1.108/php/Map_Insert2.php";
    private Map<String,String> params;

    public NewMarkerRequest(Double Lat, Double Lon, String Restaurant_Name, String Restaurant_Phone, Double Star,String User_Id,Response.Listener<String> responseListener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,responseListener,null);
        params=new HashMap<>();
        params.put("Lat",Lat+"");
        params.put("Lon",Lon+"");
        params.put("Restaurant_Name",Restaurant_Name);
        params.put("Restaurant_Phone",Restaurant_Phone);
        params.put("Star",Star+"");
        params.put("User_Id",User_Id);

    }
    public Map<String, String> getParams() {
        return params;
    }
}