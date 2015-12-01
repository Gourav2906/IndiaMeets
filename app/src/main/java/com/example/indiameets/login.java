package com.example.indiameets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class login extends Activity {
    Button bsignup;
    EditText etPassword, etUsername;
    TextView iusername, ipassword;
    private String loginToken;

    private TextView signup;

        @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etUsername = (EditText) findViewById(R.id.login_username);
        etPassword = (EditText) findViewById(R.id.login_password);
        //iusername=(TextView) findViewById(R.id.ilogin_username);
        //ipassword=(TextView)findViewById(R.id.ilogin_password);

        bsignup = (Button) findViewById(R.id.button_login);
        bsignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
             //   if (validate(iusername, ipassword)) {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    user user1 = new user(username, password);
                    authenticate(user1);
            //    }


            }
        });
            signup = (TextView) findViewById(R.id.button_signup);

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);


            }
        });
    }


    public boolean validate(TextView _username,TextView _password) {
        boolean valid = true;

        String username = _username.getText().toString();
        String password = _password.getText().toString();

        if (username.isEmpty()) {
            _username.setError("Enter a username");
            valid = false;
        } else {
            _username.setError(null);
        }

        if (password.isEmpty()) {
            _password.setError("Enter password");
            valid = false;
        } else {
            _password.setError(null);
        }

        return valid;
    }
    private void authenticate (final user User) {
        final String url = "http://192.168.42.82:3000/api/login";
        RequestQueue queue= Volley.newRequestQueue(this);
        HashMap<String,String> params = new HashMap<String, String>();
            params.put("username",User.username);
            params.put("password", User.password);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n%s", response.toString(4));
                    Toast.makeText(login.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    if(response.getBoolean("success")){
                        loginToken = response.getString("token");
                        //userId = response.getString("userid");
                        //intent(login.this,AddEvent);
                        //startActivity(intent);
                        Log.d("teg", loginToken);
                        //  setResult(RESULT_OK,intent);
                       // finish();
                        //Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, Main2Activity.class);
                        intent.putExtra("token",loginToken);
                        startActivity(intent);

                        //logUserIN(User);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this,"Error in server",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(req);
    }
}

