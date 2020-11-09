package com.example.smartnotesapp;

public class UserInfo {

    public String name;
    public String surname;
    public String phoneno;
    public String uid;

    public UserInfo(){
        this.uid = "";
        this.name = "";
        this.surname = "";
        this.phoneno = "";
    }
    public String getUserName() { return name; }
    public String getUserSurname() {
        return surname;
    }
    public String getUserPhoneno() {
        return phoneno;
    }

    public void setUId(String uid){this.uid = uid;}
    public void setUserName(String name) { this.name = name; }
    public void setUserSurname(String surname) {
        this.surname = surname;
    }
    public void setUserPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
