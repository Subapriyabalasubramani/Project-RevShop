package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;

public class BuyerService {

	ProductService productService = new ProductService();
	CartService cartService = new CartService();
	CheckoutService checkoutService = new CheckoutService();
	OrderService orderService = new OrderService();

	public void showBuyerMenu(User buyer, Scanner sc) {
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
			System.out.println("9. Logout");
			System.out.print("\nEnter choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				productService.showAllProductsForBuyer();
				productService.viewProductDetails(sc);
				break;
			case 2:
				productService.browseOrSearchProducts(sc);
				break;
			case 3:
				productService.showAllProductsForBuyer();
				cartService.addToCart(buyer, sc);
				break;

			case 4:
				cartService.viewCart(buyer);
				break;

			case 5:
				cartService.updateCart(buyer, sc);
				break;

			case 6:
				cartService.removeFromCart(buyer, sc);
				break;
				
			case 7:
				checkoutService.checkout(buyer, sc);
				break;
			
			case 8:
				orderService.viewOrderHistory(buyer, sc);
				break;

			case 9:
				logout = true;
				System.out.println("Logged out successfully");
				break;
				
			default:
				System.out.println("Invalid choice");
			}
		}
	}

}
