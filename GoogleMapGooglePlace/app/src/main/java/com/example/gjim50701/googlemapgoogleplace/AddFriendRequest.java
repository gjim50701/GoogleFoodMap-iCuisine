package com.example.gjim50701.googlemapgoogleplace;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gjim50701 on 2018/8/31.
 */

public class AddFriendRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://163.13.201.88/php/Friend_Insert.php";
    private Map<String,String> params;

    public AddFriendRequest(String User_Id, String Friend_Id ,Response.Listener<String> responseListener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,responseListener,null);
        params=new HashMap<>();
        params.put("User_Id",User_Id);
        params.put("Friend_Id",Friend_Id);

    }
    public Map<String, String> getParams() {
        return params;
    }
}