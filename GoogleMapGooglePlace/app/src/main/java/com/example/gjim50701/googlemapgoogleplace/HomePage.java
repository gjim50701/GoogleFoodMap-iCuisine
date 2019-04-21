package com.example.gjim50701.googlemapgoogleplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ImageView userImage;
    private TextView userAccount,userName;

    private String User_Id,User_Name,User_Image;

    private  static  final  String BLOG_URL = "http://163.13.201.88/php/Post_Show.php";
    RecyclerView recyclerView;
    BlogAdapater adapater;

    List<BlogSetGet> blogList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        blogList = new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBlogs();

    }

    private  void  loadBlogs(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BLOG_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray blogs = new JSONArray(response);

                            for (int i =0;i<blogs.length();i++){
                                JSONObject blogObject = blogs.getJSONObject(i);

                                String user_name = blogObject.getString("User_Name");
                                String node_name = blogObject.getString("Node_Name");
                                String user_image = blogObject.getString("User_Image");
                                String post_time = blogObject.getString("Post_Time");
                                String post_pic = blogObject.getString("Post_Pic");
                                String post_content = blogObject.getString("Post_Content");
                                String post_star = blogObject.getString("Post_Star");

                                BlogSetGet blog = new BlogSetGet (node_name,user_name,user_image,post_time,post_pic,post_content,post_star);
                                blogList.add(blog);
                            }
                            adapater = new BlogAdapater(HomePage.this,blogList);
                            recyclerView.setAdapter(adapater);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomePage.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        AppController gv = (AppController)getApplicationContext();
        User_Id = gv.getUserId();
        User_Name = gv.getUserName();
        User_Image = gv.getUserImage();

        userImage = (ImageView) findViewById(R.id.user_data_image);
        userName = (TextView) findViewById(R.id.user_data_name);
        userAccount = (TextView) findViewById(R.id.user_data_account);

        new DownloadImageTask(userImage).execute(User_Image);
        userName.setText(User_Name);
        userAccount.setText(User_Id);

        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_userfriend) {

            Intent intent = new Intent(HomePage.this,UserFrined.class);
            startActivity(intent);

        } else if (id == R.id.nav_foodmap) {

            Intent intent = new Intent(HomePage.this,MapActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_useraccount) {

            Intent intent = new Intent(HomePage.this,UserAccount.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

            SharedPrefManager.getmInstance(this).logout();
            finish();
            startActivity(new Intent(this,MainActivity.class));
            Toast.makeText(getApplicationContext(), "已登出", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
