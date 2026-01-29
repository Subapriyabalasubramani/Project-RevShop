package com.revshop.dao;

import java.util.List;

import com.revshop.model.Review;

public interface ReviewDAO {
	
	List<Review> getReviewsByProductId(int productId);
	
	List<Review> getReviewsBySeller(int sellerId);
	
	void addReview(int buyerId, int productId, int rating, String comment);

	boolean hasBuyerPurchasedProduct(int buyerId, int productId);
}
