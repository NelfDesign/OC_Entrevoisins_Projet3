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
     * check if a neighbour is favorite in the neighbours list
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
     * addFavorite update the boolean to true for the selected neighbour
     * @param neighbour
     */
    @Override
    public void addFavorite(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(true);
    }


    /**
     * deleteFavorite update the boolean to false for the selected neighbour
     * @param neighbour
     */
    @Override
    public void deleteFavorite(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(false);
    }
}
