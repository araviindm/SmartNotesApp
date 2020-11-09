package com.example.smartnotesapp;

public class Posts {

    public String uid;
    public String title;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String tag;
    public String post;

    public String getSurName() {
        return surName;
    }

    public String post_time;
    public String surName;


    public String getPost_time()  {
       return post_time;
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

    public Posts(String uid,String name,String surName,String title,String tag, String post,String post_time) {
        this.uid = uid;
        this.name = name;
        this.surName = surName;
        this.title = title;
        this.tag = tag;
        this.post = post;
        this.post_time = post_time;

    }


    public String getTitle() {
        return title;
    }


    public String getTag() {
        return tag;
    }


    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }


}

