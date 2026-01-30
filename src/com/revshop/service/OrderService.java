package com.revshop.service;

import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.revshop.dao.OrderDAO;
import com.revshop.dao.impl.OrderDAOImpl;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.User;

public class OrderService {
	private static final Logger logger =
            Logger.getLogger(OrderService.class);

    private OrderDAO orderDAO = new OrderDAOImpl();
 
    void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    public void viewOrderHistory(User buyer, Scanner sc) {
    	logger.info("Order history requested. buyerId: " + buyer.getUserId());

        List<Order> orders = orderDAO.getOrdersByBuyer(buyer.getUserId());

        if (orders.isEmpty()) {
        	logger.info("No orders found for buyerId: " + buyer.getUserId());
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

        if (orderId == 0) {
        	logger.info("Buyer exited order details view. buyerId: " + buyer.getUserId());
            return;
        }
        
        logger.info("Buyer viewing order details. orderId: " + orderId +
                ", buyerId: " + buyer.getUserId());

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
    	boolean hasOrders = orderDAO.hasOrdersForSeller(sellerId);

        if (hasOrders) {
        	logger.info("Seller has orders. sellerId: " + sellerId);
        }

        return hasOrders;
    }
    
    public void viewOrdersForSeller(User seller, Scanner sc) {
    	
    	logger.info("Seller viewing orders. sellerId: " + seller.getUserId());

        List<Order> orders = orderDAO.getOrdersForSeller(seller.getUserId());

        if (orders.isEmpty()) {
        	logger.info("No orders found for sellerId: " + seller.getUserId());
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
        	logger.info("Seller exited order view. sellerId: " + seller.getUserId());
            return;
        }
        
        logger.info("Seller viewing order items. orderId: " + orderId +
                ", sellerId: " + seller.getUserId());

        List<CartItem> items =
            orderDAO.getOrderItemsForSeller(orderId, seller.getUserId());

        if (items.isEmpty()) {
        	logger.info("No order items found. orderId: " + orderId +
                    ", sellerId: " + seller.getUserId());
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

