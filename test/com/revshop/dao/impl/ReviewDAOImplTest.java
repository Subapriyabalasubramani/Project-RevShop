package com.revshop.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revshop.model.Review;

public class ReviewDAOImplTest {
	
	private static final int VALID_PRODUCT_ID = 22;
    private static final int VALID_BUYER_ID = 6;
    private static final int VALID_SELLER_ID = 4;
    
    @Test
    public void testGetReviewsByProductId_pass() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        List<Review> reviews = dao.getReviewsByProductId(VALID_PRODUCT_ID);

        assertNotNull(reviews);
    }
    
    @Test
    public void testGetReviewsBySeller_pass() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        List<Review> reviews = dao.getReviewsBySeller(VALID_SELLER_ID);

        assertNotNull(reviews);
    }

    @Test
    public void testAddReview_pass() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        dao.addReview(VALID_BUYER_ID, VALID_PRODUCT_ID, 5, "JUnit simple test review");
    }
    
    @Test
    public void testGetReviewsByProductId_fail() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        List<Review> reviews = dao.getReviewsByProductId(-1);

        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }

    @Test
    public void testGetReviewsBySeller_fail() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        List<Review> reviews = dao.getReviewsBySeller(-1);

        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }

    @Test
    public void testHasBuyerPurchasedProduct_fail() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        boolean result = dao.hasBuyerPurchasedProduct(-1, -1);

        assertFalse(result);
    }
}
