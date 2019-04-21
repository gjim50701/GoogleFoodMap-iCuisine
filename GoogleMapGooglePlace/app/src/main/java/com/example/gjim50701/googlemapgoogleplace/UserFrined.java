package com.example.gjim50701.googlemapgoogleplace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UserFrined extends AppCompatActivity {

    private ImageView search;
    private EditText searchinput;

    private String User_Id;
    private String urlFirend = "http://163.13.201.88/php/User_Check.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_frined);

        search = (ImageView) findViewById(R.id.ic_addfriend);
        searchinput = (EditText) findViewById(R.id.input_search);

        AppController gv = (AppController) getApplicationContext();
        User_Id = gv.getUserId();


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Searchcontent = searchinput.getText().toString();
                goSearch(Searchcontent);
            }
        });
    }

    private void goSearch(final String content){

        StringRequest strReQ = new StringRequest(Request.Method.POST, urlFirend, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String Friend_Id = jObj.getString("Friend_Id");
                    String Friend_Name = jObj.getString("Friend_Name");
                    String Friend_Image = jObj.getString("Friend_Image");


                    if(Friend_Id.equals("無此用戶"))
                    {
                        Toast.makeText(UserFrined.this,"無此用戶",Toast.LENGTH_SHORT).show();

                    }else {

                        ImageView iv = new ImageView(UserFrined.this);
                        new DownloadImageTask(iv).execute(Friend_Image);
                        goAlertDialog(Friend_Id, Friend_Name, iv);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(UserFrined.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content",content+"");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReQ);

    }
    private void goAlertDialog (final String friend_id ,final String friend_name,ImageView iv){


            AlertDialog.Builder builder = new AlertDialog.Builder(UserFrined.this);
            builder.setTitle("加入好友")
                    .setMessage("確定要把 " + friend_name + " 加為好友？")
                    .setView(iv)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (success) {
                                            Toast.makeText(UserFrined.this, "加入成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(UserFrined.this, "加入失敗", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            AddFriendRequest addFriendRequest = new AddFriendRequest(User_Id, friend_id, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(UserFrined.this);
                            queue.add(addFriendRequest);
                        }
                    })
                    .create()
                    .show();

    }
}




