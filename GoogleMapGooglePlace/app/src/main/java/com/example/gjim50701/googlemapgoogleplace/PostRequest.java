package com.example.gjim50701.googlemapgoogleplace;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gjim50701 on 2018/9/2.
 */

public class PostRequest extends StringRequest {
    private static final String
            REGISTER_REQUEST_URL="http://192.168.1.108/php/Post_Insert.php";
    private Map<String,String> params; public PostRequest(String user_id,Double lat,Double lon,String pic_val,String desc_val,Double star , Response.Listener<String> responseListener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,responseListener,null);
        params=new HashMap<>();
        params.put("User_Id",user_id);
        params.put("Node_Lat",lat+"");
        params.put("Node_Lon",lon+"");
        params.put("Post_pic",pic_val);
        params.put("Post_desc",desc_val);
        params.put("Post_star",star+"");
    }
    public Map<String, String> getParams() {
        return params;
    }
}
