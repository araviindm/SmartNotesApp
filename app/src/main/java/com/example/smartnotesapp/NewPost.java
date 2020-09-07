package com.example.smartnotesapp;

public class NewPost {


    public String post;
    public String uid;

    public NewPost(){
    }

    public NewPost(String uid,String post){
        this.uid = uid;
        this.post = post;
    }

    public String getpost() {
        return post;
    }
    public String getuid() {
        return uid;
    }
}

