package com.example.indiameets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public List<person> personList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        final RVAdapter rvAdapter = new RVAdapter(personList);
        rv.setAdapter(rvAdapter);
        Intent i=getIntent();
        String location = i.getStringExtra("location");
        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS", MODE_PRIVATE).edit();
        if (i.hasExtra("location")) {
            editor.putString(getString(R.string.location), location);
            editor.commit();
        }
        HashMap<String, String> params = new HashMap<String, String>();
        Intent  iq = new Intent(Main2Activity.this, Template.class);
        String name = iq.getStringExtra("token");
        iq.putExtra("token",name);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
       String url = "http://192.168.42.218:3000/api/locate";
//        String url="http://api.football-data.org/alpha/soccerseasons/398/teams";
    //    JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        SharedPreferences sharedPref = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        params.put("location",sharedPref.getString(getString(R.string.location), "Delhi"));
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,url, new JSONObject(params),new Response.Listener<JSONArray>() {
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

        requestQueue.add(req);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addevent) {
            // Handle the camera action
            // sharedPref = Main2Activity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences sharedPref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
            String highScore = sharedPref.getString(getString(R.string.tok), null);
//            Log.d("toki",highScore);
            Intent i=getIntent();
           // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
      //      sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //    int defaultValue = getResources().getInteger(R.string.tok_deflaut);
            // tk ta=new tk(i.getStringExtra("token"));
          //  Log.d("tk", name);
             // if(i.hasExtra("token"))
               //   Log.d("ff",tk.getValue());
            if(highScore==null)
              {
                  Intent  intent = new Intent(Main2Activity.this, login.class);
                  startActivity(intent);
              }
          else
             {
                 Log.d("aa","qw");
                 String name = i.getStringExtra("token");
                 Intent  intent = new Intent(Main2Activity.this, AddEvent.class);
                 intent.putExtra("token", name);
                 startActivity(intent);
             }
                    }
        else if (id == R.id.nav_gallery) {
            SharedPreferences sharedPref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
            String highScore = sharedPref.getString(getString(R.string.tok), null);
//            Log.d("toki",highScore);
            Intent i=getIntent();
            // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            //      sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            //    int defaultValue = getResources().getInteger(R.string.tok_deflaut);
            // tk ta=new tk(i.getStringExtra("token"));
            //  Log.d("tk", name);
            // if(i.hasExtra("token"))
            //   Log.d("ff",tk.getValue());
            if(highScore==null)
            {
                Intent  intent = new Intent(Main2Activity.this, login.class);
                startActivity(intent);
            }
            else
            {
                Log.d("aa","qw");
                String name = i.getStringExtra("token");
                Intent  intent = new Intent(Main2Activity.this, Going.class);
                intent.putExtra("token", name);
                startActivity(intent);
            }

        }


        //    }
    else if (id == R.id.nav_slideshow) {

            SharedPreferences sharedPref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
            String highScore = sharedPref.getString(getString(R.string.tok), null);
//            Log.d("toki",highScore);
            Intent i=getIntent();
            // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            //      sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            //    int defaultValue = getResources().getInteger(R.string.tok_deflaut);
            // tk ta=new tk(i.getStringExtra("token"));
            //  Log.d("tk", name);
            // if(i.hasExtra("token"))
            //   Log.d("ff",tk.getValue());
            if(highScore==null)
            {
                Intent  intent = new Intent(Main2Activity.this, login.class);
                startActivity(intent);
            }
            else
            {
                Log.d("aa","qw");
                String name = i.getStringExtra("token");
                Intent  intent = new Intent(Main2Activity.this, MyEvent.class);
                intent.putExtra("token", name);
                startActivity(intent);
            }

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPref.edit();
            editor.remove(getString(R.string.tok));
            editor.commit();
            Intent  intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
