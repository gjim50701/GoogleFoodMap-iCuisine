package com.example.gjim50701.googlemapgoogleplace;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewMarker extends AppCompatActivity {

    private Double Lat,Lon,Star;
    private String User_Id,Restaurant_Name,Restaurant_Phone;

    private EditText latitude,longitude,title,phonenumber;
    private RatingBar star;
    private Button inputvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_marker);

        Bundle bundle = getIntent().getExtras();
        Lat = bundle.getDouble("Lat");
        Lon = bundle.getDouble("Lon");

        latitude=(EditText)findViewById(R.id.etLat);
        longitude=(EditText)findViewById(R.id.etLon);
        title=(EditText)findViewById(R.id.etTitle);
        phonenumber=(EditText)findViewById(R.id.etPhone);
        star=(RatingBar)findViewById(R.id.rbStar);
        inputvalue=(Button)findViewById(R.id.inputButton);

        latitude.setText(Lat.toString());
        longitude.setText(Lon.toString());

        AppController gv = (AppController)getApplicationContext();
        User_Id= gv.getUserId();

        inputvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant_Name = title.getText().toString();
                Restaurant_Phone = phonenumber.getText().toString();
                Star = (double) star.getRating();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(NewMarker.this, MapActivity.class);
                                NewMarker.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewMarker.this);
                                builder.setMessage("標記失敗")
                                        .setNegativeButton("重新標記", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                        NewMarkerRequest newMarkerRequest = new NewMarkerRequest(Lat,Lon,Restaurant_Name,Restaurant_Phone,Star,User_Id,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(NewMarker.this);
                        queue.add(newMarkerRequest);
                        }
        });
    }
}