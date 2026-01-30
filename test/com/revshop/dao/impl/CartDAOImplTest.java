package com.revshop.dao.impl;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;
import com.revshop.model.CartItem;

public class CartDAOImplTest {

    @Test
    public void testGetCartIdByBuyer_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        int cartId = dao.getCartIdByBuyer(5);

        assertTrue(cartId > 0);
    }

    @Test
    public void testViewCart_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        List<CartItem> items = dao.viewCart(1);

        assertNotNull(items);
    }

    @Test
    public void testAddToCart_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        // Passes if no exception occurs
        dao.addToCart(1, 22, 1, 500);
    }

    @Test
    public void testUpdateCartItem_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        // Passes if no exception occurs
        dao.updateCartItem(1, 22, 2);
    }

    @Test
    public void testRemoveFromCart_pass() {
        CartDAOImpl dao = new CartDAOImpl();

        // Passes if no exception occurs
        dao.removeFromCart(1, 22);
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
