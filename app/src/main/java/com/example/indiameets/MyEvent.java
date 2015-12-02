package com.example.indiameets;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyEvent extends AppCompatActivity {
    public List<person> personList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.going);

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        final RVAdapter rvAdapter = new RVAdapter(personList);
        rv.setAdapter(rvAdapter);

        HashMap<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String highScore = sharedPref.getString(getString(R.string.tok), null);
        params.put("token",highScore);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.42.218:3000/api/my";
//        String url="http://api.football-data.org/alpha/soccerseasons/398/teams";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,url, new JSONObject(params),new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("rt", "qw");
                    if (response.length() > 0) {
//                        personList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            person person = new person();
                            if (!jsonObject.isNull("name")) {
                                person.name = jsonObject.getString("name");
                                //     Log.d("mag",person.name);
                            }
                            if (!jsonObject.isNull("description")) {
                                person.age = jsonObject.getString("description");
                                //   Log.d("ok",person.age);
                            }
                            if (!jsonObject.isNull("imagepath")) {
                                person.url = jsonObject.getString("imagepath");
                                //   Log.d("ok",person.url);
                            }
                            if (!jsonObject.isNull("time")) {
                                person.time = jsonObject.getString("time");
                                //   Log.d("ok",person.url);
                            }
                            if (!jsonObject.isNull("location")) {
                                person.location = jsonObject.getString("location");
                                //   Log.d("ok",person.url);
                            }

                            if (!jsonObject.isNull("join")) {
                                person.join = jsonObject.getJSONArray("join");
                                //   Log.d("ok",person.url);
                            }



                            personList.add(i, person);
                        }
                        rvAdapter.notifyDataSetChanged();
                        Log.d("tag", "hello");
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("tag", "qwe");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","he"+error.getMessage());
                // do something
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

}
