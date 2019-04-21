package com.example.gjim50701.googlemapgoogleplace.photocrop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gjim50701.googlemapgoogleplace.AppController;
import com.example.gjim50701.googlemapgoogleplace.BuildConfig;
import com.example.gjim50701.googlemapgoogleplace.MainActivity;
import com.example.gjim50701.googlemapgoogleplace.MapActivity;
import com.example.gjim50701.googlemapgoogleplace.R;
import com.example.gjim50701.googlemapgoogleplace.UserAccount;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PhotoActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = "Dashboard";

    //Capture Image and Pick Gallery Image
    private static final int REQUEST_CAMERA = 112;
    private static final int REQUEST_PHOTO_LIBRARY = 111;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int ACTION_TAKE_PHOTO_CAMERA = 1;
    private static final int ACTION_TAKE_PHOTO_DEVICE = 4;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    Bitmap myPhoto,bitmap;
    private static final int REQ_WIDTH = 100;
    private static final int REQ_HEIGHT = 100;

    private String imageUrl = "http://163.13.201.88/php/User_Pic_Update.php";
    private String User_Id,User_Image;

    private Button saveImage;
    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mContext = PhotoActivity.this;

        iv_image = findViewById(R.id.iv_image);
        saveImage = (Button) findViewById(R.id.btn_save);

        AppController gv = (AppController) getApplicationContext();
        User_Id = gv.getUserId();

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage(){

        StringRequest strReQ = new StringRequest(Request.Method.POST, imageUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"修改成功 請重新登入",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PhotoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"修改失敗",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                String imageData = imageToString(myPhoto);
                params.put("User_Id",User_Id);
                params.put("image",imageData);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReQ);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);

        return  encodedImage;
    }


    public void openCamera(View v)
    {
        if (permissionCamera())
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_CAMERA);
    }

    public void openGallery(View v)
    {
        if (permissionPhoto())
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_DEVICE);
    }

    private boolean permissionCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ( ActivityCompat.checkSelfPermission(mContext,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }

    }

    private boolean permissionPhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHOTO_LIBRARY);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&  grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_CAMERA);
                }
                break;
            case REQUEST_PHOTO_LIBRARY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_DEVICE);
                }
                break;
        }
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)) {

        } else {

            return;
        }

        switch(actionCode) {
            case ACTION_TAKE_PHOTO_CAMERA:
                File f;
                Uri mediaFileUri;
                try {

                    f = setUpPhotoFile();

                    if(f != null){
                        mediaFileUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", f);
                        mCurrentPhotoPath = f.getAbsolutePath();
                    }else{
                        f = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                        mediaFileUri = getOutputMediaFileProviderUri(MEDIA_TYPE_IMAGE);
                    }

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mediaFileUri);

                } catch (Exception e) {
                    e.printStackTrace();
                    mCurrentPhotoPath = null;
                    //mCurrentPhotoUri = null;
                }
                break;
            case ACTION_TAKE_PHOTO_DEVICE:
                try{
                    takePictureIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    takePictureIntent.setType("image/*");
                }catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        if(f == null){
            return null;
        }
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir(mAlbumStorageDirFactory);
        if(albumF == null){
            return null;
        }
        return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
    }

    public File getAlbumDir(AlbumStorageDirFactory mAlbumStorageDirFactory) {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getString(R.string.app_name));
            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }
        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }
        return storageDir;
    }


    private Uri getOutputMediaFileProviderUri(int type){
        return FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Bauwow");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Zaffee", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_CANCELED){
            finish();
            return;
        }
        switch (requestCode) {

            case ACTION_TAKE_PHOTO_CAMERA: {
                if (resultCode == RESULT_OK) {
                    if(mCurrentPhotoPath == null){
                        mCurrentPhotoPath = ImageFilePath.getPath(this, data.getData());
                    }

                    openCropIntent();
                }
                break;
            }
            case ACTION_TAKE_PHOTO_DEVICE:{
                if (resultCode == RESULT_OK) {
                    Uri chosenImageUri;
                    try
                    {
                        if(data != null){
                            chosenImageUri = data.getData();

                            mCurrentPhotoPath = ImageFilePath.getPath(this, chosenImageUri);

                            openCropIntent();

                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                break;
            }
            case CROP_FROM_CAMERA:
                handleBigCameraPhoto();

                break;
            default:{
                finish();
            }
        }
    }


    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {

                try {
                    myPhoto = Constants.bmp;
                    iv_image.setImageBitmap(myPhoto);
                    Constants.bmp = null;

                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        myPhoto = Constants.decodeBitmapFromFile(mCurrentPhotoPath, REQ_WIDTH, REQ_HEIGHT);
                        bitmap = Bitmap.createScaledBitmap(myPhoto, iv_image.getWidth(), iv_image.getHeight(), true);
                        iv_image.setImageBitmap(bitmap);
                        bitmap = null;
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
//                        global.alertStatus(mContext, "errore photo: " + e.toString(), "");
                        //finish();
                    }
                }

            Log.v("PHOTO", mCurrentPhotoPath);
            //galleryAddPic();
            //mCurrentPhotoPath = null;
        }else{
            Log.e("Error", "");
//            global.alertStatus(mContext, "Error to load photo", "");
        }

    }

    private void openCropIntent(){
        if (mCurrentPhotoPath != null) {
            Intent intent = new Intent(PhotoActivity.this, CropImageIntent.class);
            intent.putExtra("image", mCurrentPhotoPath);
            startActivityForResult(intent, CROP_FROM_CAMERA);
        }else{
            Log.e("Error", "");
//            global.alertStatus(mContext, "errore photo: take camera", "");
        }
    }
}
