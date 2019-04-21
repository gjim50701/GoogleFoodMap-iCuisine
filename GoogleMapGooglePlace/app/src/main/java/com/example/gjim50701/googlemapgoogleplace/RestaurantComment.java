package com.example.gjim50701.googlemapgoogleplace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantComment extends AppCompatActivity {



    private  static  final  String BLOG_URL = "http://163.13.201.88/php/Post_Show2.php";
    RecyclerView recyclerView;
    BlogAdapater adapater;

    Double Lat,Lon;

    List<BlogSetGet> blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_comment);


        Bundle bundle = getIntent().getExtras();
        Lat = bundle.getDouble("Lat");
        Lon = bundle.getDouble("Lon");


        blogList = new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBlogs();
    }

    private  void  loadBlogs(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BLOG_URL,
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
                            adapater = new BlogAdapater(RestaurantComment.this,blogList);
                            recyclerView.setAdapter(adapater);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RestaurantComment.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Node_Lat", Lat + "");
                params.put("Node_Lon", Lon + "");
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public static  class  BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
        }

        public void  setTitle(String title){

            TextView post_title=(TextView)mView.findViewById(R.id.post_place);
            post_title.setText(title);
        }
        public  void setDesc(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
    }
}
