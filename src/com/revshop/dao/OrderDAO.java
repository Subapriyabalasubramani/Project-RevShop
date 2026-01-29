package com.revshop.dao;

import java.util.List;

import com.revshop.model.Order;
import com.revshop.model.CartItem;

public interface OrderDAO {

	int createOrder(Order order);

	void addOrderItem(int orderId, CartItem item);

	void reduceProductStock(int productId, int quantity);

	void clearCart(int cartId);
	
	List<Order> getOrdersByBuyer(int buyerId);

	List<CartItem> getOrderItems(int orderId);
	
	List<Order> getOrdersForSeller(int sellerId);

	List<CartItem> getOrderItemsForSeller(int orderId, int sellerId);

	boolean hasOrdersForSeller(int sellerId);

	List<CartItem> getPurchasedProducts(int buyerId);
	
	void updatePaymentDetails(int orderId, String paymentMode, String paymentStatus);


}
