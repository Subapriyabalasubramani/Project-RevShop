package com.revshop.service;

import java.util.List;
import java.util.Scanner;

import com.revshop.model.CartItem;
import com.revshop.model.User;
import com.revshop.model.Product;
import com.revshop.dao.CartDAO;
import com.revshop.dao.ProductDAO;
import com.revshop.dao.impl.CartDAOImpl;
import com.revshop.dao.impl.ProductDAOImpl;


public class CartService {

	private CartDAO cartDAO = new CartDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

    public void addToCart(User buyer, Scanner sc) {

        int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

        if (cartId == -1) {
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
            System.out.println("Invalid product or quantity");
            return;
        }

        cartDAO.addToCart(cartId, productId, qty, product.getDiscountPrice());
        System.out.println("Product added to cart successfully");
    }

    public void viewCart(User buyer) {

        int cartId = cartDAO.getCartIdByBuyer(buyer.getUserId());

        if (cartId == -1) {
            System.out.println("Cart is empty");
            return;
        }

        List<CartItem> items = cartDAO.viewCart(cartId);

        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

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
            System.out.println("Cart updated");
        } else {
            cartDAO.removeFromCart(cartId, productId);
            System.out.println("Item removed from cart");
        }
    }


    public void removeFromCart(User buyer, Scanner sc) {

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
        System.out.println("\nItem removed from cart");
    }
}
