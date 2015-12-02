package com.example.indiameets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Template extends AppCompatActivity {
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template);

        Intent i=getIntent();
//String id;
        String name = i.getStringExtra("name");
        String age = i.getStringExtra("age");
        String time = i.getStringExtra("time");
        String location = i.getStringExtra("location");
        Button sponsor,join;
        sponsor=(Button) findViewById(R.id.button_sponsor);
        join=(Button)findViewById(R.id.button_join);
        TextView v1 = (TextView) findViewById(R.id.name);
        v1.setText(name);
        TextView v2 = (TextView) findViewById(R.id.age);
        v2.setText(age);
        TextView v3 = (TextView) findViewById(R.id.time);
        v3.setText(time);

       // Intent ip=getIntent();

        //  Log.d("tk", name);
        // if(i.hasExtra(token))
//        if(!ip.hasExtra("token"))
//        {
//            Intent  intent = new Intent(Template.this, login.class);
//            startActivity(intent);
//        }
//        else
//        {
//            Log.d("aa","qw");
//            String nam = i.getStringExtra("token");
//            Intent  intent = new Intent(Template.this, AddEvent.class);
//            intent.putExtra("token", nam);
//            startActivity(intent);
//
//        }



            sponsor.setOnClickListener(new View.OnClickListener()

            {

                @Override
                public void onClick (View v){
                Intent intent = new Intent(Template.this, Sponsor.class);
                startActivity(intent);
            }
            }

            );

            RequestQueue que = Volley.newRequestQueue(this);
            String url = "http://192.168.42.218:3000/api/joined";
            HashMap<String, String> param = new HashMap<String, String>();
            param.put("name",name);

            JsonArrayRequest requ = new JsonArrayRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        Log.d("rt", "qw");
                        JSONObject jsonObject = response.getJSONObject(0);
                        person person = new person();
                        if (!jsonObject.isNull("name")) {
                            person.name = jsonObject.getString("name");
                            Log.d("mag", person.name);
                        }
                        if (!jsonObject.isNull("description")) {
                            person.age = jsonObject.getString("description");
                            Log.d("ok", person.age);
                        }
                        if (!jsonObject.isNull("imagepath")) {
                            person.url = jsonObject.getString("imagepath");
                            Log.d("ok", person.url);
                        }
                        if (!jsonObject.isNull("time")) {
                            person.time = jsonObject.getString("time");
                            Log.d("ok", person.time);
                        }
                        if (!jsonObject.isNull("location")) {
                            person.location = jsonObject.getString("location");
                            Log.d("ok", person.location);
                        }

                        if (!jsonObject.isNull("join")) {
                            person.join = jsonObject.getJSONArray("join");
                           // Log.d("ok", person.join);
                        }
                        id = jsonObject.getString("_id");
                        Log.d("id", id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Template.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    TextView v4 = (TextView) findViewById(R.id.location);
                    v4.setText(error.getMessage());

                }
            });
            que.add(requ);

            Toast.makeText(Template.this,name,Toast.LENGTH_LONG).

            show();

        join.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("sd", id);
                SharedPreferences sharedPref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
                String highScore = sharedPref.getString(getString(R.string.tok), null);
                if(highScore==null)
                {
                    Intent  intent = new Intent(Template.this, login.class);
                    startActivity(intent);
                }
                else
                {
                    Log.d("aa","qw");
                  joinevent(id); }
            }
        });
        }
    void joinevent(String ip)
    {
        RequestQueue que = Volley.newRequestQueue(this);
        SharedPreferences sharedPref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String highScore = sharedPref.getString(getString(R.string.tok), null);
        Log.d("zxaa", highScore);
        String url = "http://192.168.42.218:3000/api/join";
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("_id",ip);
        param.put("token", highScore);
        JsonObjectRequest requ = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("rt", "qw");
                    JSONObject jsonObject = response;
                    person person = new person();
                    person.name = jsonObject.getString("name");
                    Log.d("id", person.name);
                    Intent intent = new Intent(Template.this, Main2Activity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Template.this, error.getMessage(), Toast.LENGTH_LONG).show();
                TextView v4 = (TextView) findViewById(R.id.location);
                v4.setText(error.getMessage());

            }
        });
        que.add(requ);


    }
}
