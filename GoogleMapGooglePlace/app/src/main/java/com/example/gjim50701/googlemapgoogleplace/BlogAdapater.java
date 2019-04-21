package com.example.gjim50701.googlemapgoogleplace;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gjim50701 on 2018/9/2.
 */

public class BlogAdapater extends RecyclerView.Adapter<BlogAdapater.BlogViewHolder> {


    private Context mCtx;
    private List<BlogSetGet> BlogList;


    public BlogAdapater(Context mCtx, List<BlogSetGet> blogList) {
        this.mCtx = mCtx;
        BlogList = blogList;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.blog_row,parent,false);
        return new BlogViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        BlogSetGet blog = BlogList.get(position);

        holder.textViewName.setText(blog.getUser_Name());
        holder.textViewTime.setText(blog.getTime());
        holder.textViewStar.setText(blog.getStar()+"★");
        holder.textViewPlace.setText(blog.getRestaurant_Name());
        holder.textViewContent.setText(blog.getContent());



        new DownloadImageTask(holder.imageView).execute(blog.getPic());

      /*
        Glide.with(mCtx)
                .load(blog.getPic())
                .into(holder.imageView);
          */
        Glide.with(mCtx)
                .load(blog.getUser_Image())
                .into(holder.imageUser);

    }

    @Override
    public int getItemCount() {
        return BlogList.size();
    }

    class BlogViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView,imageUser;
        TextView textViewPlace,textViewContent,textViewStar,textViewName,textViewTime;
        public BlogViewHolder(View itemView) {
            super(itemView);


            textViewName=itemView.findViewById(R.id.post_id);
            textViewTime=itemView.findViewById(R.id.post_time);
            imageView = itemView.findViewById(R.id.post_image);
            imageUser = itemView.findViewById(R.id.post_userimage);
            textViewPlace = itemView.findViewById(R.id.post_place);
            textViewContent= itemView.findViewById(R.id.post_desc);
            textViewStar = itemView.findViewById(R.id.post_star);


        }
    }
}

