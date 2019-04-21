package com.example.gjim50701.googlemapgoogleplace;

/**
 * Created by gjim50701 on 2018/8/25.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL="http://192.168.1.108/php/Login.php";
    private Map<String,String> params;
    public LoginRequest(String username,String password, Response.Listener<String>Listener)
    {
        super(Method.POST,LOGIN_REQUEST_URL,Listener,null);
        params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);

    }
    public Map<String, String> getParams() {
        return params;
    }
}
