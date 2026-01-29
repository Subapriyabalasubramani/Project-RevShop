package com.revshop.dao;

import java.util.List;

import com.revshop.model.Review;

public interface ReviewDAO {
	
	List<Review> getReviewsByProductId(int productId);
}
