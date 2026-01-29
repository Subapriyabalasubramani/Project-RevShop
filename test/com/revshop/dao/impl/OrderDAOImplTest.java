package com.revshop.dao.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.revshop.model.CartItem;
import com.revshop.model.Order;

public class OrderDAOImplTest {
	
	private static final int VALID_BUYER_ID = 5;
    private static final int VALID_SELLER_ID = 4;
    private static final int VALID_PRODUCT_ID = 22;
    private static final int VALID_ORDER_ID = 1;
    private static final int VALID_CART_ID = 1;
    
    @Test
    public void testCreateOrder_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        Order order = new Order();
        order.setBuyerId(VALID_BUYER_ID);
        order.setShippingAddress("Test Address");
        order.setBillingAddress("Test Address");
        order.setTotalAmount(1000);

        int orderId = dao.createOrder(order);

        assertTrue(orderId > 0);
    }

    @Test
    public void testGetOrdersByBuyer_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<Order> orders = dao.getOrdersByBuyer(VALID_BUYER_ID);

        assertNotNull(orders);
    }

    @Test
    public void testGetOrderItems_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<CartItem> items = dao.getOrderItems(VALID_ORDER_ID);

        assertNotNull(items);
    }

    @Test
    public void testHasOrdersForSeller_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        boolean result = dao.hasOrdersForSeller(VALID_SELLER_ID);

        assertTrue(result);
    }

    @Test
    public void testGetOrdersForSeller_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<Order> orders = dao.getOrdersForSeller(VALID_SELLER_ID);

        assertNotNull(orders);
    }

    @Test
    public void testGetPurchasedProducts_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<CartItem> items = dao.getPurchasedProducts(VALID_BUYER_ID);

        assertNotNull(items);
    }

    @Test
    public void testUpdatePaymentDetails_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        dao.updatePaymentDetails(VALID_ORDER_ID, "COD", "SUCCESS");
        // Passes if no exception
    }

    @Test
    public void testGetOrdersByBuyer_fail() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<Order> orders = dao.getOrdersByBuyer(-1);

        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    @Test
    public void testGetOrderItems_fail() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<CartItem> items = dao.getOrderItems(-1);

        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    @Test
    public void testHasOrdersForSeller_fail() {
        OrderDAOImpl dao = new OrderDAOImpl();

        boolean result = dao.hasOrdersForSeller(-1);

        assertFalse(result);
    }

    @Test
    public void testGetOrdersForSeller_fail() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<Order> orders = dao.getOrdersForSeller(-1);

        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    @Test
    public void testGetPurchasedProducts_fail() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<CartItem> items = dao.getPurchasedProducts(-1);

        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

}
