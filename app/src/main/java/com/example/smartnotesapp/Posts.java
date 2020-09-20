package com.example.smartnotesapp;

public class Posts {


    public String post;
    public String uid;

    public Posts(){
    }

    public Posts(String uid, String post) {
        this.post = post;
        this.uid = uid;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

