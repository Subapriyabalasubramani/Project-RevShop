package com.revshop.service;

import java.util.List;
import java.util.Scanner;

import com.revshop.dao.OrderDAO;
import com.revshop.dao.impl.OrderDAOImpl;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.User;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAOImpl();

    public void viewOrderHistory(User buyer, Scanner sc) {

        List<Order> orders = orderDAO.getOrdersByBuyer(buyer.getUserId());

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n--- Your Orders ---");
        for (Order order : orders) {
            System.out.println(
                "Order ID: " + order.getOrderId() +
                " | Date: " + order.getOrderDate() +
                " | Total: " + order.getTotalAmount()
            );
        }

        System.out.print("\nEnter Order ID to view details (0 to exit): ");
        int orderId = sc.nextInt();
        sc.nextLine();

        if (orderId == 0) return;

        List<CartItem> items = orderDAO.getOrderItems(orderId);

        System.out.println("\n--- Order Details ---");
        for (CartItem item : items) {
            System.out.println(
                item.getProductName() +
                " | Qty: " + item.getQuantity() +
                " | Price: " + item.getPrice()
            );
        }
    }
    
    public boolean hasOrdersForSeller(int sellerId) {
        return orderDAO.hasOrdersForSeller(sellerId);
    }
    
    public void viewOrdersForSeller(User seller, Scanner sc) {

        List<Order> orders = orderDAO.getOrdersForSeller(seller.getUserId());

        if (orders.isEmpty()) {
            System.out.println("No orders for your products yet.");
            return;
        }

        System.out.println("\n--- Orders for Your Products ---");
        for (Order order : orders) {
            System.out.println(
                "Order ID: " + order.getOrderId() +
                " | Date: " + order.getOrderDate() +
                " | Total: " + order.getTotalAmount()
            );
        }

        System.out.print("\nEnter Order ID to view order items (0 to go back): ");
        int orderId = sc.nextInt();
        sc.nextLine();

        if (orderId == 0) {
            return;
        }

        List<CartItem> items =
            orderDAO.getOrderItemsForSeller(orderId, seller.getUserId());

        if (items.isEmpty()) {
            System.out.println("No items found for this order.");
            return;
        }

        System.out.println("\n--- Order Items ---");
        for (CartItem item : items) {
            System.out.println(
                item.getProductName() +
                " | Qty: " + item.getQuantity() +
                " | Price: " + item.getPrice()
            );
        }
    }
        
}

