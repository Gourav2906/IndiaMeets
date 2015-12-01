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

/**
 * Created by viyom_g on 12/1/2015.
 */
public class Sponsor extends Activity {
    EditText etname, etamount;
    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsor);
        etname = (EditText) findViewById(R.id.sponsor_name);
        etamount = (EditText) findViewById(R.id.sponsor_amount);
        enter = (Button) findViewById(R.id.button_enter);
        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etname.getText().toString();
                String amount = etamount.getText().toString();
                addsponsor(name,amount);
            }
        });
    }
    private void addsponsor (String name, String amount) {
        final String url = "http://192.168.42.195:3000/api/login";
        RequestQueue queue= Volley.newRequestQueue(this);
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("name",name);
        params.put("price", amount);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n%s", response.toString(4));
                    Toast.makeText(Sponsor.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    if(response.getBoolean("success")){
                        //loginToken = response.getString("token");
                        //userId = response.getString("userid");
                        Intent intent = new Intent();
                        //intent.putExtra("logintoken",loginToken);
                        //intent(login.this,AddEvent);
                        //startActivity(intent);
                       // Log.d("teg", loginToken);
                        //intent.putExtra("userid",userId);
                       // setResult(RESULT_OK,intent);
                       // finish();
                        //Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sponsor.this, MainActivity.class);
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
                Toast.makeText(Sponsor.this,"Error in server",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(req);
    }
}