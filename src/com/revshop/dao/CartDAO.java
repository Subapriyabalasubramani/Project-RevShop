package com.revshop.dao;

import com.revshop.model.CartItem;
import java.util.List;

public interface CartDAO {

	int getCartIdByBuyer(int buyerId);

    void createCart(int buyerId);

    void addToCart(int cartId, int productId, int quantity, double price);

    List<CartItem> viewCart(int cartId);
    
    void updateCartItem(int cartId, int productId, int quantity);

    void removeFromCart(int cartId, int productId);
    
}
