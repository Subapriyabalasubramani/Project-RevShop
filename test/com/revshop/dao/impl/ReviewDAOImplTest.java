package com.revshop.dao.impl;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

import com.revshop.model.Review;

public class ReviewDAOImplTest {
    
    @Test
    public void testGetReviewsByProductId_pass() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        List<Review> reviews = dao.getReviewsByProductId(22);

        assertNotNull(reviews);
    }
    
    @Test
    public void testGetReviewsBySeller_pass() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        List<Review> reviews = dao.getReviewsBySeller(4);

        assertNotNull(reviews);
    }

    @Test
    public void testAddReview_pass() {
        ReviewDAOImpl dao = new ReviewDAOImpl();

        dao.addReview(6, 22, 5, "JUnit simple test review");
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
