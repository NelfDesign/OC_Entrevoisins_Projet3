package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favoritesList = new ArrayList<>();

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
        return favoritesList;
    }

    @Override
    public void addFavorite(Neighbour neighbour) {
        neighbour.setFavorite(true);
        favoritesList.add(neighbour);
    }

    @Override
    public void deleteFavorite(Neighbour neighbour) {
        favoritesList.remove(neighbour);
    }
}
