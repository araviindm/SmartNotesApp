package com.example.smartnotesapp;

import java.util.Date;

public class Posts {

    public String uid;
    public String title;
    public String tag;
    public String post;
    public String post_time;


    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public Posts(){
    }

    public Posts(String uid,String name,String title,String tag, String post,String post_time) {
        this.uid = uid;
        this.name = name;
        this.title = title;
        this.tag = tag;
        this.post = post;
        this.post_time = post_time;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }


}

