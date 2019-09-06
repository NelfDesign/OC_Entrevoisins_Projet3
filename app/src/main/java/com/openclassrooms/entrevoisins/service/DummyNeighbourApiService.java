package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    //private List<Neighbour> favoritesList = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     *
     * @return list of favorite neighbours
     */
    @Override
    public List<Neighbour> getFavorites() {
        List<Neighbour> tmp = new ArrayList<>();
        for (Neighbour n : neighbours){
            if (n.isFavorite()){
               tmp.add(n);
            }
        }
        return tmp;
    }

    /**
     * Add a neighbour to favorite list
     * @param neighbour
     */
    @Override
    public void addFavorite(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(true);
        //neighbour.setFavorite(true);
        //favoritesList.add(neighbour);
    }


    /**
     * Delete a neighbour to the list
     * @param neighbour
     */
    @Override
    public void deleteFavorite(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(false);
    }
}
