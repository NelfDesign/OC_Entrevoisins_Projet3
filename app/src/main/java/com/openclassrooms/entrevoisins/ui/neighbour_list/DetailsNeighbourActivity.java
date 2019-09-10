package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.toolbarDetails)
    Toolbar toolbar;
    @BindView(R.id.nameDetails)
    TextView nameText;
    @BindView(R.id.nameFacebook)
    TextView nameFacebook;
    @BindView(R.id.imageNeighbour)
    ImageView imageNeighbour;
    @BindView(R.id.nameCard)
    TextView nameCard;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_details)
    CoordinatorLayout mCoordinatorLayout;

    //Fields
    private Neighbour mNeighbour;
    private NeighbourApiService mNeighbourApiService;
    private Intent mIntent;

    // method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__neighbour);
        ButterKnife.bind(this);
        mNeighbourApiService = DI.getNeighbourApiService();

        //Add toolbar and return arrow
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (toolbar != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //get data from json to neighbour
        mIntent = getIntent();
        Gson gson = new Gson();
        mNeighbour = gson.fromJson(mIntent.getStringExtra("json"), Neighbour.class);
        //update UI
        updateUi();

        //Listen click on FAB button to add neighbour to favorite or not
        fab.setOnClickListener(view -> {
            if (!mNeighbourApiService.getFavorites().contains(mNeighbour)) {
                mNeighbourApiService.addFavorite(mNeighbour);
                fab.setImageResource(R.drawable.ic_star_yellow_24dp);
                Snackbar.make(mCoordinatorLayout, "Add to favorite", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mCoordinatorLayout, mNeighbour.getName() + " is also favorite", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * set the UI fiels with the neighbour data
     */
    private void updateUi() {
        nameText.setText(mNeighbour.getName());
        nameFacebook.setText(mNeighbour.getName().toLowerCase());

        Glide.with(imageNeighbour.getContext())
                .load(mNeighbour.getAvatarUrl())
                .into(imageNeighbour);

        nameCard.setText(mNeighbour.getName());

        //check if the neighbour is favorite
        if (mNeighbourApiService.getFavorites().contains(mNeighbour)){
            fab.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }
}

