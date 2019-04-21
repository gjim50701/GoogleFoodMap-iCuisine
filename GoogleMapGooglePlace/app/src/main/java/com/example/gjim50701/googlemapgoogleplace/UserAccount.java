package com.example.gjim50701.googlemapgoogleplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gjim50701.googlemapgoogleplace.photocrop.PhotoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserAccount extends AppCompatActivity {

    private String nameurl = "http://163.13.201.88/php/User_Update.php";
    private String User_Id,User_Name,User_Image,User_New_Name;

    private ImageView userImage,changeName,changeImage;
    private TextView userAccount;
    private EditText userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);


        AppController gv = (AppController) getApplicationContext();

        User_Id = gv.getUserId();
        User_Name = gv.getUserName();
        User_Image = gv.getUserImage();

        userImage = (ImageView) findViewById(R.id.user_data_image);
        userName = (EditText) findViewById(R.id.user_data_name);
        userAccount = (TextView) findViewById(R.id.user_data_account);
        changeName = (ImageView) findViewById(R.id.change_name);
        changeImage = (ImageView) findViewById(R.id.change_image);

        new DownloadImageTask(userImage).execute(User_Image);
        userName.setText(User_Name);
        userAccount.setText(User_Id);

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rename();
            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

    }

    private void rename(){

        StringRequest strReQs = new StringRequest(Request.Method.POST,nameurl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    Toast.makeText(UserAccount.this, "更改成功 請重新登入", Toast.LENGTH_SHORT).show();
                    JSONObject jObj = new JSONObject(response);
                    User_New_Name = jObj.getString("User_New_Name");
                    Intent intent = new Intent(UserAccount.this,MainActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(UserAccount.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("User_New_Name",userName.getText()+"");
                params.put("User_Id",User_Id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReQs);

    }

    private void getImage() {

        Intent intent = new Intent(UserAccount.this,PhotoActivity.class);
        startActivity(intent);

    }

}
