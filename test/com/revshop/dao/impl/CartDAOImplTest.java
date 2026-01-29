package com.revshop.dao.impl;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

import com.revshop.model.Order;
import com.revshop.model.CartItem;

public class CartDAOImplTest {
	
	private static final int VALID_BUYER_ID = 5;
    private static final int VALID_CART_ID = 1;
    private static final int VALID_PRODUCT_ID = 22;

    @Test
    public void testGetCartIdByBuyer_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        int cartId = dao.getCartIdByBuyer(VALID_BUYER_ID);

        assertTrue(cartId > 0);
    }

    @Test
    public void testViewCart_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        List<CartItem> items = dao.viewCart(VALID_CART_ID);

        assertNotNull(items);
    }

    @Test
    public void testAddToCart_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        // Passes if no exception occurs
        dao.addToCart(VALID_CART_ID, VALID_PRODUCT_ID, 1, 500);
    }

    @Test
    public void testUpdateCartItem_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        // Passes if no exception occurs
        dao.updateCartItem(VALID_CART_ID, VALID_PRODUCT_ID, 2);
    }

    @Test
    public void testRemoveFromCart_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        // Passes if no exception occurs
        dao.removeFromCart(VALID_CART_ID, VALID_PRODUCT_ID);
    }

    @Test
    public void testGetCartIdByBuyer_fail() {
        CartDAOImpl dao = new CartDAOImpl();

        int cartId = dao.getCartIdByBuyer(-1);

        assertEquals(-1, cartId);
    }

    @Test
    public void testViewCart_fail() {
        CartDAOImpl dao = new CartDAOImpl();

        List<CartItem> items = dao.viewCart(-1);

        assertNotNull(items);
        assertTrue(items.isEmpty());
    }
}
