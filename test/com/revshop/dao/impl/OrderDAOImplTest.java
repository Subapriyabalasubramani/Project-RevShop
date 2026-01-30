package com.revshop.dao.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.revshop.model.CartItem;
import com.revshop.model.Order;

public class OrderDAOImplTest {
    
    @Test
    public void testCreateOrder_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        Order order = new Order();
        order.setBuyerId(5);
        order.setShippingAddress("Test Address");
        order.setBillingAddress("Test Address");
        order.setTotalAmount(1000);

        int orderId = dao.createOrder(order);

        assertTrue(orderId > 0);
    }

    @Test
    public void testGetOrdersByBuyer_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<Order> orders = dao.getOrdersByBuyer(5);

        assertNotNull(orders);
    }

    @Test
    public void testGetOrderItems_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<CartItem> items = dao.getOrderItems(1);

        assertNotNull(items);
    }

    @Test
    public void testHasOrdersForSeller_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        boolean result = dao.hasOrdersForSeller(4);

        assertTrue(result);
    }

    @Test
    public void testGetOrdersForSeller_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<Order> orders = dao.getOrdersForSeller(4);

        assertNotNull(orders);
    }

    @Test
    public void testGetPurchasedProducts_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        List<CartItem> items = dao.getPurchasedProducts(5);

        assertNotNull(items);
    }

    @Test
    public void testUpdatePaymentDetails_pass() {
        OrderDAOImpl dao = new OrderDAOImpl();

        dao.updatePaymentDetails(1, "COD", "SUCCESS");
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
