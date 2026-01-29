package com.revshop.service;

import java.util.List;
import java.util.Scanner;

import com.revshop.dao.OrderDAO;
import com.revshop.dao.CartDAO;
import com.revshop.dao.impl.CartDAOImpl;
import com.revshop.dao.impl.OrderDAOImpl;
import com.revshop.model.Order;
import com.revshop.model.CartItem;
import com.revshop.model.User;

public class CheckoutService {

	private CartDAO cartDAO = new CartDAOImpl();
	private OrderDAO orderDAO = new OrderDAOImpl();

	public void checkout(User buyer, Scanner sc) {

	    int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

	    if (cartId == -1) {
	        System.out.println("Cart is empty");
	        return;
	    }

	    List<CartItem> cartItems = cartDAO.viewCart(cartId);

	    if (cartItems.isEmpty()) {
	        System.out.println("Cart is empty");
	        return;
	    }

	    // Show order summary
	    double total = 0;
	    System.out.println("\n--- Order Summary ---");
	    for (CartItem item : cartItems) {
	        double itemTotal = item.getPrice() * item.getQuantity();
	        total += itemTotal;

	        System.out.println(
	            item.getProductName() +
	            " | Qty: " + item.getQuantity() +
	            " | Price: " + itemTotal
	        );
	    }
	    System.out.println("Total Amount: " + total);

	    // Get addresses
	    System.out.print("\nEnter Shipping Address: ");
	    String shipping = sc.nextLine();

	    System.out.print("Enter Billing Address: ");
	    String billing = sc.nextLine();

	    // Confirmation
	    System.out.print("\nConfirm order? (yes/no): ");
	    String confirm = sc.nextLine();

	    if (!confirm.equalsIgnoreCase("yes")) {
	        System.out.println("Order cancelled. Go back to cart.");
	        return;
	    }

	    Order order = new Order();
	    order.setBuyerId(buyer.getUserId());
	    order.setShippingAddress(shipping);
	    order.setBillingAddress(billing);
	    order.setTotalAmount(total);

	    int orderId = orderDAO.createOrder(order);

	    for (CartItem item : cartItems) {
	        orderDAO.addOrderItem(orderId, item);
	        orderDAO.reduceProductStock(item.getProductId(), item.getQuantity());
	    }

	    orderDAO.clearCart(cartId);

	    System.out.println("\n[NOTIFICATION] Your order has been placed successfully");
	    System.out.println("Order ID: " + orderId);
	}

}
