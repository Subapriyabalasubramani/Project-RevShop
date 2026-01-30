package com.revshop.service;

import static org.mockito.Mockito.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class BuyerServiceTest {

    @Mock private ProductService productService;
    @Mock private CartService cartService;
    @Mock private CheckoutService checkoutService;
    @Mock private OrderService orderService;
    @Mock private FavoritesService favoritesService;
    @Mock private ReviewService reviewService;
    @Mock private UserService userService;

    @InjectMocks
    private BuyerService buyerService;

    private User buyer;

    @Before
    public void setup() {
        buyer = new User();
        buyer.setUserId(5);

        buyerService.setProductService(productService);
        buyerService.setCartService(cartService);
        buyerService.setCheckoutService(checkoutService);
        buyerService.setOrderService(orderService);
        buyerService.setFavoritesService(favoritesService);
        buyerService.setReviewService(reviewService);
        buyerService.setUserService(userService);
    }

    @Test
    public void testBuyerMenu_viewProductDetails() {

        Scanner sc = new Scanner(
            "1\n" +  // choice
            "1\n" +  // productId
            "12\n"   // logout
        );

        buyerService.showBuyerMenu(buyer, sc);

        verify(productService, atLeastOnce()).showAllProductsForBuyer();
        verify(productService, atLeastOnce()).viewProductDetails(any(Scanner.class));
    }
    
    @Test
    public void testBuyerMenu_addToCart() {

        Scanner sc = new Scanner(
            "3\n" +
            "12\n"
        );

        buyerService.showBuyerMenu(buyer, sc);

        verify(productService).showAllProductsForBuyer();
        verify(cartService).addToCart(eq(buyer), any(Scanner.class));
    }

    @Test
    public void testBuyerMenu_viewCart() {

        Scanner sc = new Scanner(
            "4\n" +
            "12\n"
        );

        buyerService.showBuyerMenu(buyer, sc);

        verify(cartService).viewCart(buyer);
    }

    @Test
    public void testBuyerMenu_checkout() {

        Scanner sc = new Scanner(
            "7\n" +
            "12\n"
        );

        buyerService.showBuyerMenu(buyer, sc);

        verify(checkoutService).checkout(eq(buyer), any(Scanner.class));
    }
    
    @Test
    public void testBuyerMenu_orderHistory() {

        Scanner sc = new Scanner(
            "8\n" +
            "0\n" +
            "12\n"
        );

        buyerService.showBuyerMenu(buyer, sc);

        verify(orderService).viewOrderHistory(eq(buyer), any(Scanner.class));
    }

    @Test
    public void testBuyerMenu_addToFavorites() {

        Scanner sc = new Scanner(
            "9\n" +
            "12\n"
        );

        buyerService.showBuyerMenu(buyer, sc);

        verify(favoritesService).addToFavorites(eq(buyer), any(Scanner.class));
    }

    @Test
    public void testBuyerMenu_addReview() {

        Scanner sc = new Scanner(
            "10\n" +
            "12\n"
        );

        buyerService.showBuyerMenu(buyer, sc);

        verify(reviewService).addReview(eq(buyer), any(Scanner.class));
    }

    @Test
    public void testBuyerMenu_logout() {

        Scanner sc = new Scanner("12\n");

        buyerService.showBuyerMenu(buyer, sc);

        verifyNoMoreInteractions(
            productService,
            cartService,
            checkoutService,
            orderService,
            favoritesService,
            reviewService,
            userService
        );
    }
}
