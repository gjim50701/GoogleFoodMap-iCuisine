package com.example.gjim50701.googlemapgoogleplace;

/**
 * Created by gjim50701 on 2018/9/2.
 */

public class BlogSetGet {

    private  String place,username,userimage ,time,pic,content,star;

    public BlogSetGet(String Restaurant_Name, String User_Name,String User_Image ,String time, String pic,String content,String star) {
        this.place = Restaurant_Name;
        this.username = User_Name;
        this.userimage = User_Image;
        this.time = time;
        this.pic=pic;
        this.content=content;
        this.star=star;
    }

    public String getRestaurant_Name() {
        return place;
    }

    public void setRestaurant_Name(String place) {
        this.place = place;
    }

    public String getUser_Name() {
        return username;
    }

    public void setUser_Name(String username) {
        this.username=username;
    }

    public String getUser_Image() {
        return userimage;
    }

    public void setUser_Image(String userimage) {
        this.userimage = userimage;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time=time;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic=pic;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content=content;
    }
}