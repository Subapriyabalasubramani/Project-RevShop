package com.revshop.service;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


import com.revshop.dao.FavoritesDAO;
import com.revshop.dao.impl.FavoritesDAOImpl;
import com.revshop.model.Favorites;
import com.revshop.model.User;

public class FavoritesService {
	private static final Logger LOGGER =
	        Logger.getLogger(FavoritesService.class.getName());


	private FavoritesDAO favoriteDAO = new FavoritesDAOImpl();

	public void addToFavorites(User buyer, Scanner sc) {
		LOGGER.info("Add to favorites initiated. buyerId: " + buyer.getUserId());
		
		ProductService productService = new ProductService();
		productService.showAllProductsForBuyer();

		System.out.print("\nEnter Product ID to favorite: ");
		int productId = sc.nextInt();
		sc.nextLine();

		favoriteDAO.addFavorite(buyer.getUserId(), productId);
		LOGGER.info("Product added to favorites. buyerId: " +
	            buyer.getUserId() + ", productId: " + productId);
		
		viewFavorites(buyer);
	}

	public void viewFavorites(User buyer) {
		LOGGER.info("View favorites requested. buyerId: " + buyer.getUserId());

		List<Favorites> favorites = favoriteDAO.getFavoritesByBuyer(buyer
				.getUserId());

		if (favorites.isEmpty()) {
			LOGGER.info("No favorites found for buyerId: " + buyer.getUserId());
			System.out.println("No favorite products");
			return;
		}

		System.out.println("\n--- Your Favorites ---");
		for (Favorites f : favorites) {
			System.out.println("Product ID: " + f.getProductId() + " | Name: "
					+ f.getProductName());
		}
		LOGGER.info("Displaying favorites list. buyerId: " + buyer.getUserId());
	}

//	public void removeFromFavorites(User buyer, Scanner sc) {
//
//		System.out.print("Enter Product ID to remove: ");
//		int productId = sc.nextInt();
//		sc.nextLine();
//
//		favoriteDAO.removeFavorite(buyer.getUserId(), productId);
//	}
}
