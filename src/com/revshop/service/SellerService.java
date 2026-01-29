package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;

public class SellerService {

	ProductService productService = new ProductService();
	OrderService orderService = new OrderService();
	NotificationService notificationService = new NotificationService();
	ReviewService reviewService = new ReviewService();

	public void showSellerMenu(User seller, Scanner sc) {
		
		if (notificationService.hasUnreadNotifications(seller.getUserId())) {
		    System.out.println("\n You have new notifications!");
		}

		boolean logout = false;

		while (!logout) {
			System.out.println("\n--- Seller Menu ---");
			System.out.println("1. Add a Product");
			System.out.println("2. View My Products");
			System.out.println("3. Update Product");
			System.out.println("4. Delete Product");
			System.out.println("5. View my Orders");
			System.out.println("6. View Reviews and Ratings");
			System.out.println("7. Logout");
			System.out.print("\nEnter a choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				productService.addSellerProduct(seller, sc);
				break;
			case 2:
				productService.viewBySellerProducts(seller.getUserId());
				break;
			case 3:
				productService.updateSellerProduct(seller, sc);
				break;
			case 4:
				productService.deleteSellerProduct(seller, sc);
				break;
			case 5:
				orderService.viewOrdersForSeller(seller, sc);
				notificationService.clearNotifications(seller.getUserId());
				break;
				
			case 6:
			    reviewService.viewReviewsForSellerProducts(seller.getUserId());
			    break;

			case 7:
				System.out.println("Logged out successfully");
				logout = true;
				break;
			default:
				System.out.println("Invalid choice. Try again");
				break;
			}
		}
	}

}
