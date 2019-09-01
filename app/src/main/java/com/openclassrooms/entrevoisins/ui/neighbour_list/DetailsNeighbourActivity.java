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

    private Neighbour mNeighbour;
    private NeighbourApiService mNeighbourApiService;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__neighbour);
        ButterKnife.bind(this);
        mNeighbourApiService = DI.getNeighbourApiService();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (toolbar != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        intent = getIntent();
        Gson gson = new Gson();
        mNeighbour = gson.fromJson(intent.getStringExtra("json"), Neighbour.class);

        updateUi();

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

    private void updateUi() {
        nameText.setText(mNeighbour.getName());
        nameFacebook.setText(mNeighbour.getName().toLowerCase());

        Glide.with(imageNeighbour.getContext())
                .load(mNeighbour.getAvatarUrl())
                .into(imageNeighbour);

        nameCard.setText(mNeighbour.getName());

        if (mNeighbourApiService.getFavorites().contains(mNeighbour)){
            fab.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }
}

