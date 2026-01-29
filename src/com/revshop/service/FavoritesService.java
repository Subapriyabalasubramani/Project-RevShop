package com.revshop.service;

import java.util.List;
import java.util.Scanner;

import com.revshop.dao.FavoritesDAO;
import com.revshop.dao.impl.FavoritesDAOImpl;
import com.revshop.model.Favorites;
import com.revshop.model.User;

public class FavoritesService {

	private FavoritesDAO favoriteDAO = new FavoritesDAOImpl();

	public void addToFavorites(User buyer, Scanner sc) {
		
		ProductService productService = new ProductService();
		productService.showAllProductsForBuyer();

		System.out.print("\nEnter Product ID to favorite: ");
		int productId = sc.nextInt();
		sc.nextLine();

		favoriteDAO.addFavorite(buyer.getUserId(), productId);
		
		viewFavorites(buyer);
	}

	public void viewFavorites(User buyer) {

		List<Favorites> favorites = favoriteDAO.getFavoritesByBuyer(buyer
				.getUserId());

		if (favorites.isEmpty()) {
			System.out.println("No favorite products");
			return;
		}

		System.out.println("\n--- Your Favorites ---");
		for (Favorites f : favorites) {
			System.out.println("Product ID: " + f.getProductId() + " | Name: "
					+ f.getProductName());
		}
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
