package com.example.indiameets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import org.json.JSONArray;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * Created by sony on 27-11-2015.
 */


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>  {
    List<person> persons;
    RVAdapter(List<person> persons) {
        this.persons = persons;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position) {
        holder.personName.setText(persons.get(position).name);
        holder.personAge.setText(String.valueOf(persons.get(position).age));
        new DownloadImageTask(holder.images).execute(persons.get(position).url);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // perform your operations hereIn
                Intent intent = new Intent(holder.itemView.getContext(), Template.class);
                Bundle b = new Bundle();
                intent.putExtra("name", persons.get(position).name);
                intent.putExtra("age", persons.get(position).age);
                intent.putExtra("url",persons.get(position).url);
                intent.putExtra("time",persons.get(position).time);
                intent.putExtra("location",persons.get(position).location);

                //intent.putExtra("join",persons.get(position).join);

                v.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (persons != null) {
            return persons.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView images;



        PersonViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_age);
            images=(ImageView)itemView.findViewById(R.id.thumbnail);


        }

    }


    // show The Image

////
//   public void onClick(View v) {
//
//       Intent intent = new Intent(Main2Activity.this, login.class);
//       startActivity(intent);
//
//    }

  public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

      protected void onPreExecute (){

      }

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
           URI url= new File(urldisplay).toURI();
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
}


