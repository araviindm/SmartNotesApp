package com.example.smartnotesapp;

public class UserInfo {

    public String name;
    public String surname;
    public String phoneno;
    public String uid;

    public UserInfo(){
    }

    public UserInfo(String uid,String name, String surname, String phoneno){

        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.phoneno = phoneno;
    }
   // public String getUId(){return uid;}
    public String getUserName() {
        return name;
    }
    public String getUserSurname() {
        return surname;
    }
    public String getUserPhoneno() {
        return phoneno;
    }
}
