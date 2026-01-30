package com.revshop.dao.impl;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

import com.revshop.model.Favorites;

public class FavoritesDAOImplTest {

    @Test
    public void testAddFavorite_pass() {
        FavoritesDAOImpl dao = new FavoritesDAOImpl();

        // Passes if no exception occurs
        dao.addFavorite(5, 25);
    }

    @Test
    public void testGetFavoritesByBuyer_pass() {
        FavoritesDAOImpl dao = new FavoritesDAOImpl();

        List<Favorites> favorites = dao.getFavoritesByBuyer(5);

        assertNotNull(favorites);
        // may be empty or non-empty
    }

    @Test
    public void testGetFavoritesByBuyer_fail() {
        FavoritesDAOImpl dao = new FavoritesDAOImpl();

        List<Favorites> favorites = dao.getFavoritesByBuyer(-1);

        assertNotNull(favorites);
        assertTrue(favorites.isEmpty());
    }
}
