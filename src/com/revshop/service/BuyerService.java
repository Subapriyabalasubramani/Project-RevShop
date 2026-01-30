package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;
import java.util.logging.Logger;

public class BuyerService {

	ProductService productService = new ProductService();
	CartService cartService = new CartService();
	CheckoutService checkoutService = new CheckoutService();
	OrderService orderService = new OrderService();
	FavoritesService favoritesService = new FavoritesService();
	ReviewService reviewService = new ReviewService();
	UserService userService = new UserService();
	
	private static final Logger LOGGER = Logger.getLogger(BuyerService.class
			.getName());

	public void showBuyerMenu(User buyer, Scanner sc) {
		LOGGER.info("Buyer menu started for buyerId: " + buyer.getUserId());

		boolean logout = false;

		while (!logout) {
			System.out.println("\n--- Buyer Menu ---");
			System.out.println("1. View Product Details");
			System.out.println("2. Browse/Search products");
			System.out.println("3. Add to Cart");
			System.out.println("4. View Cart");
			System.out.println("5. Update Cart");
			System.out.println("6. Remove from Cart");
			System.out.println("7. Checkout");
			System.out.println("8. Order History");
			System.out.println("9. Save to favorties");
			System.out.println("10. Review & Ratings");
			System.out.println("11. Change Password");
			System.out.println("12. Logout");
			System.out.print("\nEnter choice: ");

			int choice = sc.nextInt();
			sc.nextLine();
			LOGGER.info("Buyer selected menu option: " + choice + " buyerId: "
					+ buyer.getUserId());

			switch (choice) {
			case 1:
				LOGGER.info("Buyer chose: View Product Details");
				productService.showAllProductsForBuyer();
				productService.viewProductDetails(sc);
				break;
			case 2:
				LOGGER.info("Buyer chose: Browse/Search Products");
				productService.browseOrSearchProducts(sc);
				break;
			case 3:
				LOGGER.info("Buyer chose: Add to Cart");
				productService.showAllProductsForBuyer();
				cartService.addToCart(buyer, sc);
				break;

			case 4:
				LOGGER.info("Buyer chose: View Cart");
				cartService.viewCart(buyer);
				break;

			case 5:
				LOGGER.info("Buyer chose: Update Cart");
				cartService.updateCart(buyer, sc);
				break;

			case 6:
				LOGGER.info("Buyer chose: Remove from Cart");
				cartService.removeFromCart(buyer, sc);
				break;

			case 7:
				LOGGER.info("Buyer chose: Checkout");
				checkoutService.checkout(buyer, sc);
				break;

			case 8:
				LOGGER.info("Buyer chose: View Order History");
				orderService.viewOrderHistory(buyer, sc);
				break;

			case 9:
				LOGGER.info("Buyer chose: Add to Favorites");
				favoritesService.addToFavorites(buyer, sc);
				break;

			case 10:
				LOGGER.info("Buyer chose: Add Review");
				reviewService.addReview(buyer, sc);
				break;
				
			case 11:
				LOGGER.info("Buyer chose: Change Password");
				userService.changePassword(buyer, sc);
				break;

			case 12:
				LOGGER.info("Buyer logged out. buyerId: " + buyer.getUserId());
				logout = true;
				System.out.println("Logged out successfully");
				break;

			default:
				LOGGER.warning("Invalid menu choice entered by buyer: "
						+ choice + " buyerId: " + buyer.getUserId());
				System.out.println("Invalid choice");
			}
		}
	}

}
