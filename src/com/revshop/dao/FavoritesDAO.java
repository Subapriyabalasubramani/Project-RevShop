package com.revshop.dao;

import java.util.List;
import com.revshop.model.Favorites;

public interface FavoritesDAO {
	
	void addFavorite(int buyerId, int productId);

    List<Favorites> getFavoritesByBuyer(int buyerId);

    // void removeFavorite(int buyerId, int productId);

}
