package com.revshop.service;

import static org.mockito.Mockito.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.dao.FavoritesDAO;
import com.revshop.model.Favorites;
import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class FavoritesServiceTest {

    @Mock
    private FavoritesDAO favoritesDAO;

    @Mock
    private ProductService productService;

    @InjectMocks
    private FavoritesService favoritesService;

    private User buyer;

    @Before
    public void setup() {
        buyer = new User();
        buyer.setUserId(7);

        favoritesService.setFavoriteDAO(favoritesDAO);
        favoritesService.setProductService(productService);
    }

    @Test
    public void testAddToFavorites_success() {

        when(favoritesDAO.getFavoritesByBuyer(7))
            .thenReturn(Collections.<Favorites>emptyList());

        Scanner sc = new Scanner(
            "101\n"
        );

        favoritesService.addToFavorites(buyer, sc);

        verify(productService).showAllProductsForBuyer();
        verify(favoritesDAO).addFavorite(7, 101);
        verify(favoritesDAO).getFavoritesByBuyer(7);
    }

    @Test
    public void testViewFavorites_noFavorites() {

        when(favoritesDAO.getFavoritesByBuyer(7))
            .thenReturn(Collections.<Favorites>emptyList());

        favoritesService.viewFavorites(buyer);

        verify(favoritesDAO).getFavoritesByBuyer(7);
    }

    @Test
    public void testViewFavorites_withFavorites() {

        Favorites f = new Favorites();
        f.setProductId(101);
        f.setProductName("Phone");

        when(favoritesDAO.getFavoritesByBuyer(7))
            .thenReturn(Arrays.asList(f));

        favoritesService.viewFavorites(buyer);

        verify(favoritesDAO).getFavoritesByBuyer(7);
    }
}
