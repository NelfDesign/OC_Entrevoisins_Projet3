package com.openclassrooms.entrevoisins.ui.neighbour_list.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.Activity.DetailsNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.Adapter.MyNeighbourRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Nelfdesign at 30/08/2019
 * com.openclassrooms.entrevoisins.ui.neighbour_list
 */
public class FavoriteFragment extends Fragment implements MyNeighbourRecyclerViewAdapter.onItemListener {

    private NeighbourApiService mApiService;
    private List<Neighbour> favoritesList;
    private RecyclerView mRecyclerView;

    public FavoriteFragment() { }

    /**
     * Create and return a new instance
     * @return @{@link FavoriteFragment}
     */
    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initListFavorite();
        return view;
    }

    /**
     * Init the List of favorite neighbours
     */
    private void initListFavorite() {
        favoritesList = mApiService.getFavorites();
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(favoritesList, this));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    //method called by the back button
    @Override
    public void onResume() {
        super.onResume();
        initListFavorite();
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteFavorite(DeleteFavoriteEvent event) {
        mApiService.deleteFavorite(event.neighbour);
        initListFavorite();
    }

    /**
     * listen the click on a item and go to the detail page
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Gson gson = new Gson();
        String json = gson.toJson(favoritesList.get(position));
        Context context = getContext();
        Intent intent = new Intent(context, DetailsNeighbourActivity.class);
        intent.putExtra("json", json);
        startActivity(intent);
    }
}
