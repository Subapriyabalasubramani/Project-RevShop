package com.revshop.service;

import java.util.List;

import com.revshop.dao.OrderDAO;
import com.revshop.dao.ReviewDAO;
import com.revshop.dao.impl.OrderDAOImpl;
import com.revshop.dao.impl.ReviewDAOImpl;
import com.revshop.model.CartItem;
import com.revshop.model.Review;
import com.revshop.model.User;
import java.util.Scanner;
import java.util.logging.Logger;


public class ReviewService {
	private static final Logger LOGGER =
	        Logger.getLogger(ReviewService.class.getName());

	public void viewReviewsForSellerProducts(int sellerId) {
		LOGGER.info("Seller requested reviews for products. sellerId: " + sellerId);

		ReviewDAO reviewDAO = new ReviewDAOImpl();
		
	    List<Review> reviews = reviewDAO.getReviewsBySeller(sellerId);

	    if (reviews.isEmpty()) {
	    	LOGGER.info("No reviews found for sellerId: " + sellerId);
	        System.out.println("No reviews available for your products.");
	        return;
	    }

	    System.out.println("\n----- Product Reviews -----");

	    for (Review r : reviews) {
	        System.out.println(
	            "Product: " + r.getProductName() +
	            " | Rating: " + r.getRating()
	        );
	        System.out.println("Comment: " + r.getComment());
	        System.out.println("----------------------------------");
	    }
	}
	
	public void addReview(User buyer, Scanner sc) {
		LOGGER.info("Add review initiated by buyerId: " + buyer.getUserId());


	    OrderDAO orderDAO = new OrderDAOImpl();
	    ReviewDAO reviewDAO = new ReviewDAOImpl();

	    List<CartItem> purchasedItems =
	        orderDAO.getPurchasedProducts(buyer.getUserId());

	    if (purchasedItems.isEmpty()) {
	    	LOGGER.info("Buyer has no purchased products to review. buyerId: " + buyer.getUserId());
	        System.out.println("You have not purchased any products yet.");
	        return;
	    }

	    System.out.println("\n--- Purchased Products ---");
	    for (CartItem item : purchasedItems) {
	        System.out.println(
	            "Product ID: " + item.getProductId() +
	            " | Name: " + item.getProductName()
	        );
	    }

	    System.out.print("\nEnter Product ID to review: ");
	    int productId = sc.nextInt();
	    sc.nextLine();

	    // Safety check
	    boolean valid = false;
	    for (CartItem item : purchasedItems) {
	        if (item.getProductId() == productId) {
	            valid = true;
	            break;
	        }
	    }

	    if (!valid) {
	    	LOGGER.warning("Invalid product selected for review. buyerId: " +
	                   buyer.getUserId() + ", productId: " + productId);
	        System.out.println("Invalid product selection.");
	        return;
	    }

	    System.out.print("Enter Rating (1 to 5): ");
	    int rating = sc.nextInt();
	    sc.nextLine();

	    System.out.print("Enter Comment: ");
	    String comment = sc.nextLine();

	    reviewDAO.addReview(
	        buyer.getUserId(),
	        productId,
	        rating,
	        comment
	    );
	    LOGGER.info("Review added successfully. buyerId: " +
	            buyer.getUserId() + ", productId: " + productId);
	}

}
