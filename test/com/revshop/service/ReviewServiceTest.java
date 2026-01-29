package com.revshop.service;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;
import com.revshop.model.User;

public class ReviewServiceTest {

	private static final int VALID_BUYER_ID = 6;
	private static final int VALID_SELLER_ID = 4;

	@Test
	public void testReviewServiceObjectCreation() {
		ReviewService service = new ReviewService();
		assertNotNull(service);
	}

	@Test
	public void testViewReviewsForSellerProducts_pass() {
		ReviewService service = new ReviewService();

		service.viewReviewsForSellerProducts(VALID_SELLER_ID);
	}

	@Test
	public void testAddReview_noPurchasedProducts() {
		ReviewService service = new ReviewService();

		User buyer = new User();
		buyer.setUserId(-1);

		Scanner sc = new Scanner("");

		service.addReview(buyer, sc);
	}
}
