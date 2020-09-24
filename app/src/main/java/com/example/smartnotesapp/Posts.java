package com.example.smartnotesapp;

public class Posts {


    public String post;
    public String uid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public Posts(){
    }

    public Posts(String uid,String name, String post) {
        this.post = post;
        this.name = name;
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

