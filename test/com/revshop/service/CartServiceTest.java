package com.revshop.service;

import static org.mockito.Mockito.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.dao.CartDAO;
import com.revshop.dao.ProductDAO;
import com.revshop.model.CartItem;
import com.revshop.model.Product;
import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @Mock
    private CartDAO cartDAO;

    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private CartService cartService;

    private User buyer;

    @Before
    public void setup() {
        buyer = new User();
        buyer.setUserId(5);

        cartService.setCartDAO(cartDAO);
        cartService.setProductDAO(productDAO);
    }

    @Test
    public void testAddToCart_success_existingCart() {

        when(cartDAO.getCartIdByBuyer(5)).thenReturn(1);

        Product product = new Product();
        product.setProductId(10);
        product.setQuantity(10);
        product.setDiscountPrice(500);

        when(productDAO.getProductById(10)).thenReturn(product);

        Scanner sc = new Scanner(
            "10\n" +   // productId
            "2\n"      // quantity
        );

        cartService.addToCart(buyer, sc);

        verify(cartDAO).addToCart(1, 10, 2, 500);
    }

    @Test
    public void testAddToCart_createsNewCart() {

        when(cartDAO.getCartIdByBuyer(5))
            .thenReturn(-1)
            .thenReturn(2);

        Product product = new Product();
        product.setProductId(10);
        product.setQuantity(5);
        product.setDiscountPrice(300);

        when(productDAO.getProductById(10)).thenReturn(product);

        Scanner sc = new Scanner(
            "10\n" +
            "1\n"
        );

        cartService.addToCart(buyer, sc);

        verify(cartDAO).createCart(5);
        verify(cartDAO).addToCart(2, 10, 1, 300);
    }

    @Test
    public void testAddToCart_invalidQuantity() {

        when(cartDAO.getCartIdByBuyer(5)).thenReturn(1);

        Product product = new Product();
        product.setProductId(10);
        product.setQuantity(2);

        when(productDAO.getProductById(10)).thenReturn(product);

        Scanner sc = new Scanner(
            "10\n" +
            "5\n"   // exceeds stock
        );

        cartService.addToCart(buyer, sc);

        verify(cartDAO, never()).addToCart(anyInt(), anyInt(), anyInt(), anyDouble());
    }

    @Test
    public void testViewCart_cartNotFound() {

        when(cartDAO.getCartIdByBuyer(5)).thenReturn(-1);

        cartService.viewCart(buyer);

        verify(cartDAO).getCartIdByBuyer(5);
    }

    @Test
    public void testViewCart_withItems() {

        when(cartDAO.getCartIdByBuyer(5)).thenReturn(1);

        CartItem item = new CartItem();
        item.setProductName("Phone");
        item.setQuantity(2);
        item.setPrice(1000);
        item.setProductId(10);

        when(cartDAO.viewCart(1)).thenReturn(Arrays.asList(item));

        cartService.viewCart(buyer);

        verify(cartDAO).viewCart(1);
    }

    @Test
    public void testUpdateCart_updateQuantity() {

        when(cartDAO.getCartIdByBuyer(5)).thenReturn(1);

        when(cartDAO.viewCart(1))
            .thenReturn(Collections.<CartItem>emptyList());

        Scanner sc = new Scanner(
            "10\n" +
            "3\n"
        );

        cartService.updateCart(buyer, sc);

        verify(cartDAO).updateCartItem(1, 10, 3);
    }

    @Test
    public void testUpdateCart_removeItem() {

        when(cartDAO.getCartIdByBuyer(5)).thenReturn(1);

        when(cartDAO.viewCart(1))
            .thenReturn(Collections.<CartItem>emptyList());

        Scanner sc = new Scanner(
            "10\n" +
            "0\n"
        );

        cartService.updateCart(buyer, sc);

        verify(cartDAO).removeFromCart(1, 10);
    }

    @Test
    public void testRemoveFromCart_success() {

        when(cartDAO.getCartIdByBuyer(5)).thenReturn(1);

        when(cartDAO.viewCart(1))
            .thenReturn(Collections.<CartItem>emptyList());

        Scanner sc = new Scanner(
            "10\n"
        );

        cartService.removeFromCart(buyer, sc);

        verify(cartDAO).removeFromCart(1, 10);
    }
}
