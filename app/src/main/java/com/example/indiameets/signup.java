package com.example.indiameets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by viyom_g on 11/28/2015.
 */
public class signup extends Activity implements View.OnClickListener {
    Button bsignup;
    EditText etName, etAge, etPassword, etUsername;

    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etAge = (EditText) findViewById(R.id.etage);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bsignup = (Button) findViewById(R.id.signup_button);
        bsignup.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        user user1=new user(username,name,password,age);
        registerUser(user1);

    }
    private void registerUser(user User){
        RequestQueue queue= Volley.newRequestQueue(this);
        final String url = "http://192.168.42.195:3000/api/signup";
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("username",User.username);
        params.put("password", User.password);
        params.put("name",User.name);
        params.put("age",User.age+"");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n%s", response.toString(4));
                   // Toast.makeText(signup.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    if(response.getBoolean("success")){
                        startActivity(new Intent(signup.this,login.class));
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(signup.this,"Error in server", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(req);
    }



}