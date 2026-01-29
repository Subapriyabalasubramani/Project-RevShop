package com.revshop.service;

import static org.junit.Assert.*;
import org.junit.Test;
import com.revshop.model.User;

public class FavoritesServiceTest {

	private static final int VALID_BUYER_ID = 6;

	@Test
	public void testFavoritesServiceObjectCreation() {
		FavoritesService service = new FavoritesService();
		assertNotNull(service);
	}

	@Test
	public void testViewFavorites_pass() {
		FavoritesService service = new FavoritesService();

		User buyer = new User();
		buyer.setUserId(VALID_BUYER_ID);

		service.viewFavorites(buyer);
	}

	@Test
	public void testViewFavorites_fail() {
		FavoritesService service = new FavoritesService();

		User buyer = new User();
		buyer.setUserId(-1);

		service.viewFavorites(buyer);
	}
}
