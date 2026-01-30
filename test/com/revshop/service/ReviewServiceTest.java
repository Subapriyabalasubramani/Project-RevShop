package com.revshop.service;

import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.dao.OrderDAO;
import com.revshop.dao.ReviewDAO;
import com.revshop.model.CartItem;
import com.revshop.model.Review;
import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {

    @Mock
    private ReviewDAO reviewDAO;

    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private ReviewService reviewService;

    private User buyer;

    @Before
    public void setup() {
        buyer = new User();
        buyer.setUserId(6);

        reviewService.setReviewDAO(reviewDAO);
        reviewService.setOrderDAO(orderDAO);
    }

    @Test
    public void testViewReviewsForSellerProducts_withReviews() {

        Review r = new Review();
        r.setProductName("Laptop");
        r.setRating(5);
        r.setComment("Excellent");

        when(reviewDAO.getReviewsBySeller(4))
            .thenReturn(Arrays.asList(r));

        reviewService.viewReviewsForSellerProducts(4);

        verify(reviewDAO).getReviewsBySeller(4);
    }

    @Test
    public void testViewReviewsForSellerProducts_noReviews() {

        when(reviewDAO.getReviewsBySeller(4))
            .thenReturn(Collections.<Review>emptyList());

        reviewService.viewReviewsForSellerProducts(4);

        verify(reviewDAO).getReviewsBySeller(4);
        verifyNoMoreInteractions(reviewDAO);
    }

    @Test
    public void testAddReview_noPurchasedProducts() {

        when(orderDAO.getPurchasedProducts(6))
            .thenReturn(Collections.<CartItem>emptyList());

        Scanner sc = new Scanner("");

        reviewService.addReview(buyer, sc);

        verify(orderDAO).getPurchasedProducts(6);
        verify(reviewDAO, never())
            .addReview(anyInt(), anyInt(), anyInt(), anyString());
    }

    @Test
    public void testAddReview_success() {

        CartItem item = new CartItem();
        item.setProductId(101);
        item.setProductName("Phone");

        when(orderDAO.getPurchasedProducts(6))
            .thenReturn(Arrays.asList(item));

        Scanner sc = new Scanner(
            "101\n" +    // productId
            "5\n" +      // rating
            "Great!\n"   // comment
        );

        reviewService.addReview(buyer, sc);

        verify(reviewDAO).addReview(
            eq(6),
            eq(101),
            eq(5),
            eq("Great!")
        );
    }
}
