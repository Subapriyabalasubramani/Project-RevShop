package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;
import java.util.logging.Logger;

public class SellerService {

	private static final Logger LOGGER = Logger.getLogger(SellerService.class
			.getName());

	ProductService productService = new ProductService();
	OrderService orderService = new OrderService();
	NotificationService notificationService = new NotificationService();
	ReviewService reviewService = new ReviewService();
	UserService userService = new UserService();

	void setProductService(ProductService productService) {
		this.productService = productService;
	}

	void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	void setReviewService(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void showSellerMenu(User seller, Scanner sc) {
		LOGGER.info("Seller menu started for sellerId: " + seller.getUserId());

		if (notificationService.hasUnreadNotifications(seller.getUserId())) {
			LOGGER.info("Seller has unread notifications. sellerId: "
					+ seller.getUserId());
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
			System.out.println("7. Change Password");
			System.out.println("8. Logout");
			System.out.print("\nEnter a choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			LOGGER.info("Seller selected menu option: " + choice
					+ " sellerId: " + seller.getUserId());

			switch (choice) {
			case 1:
				LOGGER.info("Seller chose: Add Product");
				productService.addSellerProduct(seller, sc);
				break;
			case 2:
				LOGGER.info("Seller chose: View My Products");
				productService.viewBySellerProducts(seller.getUserId());
				break;
			case 3:
				LOGGER.info("Seller chose: Update Product");
				productService.updateSellerProduct(seller, sc);
				break;
			case 4:
				LOGGER.info("Seller chose: Delete Product");
				productService.deleteSellerProduct(seller, sc);
				break;
			case 5:
				LOGGER.info("Seller chose: View Orders");
				orderService.viewOrdersForSeller(seller, sc);
				notificationService.clearNotifications(seller.getUserId());
				break;

			case 6:
				LOGGER.info("Seller chose: View Reviews");
				reviewService.viewReviewsForSellerProducts(seller.getUserId());
				break;

			case 7:
				LOGGER.info("Seller chose: Change Password");
				userService.changePassword(seller, sc);
				break;

			case 8:
				LOGGER.info("Seller logged out. sellerId: "
						+ seller.getUserId());
				System.out.println("Logged out successfully");
				logout = true;
				break;
			default:
				LOGGER.warning("Invalid menu choice entered by seller: "
						+ choice + " sellerId: " + seller.getUserId());
				System.out.println("Invalid choice. Try again");
				break;
			}
		}
	}

}
