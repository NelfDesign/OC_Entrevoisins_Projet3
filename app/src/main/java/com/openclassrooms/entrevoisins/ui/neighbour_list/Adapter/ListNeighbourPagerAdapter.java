package com.openclassrooms.entrevoisins.ui.neighbour_list.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.entrevoisins.ui.neighbour_list.Fragments.FavoriteFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.Fragments.NeighbourFragment;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return NeighbourFragment.newInstance();

            case 1 : return FavoriteFragment.newInstance();
        }
        return null;
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}