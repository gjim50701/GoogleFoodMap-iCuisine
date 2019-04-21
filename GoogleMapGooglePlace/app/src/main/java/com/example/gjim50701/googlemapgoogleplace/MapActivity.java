package com.example.gjim50701.googlemapgoogleplace;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import com.example.gjim50701.googlemapgoogleplace.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;

/**
 * Created by gjim50701 on 2018/6/6.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMapLongClickListener,GoogleMap.OnMarkerClickListener {



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "開啟地圖", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady : Map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
   //         getDeviceLocation();

            center = new LatLng(25.1752467, 121.4494943);
            cameraPosition = new CameraPosition.Builder().target(center).zoom(17).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE =1234;
    private static final float DEFAULT_ZOOM = 17f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168),new LatLng(71,136));

    public static final String ID = "Node_Id";
    public static final String TITLE = "Node_Name";
    public static final String LAT = "Node_Lat";
    public static final String LNG = "Node_Lon";
    public static final String ADDRESS = "Node_Address";
    public static final String PHONE_NUMBER = "Node_Phone";
    public static final String STAR = "Node_Star";
    public static final String PICTURE = "Node_Pic";

    private String mainurl = "http://163.13.201.88/php/markers.php";
    private String userurl = "http://163.13.201.88/php/markers2.php";
    private String picurl = "http://163.13.201.88/php/Pic_Sent.php";
    private String friendurl = "http://163.13.201.88/php/Friend_Map2.php";

    String tag_json_obj = "json_obj_req";

    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps,mInfo,mPlacePicker,mMarkerchoose;

    //vars
    private boolean isOpenAllMarker = true;
    private Boolean mLocationPermissionsGranted =false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker,mNewMarker,Usermarker,Defaultmarker;
    private Bundle bundle = new Bundle();
    private LatLng latLng,center,latlon;
    private CameraPosition cameraPosition;
    private String title,phone_number,address,mSnippet,User_Id;
    private Double lat,lon;
    private double star;
    private MarkerOptions markerOptions = new MarkerOptions();
    private MarkerOptions markerOptionS = new MarkerOptions();
    private MarkerOptions markerOptionsss = new MarkerOptions();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        mInfo = (ImageView) findViewById(R.id.place_info);
        mPlacePicker =(ImageView) findViewById(R.id.place_picker);
        mMarkerchoose = (ImageView) findViewById(R.id.markerchoose);

        AppController gv = (AppController) getApplicationContext();
        User_Id = gv.getUserId();

        getLocationPermission();
    }
    
    private void init(){
        Log.d(TAG, "init: initializing");

        getUserMarkers();
        getFriendMarkers();
        getMainMarkers();


        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        mMap.setOnMapLongClickListener((GoogleMap.OnMapLongClickListener) this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this,Places.getGeoDataClient(this, null),LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    gotLocate();
                }
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked place info");
                try{
                    if(mMarker.isInfoWindowShown()){
                        mMarker.hideInfoWindow();
                    }else{
                        Log.d(TAG, "onClick: place info:" +mPlace.toString());
                        mMarker.showInfoWindow();
                    }
                }catch(NullPointerException e){
                    Log.d(TAG, "onClick: NullPointerException:"+e.getMessage());
                }
            }
        });

        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.d(TAG, "onClick: GooglePlayServicesRepairableException: "+e.getMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.d(TAG, "onClick: GooglePlayServicesNotAvailableException: "+e.getMessage());
                }

            }
        });

        mMarkerchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpenAllMarker==true){
                    mMap.clear();
                    getUserMarkers();
                    getFriendMarkers();
                    isOpenAllMarker = false;
                }else{
                    getMainMarkers();
                    isOpenAllMarker = true;
                }
            }
        });
        hideSoftKeyboard();
    }

    private void getMainMarkers() {
        StringRequest strReq = new StringRequest(Request.Method.POST, mainurl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("node");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(TITLE);
                        address = jsonObject.getString(ADDRESS);
                        phone_number = jsonObject.getString(PHONE_NUMBER);
                        star = Double.parseDouble(jsonObject.getString(STAR));
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));

                        addMarker(latLng, title,phone_number,address,star);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(MapActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void addMarker(LatLng latlng, final String title,final String phone_number,final String address,final Double star) {

        mSnippet ="地址: "+address+"\n"
                +"電話: "+phone_number+"\n"
                +"評價: "+star;

        markerOptions.position(latlng)
                .title(title)
                .snippet(mSnippet);

        Defaultmarker = mMap.addMarker(markerOptions);
        Defaultmarker.setTag("0");

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUserMarkers() {

        StringRequest strReQ = new StringRequest(Request.Method.POST, userurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("node");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(TITLE);
                        address = jsonObject.getString(ADDRESS);
                        phone_number = jsonObject.getString(PHONE_NUMBER);
                        star = Double.parseDouble(jsonObject.getString(STAR));
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));

                        addUserMarker(latLng, title, phone_number, address, star);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(MapActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("User_Id",User_Id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReQ, tag_json_obj);
    }

    private void addUserMarker(LatLng latlng, final String title,final String phone_number,final String address,final Double star) {

        mSnippet ="地址: "+address+"\n"
                +"電話: "+phone_number+"\n"
                +"評價: "+star;

        markerOptionS.position(latlng)
                .title(title)
                .snippet(mSnippet)
                .icon(defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        Usermarker = mMap.addMarker(markerOptionS);
        Usermarker.setTag("1");

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFriendMarkers(){

        StringRequest strReq = new StringRequest(Request.Method.POST, friendurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("node");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(TITLE);
                        address = jsonObject.getString(ADDRESS);
                        phone_number = jsonObject.getString(PHONE_NUMBER);
                        star = Double.parseDouble(jsonObject.getString(STAR));
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));

                        addFriendMarker(latLng, title, phone_number, address, star);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(MapActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("User_Id",User_Id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

    private void addFriendMarker(LatLng latlng, final String title,final String phone_number,final String address,final Double star) {

        mSnippet ="地址: "+address+"\n"
                +"電話: "+phone_number+"\n"
                +"評價: "+star;

        markerOptionS.position(latlng)
                .title(title)
                .snippet(mSnippet)
                .icon(defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        Usermarker = mMap.addMarker(markerOptionS);
        Usermarker.setTag("2");

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        final Double Lat = marker.getPosition().latitude;
        final Double Lon = marker.getPosition().longitude;

        getRestaurantImage(Lat, Lon,marker);

        return false;
    }

    private void getRestaurantImage (final Double Lat, final Double Lon, final Marker marker) {

        StringRequest strReQs = new StringRequest(Request.Method.POST, picurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String picture = jObj.getString(PICTURE);
                    ImageView iv = new ImageView(MapActivity.this);
                    new DownloadImageTask(iv).execute(picture);
                    gotoAlertDialog(iv,marker);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(MapActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lat", Lat + "");
                params.put("lon", Lon + "");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReQs, tag_json_obj);

    }

    private void gotoAlertDialog(ImageView iv, final Marker marker ){

        AppController gv = (AppController) getApplicationContext();
        final String user_Id = gv.getUserId();
        final String titledata = marker.getTitle();

        if (marker.getTag() == "0" ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            builder.setTitle(marker.getTitle())
                    .setMessage(marker.getSnippet())
                    .setView(iv)
                    .setNeutralButton("發文", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(MapActivity.this,WritePost.class);
                            bundle.putString("titledata",titledata);
                            bundle.putDouble("Lat",marker.getPosition().latitude);
                            bundle.putDouble("Lon",marker.getPosition().longitude);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("加入最愛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (success) {
                                            Toast.makeText(MapActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MapActivity.this, "加入失敗", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            MarkerDataRequest markerDataRequest = new MarkerDataRequest(marker.getPosition().latitude,marker.getPosition().longitude,user_Id, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MapActivity.this);
                            queue.add(markerDataRequest);
                        }
                    })
                    .setPositiveButton("評論貼文", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent();
                            intent.setClass(MapActivity.this,RestaurantComment.class);
                            bundle.putDouble("Lat",marker.getPosition().latitude);
                            bundle.putDouble("Lon",marker.getPosition().longitude);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    })
                    .create()
                    .show();

        } else if (marker.getTag() == "1"){
            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            builder.setTitle(marker.getTitle())
                    .setMessage(marker.getSnippet())
                    .setNeutralButton("發文", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(MapActivity.this,WritePost.class);
                            bundle.putString("titledata",titledata);
                            bundle.putDouble("Lat",marker.getPosition().latitude);
                            bundle.putDouble("Lon",marker.getPosition().longitude);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("刪除最愛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (success) {
                                            marker.remove();
                                            Toast.makeText(MapActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MapActivity.this, "刪除失敗", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            RemoveMarkerDataRequest removeMarkerDataRequest = new RemoveMarkerDataRequest(marker.getPosition().latitude,marker.getPosition().longitude,user_Id, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MapActivity.this);
                            queue.add(removeMarkerDataRequest);
                        }
                    })
                    .setPositiveButton("評論貼文", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(MapActivity.this,RestaurantComment.class);
                            bundle.putDouble("Lat",marker.getPosition().latitude);
                            bundle.putDouble("Lon",marker.getPosition().longitude);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    })
                    .create()
                    .show();

        } else if (marker.getTag() == "2") {

            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            builder.setTitle(marker.getTitle())
                    .setMessage(marker.getSnippet())
                    .setNeutralButton("發文", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(MapActivity.this,WritePost.class);
                            bundle.putString("titledata",titledata);
                            bundle.putDouble("Lat",marker.getPosition().latitude);
                            bundle.putDouble("Lon",marker.getPosition().longitude);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    })
                    .setPositiveButton("評論貼文", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(MapActivity.this,RestaurantComment.class);
                            bundle.putDouble("Lat",marker.getPosition().latitude);
                            bundle.putDouble("Lon",marker.getPosition().longitude);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    })
                    .create()
                    .show();


        }

    }

    @Override
    public void onMapLongClick(final LatLng point) {

        mNewMarker = mMap.addMarker(markerOptionsss.position(point).icon(defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        new AlertDialog.Builder(MapActivity.this)
                .setTitle("增新標記")
                .setMessage("是否標記此點?")
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNewMarker.remove();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lat = mNewMarker.getPosition().latitude;
                        lon = mNewMarker.getPosition().longitude;
                        Intent intent = new Intent();
                        intent.setClass(MapActivity.this,NewMarker.class);
                        bundle.putDouble("Lat",lat);
                        bundle.putDouble("Lon",lon);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDatailsCallback);
            }
        }
    }

    private void gotLocate(){

        Log.d(TAG, "gotLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString,1);
        }catch(IOException e){
            Log.d(TAG, "gotLocate: IOException: " + e.getMessage());
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "gotLocate: found a location :" + address.toString());

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if(mLocationPermissionsGranted){
                Task location  = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            try {
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        DEFAULT_ZOOM, "My location");
                            }catch (Exception e){
                                Toast.makeText(MapActivity.this,"請開啟定位再使用",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this,"unable to get current location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e){
            Log.d(TAG, "getDeviceLocation: : SecurityException : " + e.getMessage());
        }
        mSearchText.setText("");
    }

    private void moveCamera(LatLng latLng , float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat:" + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this));

        if (placeInfo != null){
            try{
                String snippet = "地址: " + placeInfo.getAddress()+"\n"+
                        "電話: " + placeInfo.getPhoneNumber()+"\n"+
                        "網站: " + placeInfo.getWebsiteUri()+"\n"+
                        "評價: " + placeInfo.getRating()+"\n";

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(snippet)
                        .icon(defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mMarker = mMap.addMarker(options);

            }catch(NullPointerException e){
                Log.d(TAG, "moveCamera: NullPointerException:"+e.getMessage());
            }
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng));
        }
        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng , float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat:" + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        hideSoftKeyboard();
    }
    
    private void initMap(){
        Log.d(TAG,"initMap : initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission : getting location permissions ");
        String[] permissions ={Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted=true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0 ; i < grantResults.length ; i++){
                        if(grantResults[i]  !=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted =false;
                            Log.d(TAG, "onRequestPermissionsResult: permissoin failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permissoin granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
    }

    /*
          --------------------------------------google places API autocomplete suggestions--------------------------------------------------------
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            hideSoftKeyboard();
            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDatailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDatailsCallback = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                if(!places.getStatus().isSuccess()){
                    Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                    places.release();
                    return;
                }
                final Place place = places.get(0);

                try {
                    mPlace = new PlaceInfo();
                    mPlace.setName(place.getName().toString());
                    Log.d(TAG, "onResult:  name:"+ place.getName());
                    mPlace.setAddress(place.getAddress().toString());
                    Log.d(TAG, "onResult: address:"+place.getAddress());
                    mPlace.setId(place.getId());
                    Log.d(TAG, "onResult: id:"+place.getId());
                    mPlace.setLatLng(place.getLatLng());
                    Log.d(TAG, "onResult: lating:"+place.getLatLng());
                    mPlace.setRating(place.getRating());
                    Log.d(TAG, "onResult: rating:"+place.getRating());
                    mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                    Log.d(TAG, "onResult:  phone number" +place.getPhoneNumber());
                    mPlace.setWebsiteUri(place.getWebsiteUri());
                    Log.d(TAG, "onResult: website uri:" +place.getWebsiteUri());

                    Log.d(TAG, "onResult: place: " +mPlace.toString());
                }catch (NullPointerException e){
                    Log.d(TAG, "onResult:  NullPointerException"+e.getMessage());
                }

                moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                        place.getViewport().getCenter().longitude),DEFAULT_ZOOM,mPlace);

                places.release();
            }
    };
}
