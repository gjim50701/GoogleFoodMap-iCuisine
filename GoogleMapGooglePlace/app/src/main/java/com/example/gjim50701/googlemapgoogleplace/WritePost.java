package com.example.gjim50701.googlemapgoogleplace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WritePost extends AppCompatActivity {

    private Double Lat,Lon,Star;
    private String User_Id,User_Name,User_Image,Restaurant_Name,desc_val,pic_val;

    private TextView user_Name,restaurant_Name;
    private ImageView user_Image;
    private ImageButton mSelectImage;
    private EditText mPostDesc;
    private RatingBar mstar;
    private Button mSubmitBtn;

    private Uri mImageUri = null;
    private  static  final  int GALLERY_REQUEST=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        Bundle bundle = getIntent().getExtras();
        Restaurant_Name = bundle.getString("titledata");
        Lat = bundle.getDouble("Lat");
        Lon = bundle.getDouble("Lon");

        AppController gv = (AppController)getApplicationContext();
        User_Id = gv.getUserId();
        User_Name = gv.getUserName();
        User_Image = gv.getUserImage();

        user_Image = (ImageView) findViewById(R.id.UserImage);
        restaurant_Name = (TextView) findViewById(R.id.RestaurantName);
        user_Name = (TextView) findViewById(R.id.UserName);
        mSelectImage = (ImageButton) findViewById(R.id.imageSelect);
        mPostDesc = (EditText) findViewById(R.id.descField);
        mstar = (RatingBar) findViewById(R.id.rbStar);
        mSubmitBtn = (Button) findViewById(R.id.submitBtn);


        new DownloadImageTask(user_Image).execute(User_Image);
        restaurant_Name.setText("餐廳名稱："+Restaurant_Name);
        user_Name.setText(User_Name);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent =new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc_val = mPostDesc.getText().toString().trim();
                Star = (double) mstar.getRating();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                    pic_val = imageToString(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(WritePost.this, "發文成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WritePost.this, HomePage.class);
                                WritePost.this.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                PostRequest postRequest= new PostRequest(User_Id,Lat,Lon,pic_val,desc_val,Star,responseListener);
                RequestQueue queue = Volley.newRequestQueue(WritePost.this);
                queue.add(postRequest);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST&&resultCode==RESULT_OK){
            mImageUri=data.getData();
            mSelectImage.setImageURI(mImageUri);
        }
    }
    private String imageToString (Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);

        return  encodedImage;
    }
}
