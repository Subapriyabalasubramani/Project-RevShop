package com.revshop.service;

import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revshop.dao.OrderDAO;
import com.revshop.dao.CartDAO;
import com.revshop.dao.impl.CartDAOImpl;
import com.revshop.dao.impl.OrderDAOImpl;
import com.revshop.model.Order;
import com.revshop.model.CartItem;
import com.revshop.model.User;
import com.revshop.service.NotificationService;
import com.revshop.dao.ProductDAO;
import com.revshop.dao.impl.ProductDAOImpl;
import com.revshop.service.PaymentService;



public class CheckoutService {
	private static final Logger logger =
            Logger.getLogger(CheckoutService.class);

	private CartDAO cartDAO = new CartDAOImpl();
	private OrderDAO orderDAO = new OrderDAOImpl();
	private NotificationService notificationService = new NotificationService();
	private ProductDAO productDAO = new ProductDAOImpl();
	private PaymentService paymentService = new PaymentService();
	
	// for testing
	void setCartDAO(CartDAO cartDAO) {
	    this.cartDAO = cartDAO;
	}

	void setOrderDAO(OrderDAO orderDAO) {
	    this.orderDAO = orderDAO;
	}

	void setNotificationService(NotificationService notificationService) {
	    this.notificationService = notificationService;
	}

	void setProductDAO(ProductDAO productDAO) {
	    this.productDAO = productDAO;
	}

	void setPaymentService(PaymentService paymentService) {
	    this.paymentService = paymentService;
	}



	public void checkout(User buyer, Scanner sc) {
		logger.info("Checkout initiated. buyerId: " + buyer.getUserId());

	    int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

	    if (cartId == -1) {
	    	logger.info("Checkout failed: cart not found. buyerId: " + buyer.getUserId());
	        System.out.println("Cart is empty");
	        return;
	    }

	    List<CartItem> cartItems = cartDAO.viewCart(cartId);

	    if (cartItems.isEmpty()) {
	    	logger.info("Checkout failed: cart empty. buyerId: " + buyer.getUserId());
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
	    	logger.info("Checkout cancelled by buyer before payment. buyerId: " + buyer.getUserId());
	        System.out.println("Order cancelled. Go back to cart.");
	        return;
	    }
	    
	    String paymentMode = paymentService.processPayment(sc);

	    if (paymentMode == null) {
	    	logger.warn("Payment failed. Checkout aborted. buyerId: " + buyer.getUserId());
	        System.out.println("Order cancelled due to payment failure");
	        return;
	    }
	    
	    logger.info("Payment successful. Mode: " + paymentMode +
	            ", buyerId: " + buyer.getUserId());

	    Order order = new Order();
	    order.setBuyerId(buyer.getUserId());
	    order.setShippingAddress(shipping);
	    order.setBillingAddress(billing);
	    order.setTotalAmount(total);

	    int orderId = orderDAO.createOrder(order);
	    logger.info("Order created successfully. orderId: " + orderId +
	            ", buyerId: " + buyer.getUserId());
	    
	    orderDAO.updatePaymentDetails(
	    	    orderId,
	    	    paymentMode,
	    	    "SUCCESS"
	    	);


	    for (CartItem item : cartItems) {
	        orderDAO.addOrderItem(orderId, item);
	        orderDAO.reduceProductStock(item.getProductId(), item.getQuantity());
	        
	        int sellerId =
	                productDAO.getSellerIdByProductId(item.getProductId());

	            notificationService.notifySeller(
	                sellerId,
	                "You have a new order for your product"
	            );
	    }

	    orderDAO.clearCart(cartId);
	    logger.info("Checkout completed successfully. orderId: " + orderId +
	            ", buyerId: " + buyer.getUserId());

	    System.out.println("\n[NOTIFICATION] Order placed successfully");
	    System.out.println("Payment Mode: " + paymentMode);
	    System.out.println("Payment Status: SUCCESS");
	    System.out.println("Order ID: " + orderId);
	    logger.info("Order items processed and sellers notified. orderId: " + orderId);
	}

}
