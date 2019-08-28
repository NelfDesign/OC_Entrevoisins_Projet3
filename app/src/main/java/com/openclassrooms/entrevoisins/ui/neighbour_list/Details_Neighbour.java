package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Details_Neighbour extends AppCompatActivity {


    private Toolbar toolbar;
    private Intent intent;
    private TextView nameText;
    private TextView nameFacebook;
    private ImageView imageNeighbour;
    private TextView nameCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__neighbour);

        toolbar = findViewById(R.id.toolbarDetails);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (toolbar != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        intent = getIntent();
        //intent.getCategories();
        nameText = findViewById(R.id.nameDetails);
        nameText.setText(intent.getStringExtra("name"));
        nameFacebook = findViewById(R.id.nameFacebook);
        nameFacebook.setText(intent.getStringExtra("facebook"));
        imageNeighbour = findViewById(R.id.imageNeighbour);

        Glide.with(imageNeighbour.getContext())
                .load(intent.getStringExtra("uri"))
                .into(imageNeighbour);

        nameCard = findViewById(R.id.nameCard);
        nameCard.setText(intent.getStringExtra("name"));


    }
}

