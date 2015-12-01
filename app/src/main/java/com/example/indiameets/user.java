package com.example.indiameets;

import android.app.Activity;

/**
 * Created by viyom_g on 11/28/2015.
 */
public class user extends Activity {
    String name,username,password;
    int age;
    public user(String username, String name, String password, int age){
        this.name=name;
        this.username=username;
        this.age=age;
        this.password= password;
    }
    public user(String username, String password){
        this.name="";
        this.username=username;
        this.age=-1;
        this.password= password;
    }
}

