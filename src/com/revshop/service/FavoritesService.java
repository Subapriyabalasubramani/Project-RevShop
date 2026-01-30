package com.revshop.service;

import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.revshop.dao.FavoritesDAO;
import com.revshop.dao.impl.FavoritesDAOImpl;
import com.revshop.model.Favorites;
import com.revshop.model.User;

public class FavoritesService {
	private static final Logger logger =
            Logger.getLogger(FavoritesService.class);


	private FavoritesDAO favoriteDAO = new FavoritesDAOImpl();
	ProductService productService = new ProductService();
	
    void setFavoriteDAO(FavoritesDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    void setProductService(ProductService productService) {
        this.productService = productService;
    }

	public void addToFavorites(User buyer, Scanner sc) {
		logger.info("Add to favorites initiated. buyerId: " + buyer.getUserId());
		
		productService.showAllProductsForBuyer();

		System.out.print("\nEnter Product ID to favorite: ");
		int productId = sc.nextInt();
		sc.nextLine();

		favoriteDAO.addFavorite(buyer.getUserId(), productId);
		logger.info("Product added to favorites. buyerId: " +
	            buyer.getUserId() + ", productId: " + productId);
		
		viewFavorites(buyer);
	}

	public void viewFavorites(User buyer) {
		logger.info("View favorites requested. buyerId: " + buyer.getUserId());

		List<Favorites> favorites = favoriteDAO.getFavoritesByBuyer(buyer
				.getUserId());

		if (favorites.isEmpty()) {
			logger.info("No favorites found for buyerId: " + buyer.getUserId());
			System.out.println("No favorite products");
			return;
		}

		System.out.println("\n--- Your Favorites ---");
		for (Favorites f : favorites) {
			System.out.println("Product ID: " + f.getProductId() + " | Name: "
					+ f.getProductName());
		}
		logger.info("Displaying favorites list. buyerId: " + buyer.getUserId());
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
