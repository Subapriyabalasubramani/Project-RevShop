package com.revshop.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.dao.OrderDAO;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private OrderService orderService;

    private User buyer;
    private User seller;

    @Before
    public void setup() {
        buyer = new User();
        buyer.setUserId(5);

        seller = new User();
        seller.setUserId(10);

        orderService.setOrderDAO(orderDAO);
    }

    @Test
    public void testViewOrderHistory_noOrders() {

        when(orderDAO.getOrdersByBuyer(5))
            .thenReturn(Collections.<Order>emptyList());

        Scanner sc = new Scanner("");

        orderService.viewOrderHistory(buyer, sc);

        verify(orderDAO).getOrdersByBuyer(5);
    }

    @Test
    public void testViewOrderHistory_exitWithoutDetails() {

        Order order = new Order();
        order.setOrderId(1);

        when(orderDAO.getOrdersByBuyer(5))
            .thenReturn(Arrays.asList(order));

        Scanner sc = new Scanner(
            "0\n"   // exit
        );

        orderService.viewOrderHistory(buyer, sc);

        verify(orderDAO).getOrdersByBuyer(5);
        verify(orderDAO, never()).getOrderItems(anyInt());
    }

    @Test
    public void testViewOrderHistory_viewDetails() {

        Order order = new Order();
        order.setOrderId(1);

        CartItem item = new CartItem();
        item.setProductName("Phone");
        item.setQuantity(2);
        item.setPrice(1000);

        when(orderDAO.getOrdersByBuyer(5))
            .thenReturn(Arrays.asList(order));

        when(orderDAO.getOrderItems(1))
            .thenReturn(Arrays.asList(item));

        Scanner sc = new Scanner(
            "1\n"
        );

        orderService.viewOrderHistory(buyer, sc);

        verify(orderDAO).getOrdersByBuyer(5);
        verify(orderDAO).getOrderItems(1);
    }

    @Test
    public void testHasOrdersForSeller_true() {

        when(orderDAO.hasOrdersForSeller(10))
            .thenReturn(true);

        boolean result = orderService.hasOrdersForSeller(10);

        assertTrue(result);
        verify(orderDAO).hasOrdersForSeller(10);
    }

    @Test
    public void testHasOrdersForSeller_false() {

        when(orderDAO.hasOrdersForSeller(10))
            .thenReturn(false);

        boolean result = orderService.hasOrdersForSeller(10);

        assertFalse(result);
        verify(orderDAO).hasOrdersForSeller(10);
    }

    @Test
    public void testViewOrdersForSeller_noOrders() {

        when(orderDAO.getOrdersForSeller(10))
            .thenReturn(Collections.<Order>emptyList());

        Scanner sc = new Scanner("");

        orderService.viewOrdersForSeller(seller, sc);

        verify(orderDAO).getOrdersForSeller(10);
    }

    @Test
    public void testViewOrdersForSeller_exitWithoutItems() {

        Order order = new Order();
        order.setOrderId(2);

        when(orderDAO.getOrdersForSeller(10))
            .thenReturn(Arrays.asList(order));

        Scanner sc = new Scanner(
            "0\n"
        );

        orderService.viewOrdersForSeller(seller, sc);

        verify(orderDAO).getOrdersForSeller(10);
        verify(orderDAO, never())
            .getOrderItemsForSeller(anyInt(), anyInt());
    }

    @Test
    public void testViewOrdersForSeller_viewItems() {

        Order order = new Order();
        order.setOrderId(2);

        CartItem item = new CartItem();
        item.setProductName("Laptop");
        item.setQuantity(1);
        item.setPrice(50000);

        when(orderDAO.getOrdersForSeller(10))
            .thenReturn(Arrays.asList(order));

        when(orderDAO.getOrderItemsForSeller(2, 10))
            .thenReturn(Arrays.asList(item));

        Scanner sc = new Scanner(
            "2\n"
        );

        orderService.viewOrdersForSeller(seller, sc);

        verify(orderDAO).getOrdersForSeller(10);
        verify(orderDAO).getOrderItemsForSeller(2, 10);
    }
}
