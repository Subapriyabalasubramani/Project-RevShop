package com.revshop.service;

import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revshop.model.CartItem;
import com.revshop.model.User;
import com.revshop.model.Product;
import com.revshop.dao.CartDAO;
import com.revshop.dao.ProductDAO;
import com.revshop.dao.impl.CartDAOImpl;
import com.revshop.dao.impl.ProductDAOImpl;


public class CartService {
	private static final Logger logger =
            Logger.getLogger(CartService.class);

	private CartDAO cartDAO = new CartDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    
 // for testing
    void setCartDAO(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }


    public void addToCart(User buyer, Scanner sc) {
    	logger.info("Add to cart initiated. buyerId: " + buyer.getUserId());

        int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

        if (cartId == -1) {
        	logger.info("No cart found. Creating new cart for buyerId: " + buyer.getUserId());
            cartDAO.createCart(buyer.getUserId());
            cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());
        }

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();

        Product product = productDAO.getProductById(productId);

        if (product == null || qty <= 0 || qty > product.getQuantity()) {
        	logger.warn("Add to cart failed due to invalid product/quantity. " +
                     "buyerId: " + buyer.getUserId() +
                     ", productId: " + productId +
                     ", qty: " + qty);
            System.out.println("\nCannot add to cart. The requested quantity is invalid or exceeds available stock.");
            return;
        }

        cartDAO.addToCart(cartId, productId, qty, product.getDiscountPrice());
        logger.info("Product added to cart successfully. buyerId: " +
                buyer.getUserId() + ", productId: " + productId);
        System.out.println("\nProduct added to cart successfully");
    }

    public void viewCart(User buyer) {

        int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

        if (cartId == -1) {
        	logger.info("View cart requested but cart is empty. buyerId: " + buyer.getUserId());
            System.out.println("Cart is empty");
            return;
        }

        List<CartItem> items = cartDAO.viewCart(cartId);

        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        logger.info("Displaying cart for buyerId: " + buyer.getUserId());

        System.out.println("\n--- Your Cart ---");
        for (CartItem item : items) {
            System.out.println(
                item.getProductName() +
                " | Qty: " + item.getQuantity() +
                " | Price: " + item.getPrice() +
                " | Product Id: " + item.getProductId()
            );
        }
    }
    
    public void updateCart(User buyer, Scanner sc) {
    	logger.info("Update cart initiated. buyerId: " + buyer.getUserId());

        int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

        if (cartId == -1) {
            System.out.println("Cart is empty");
            return;
        }

        viewCart(buyer); // show cart first

        System.out.print("Enter Product ID to update: ");
        int productId = sc.nextInt();

        System.out.print("Enter new quantity (0 to remove): ");
        int qty = sc.nextInt();
        sc.nextLine();

        if (qty > 0) {
            cartDAO.updateCartItem(cartId, productId, qty);
            logger.info("Cart item updated. buyerId: " +
                    buyer.getUserId() + ", productId: " + productId +
                    ", newQty: " + qty);
            System.out.println("Cart updated");
        } else {
            cartDAO.removeFromCart(cartId, productId);
            logger.info("Cart item removed via update. buyerId: " +
                    buyer.getUserId() + ", productId: " + productId);
            System.out.println("Item removed from cart");
        }
    }


    public void removeFromCart(User buyer, Scanner sc) {
    	logger.info("Remove from cart initiated. buyerId: " + buyer.getUserId());


        int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

        if (cartId == -1) {
            System.out.println("Cart is empty");
            return;
        }
        
        viewCart(buyer);

        System.out.print("\nEnter Product ID to remove: ");
        int productId = sc.nextInt();
        sc.nextLine();

        cartDAO.removeFromCart(cartId, productId);
        logger.info("Item removed from cart. buyerId: " +
                buyer.getUserId() + ", productId: " + productId);
        System.out.println("\nItem removed from cart");
    }
}
