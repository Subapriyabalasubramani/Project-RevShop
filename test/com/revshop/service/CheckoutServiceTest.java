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
import com.revshop.dao.OrderDAO;
import com.revshop.dao.ProductDAO;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {

    @Mock
    private CartDAO cartDAO;

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private NotificationService notificationService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CheckoutService checkoutService;

    private User buyer;

    @Before
    public void setup() {
        buyer = new User();
        buyer.setUserId(5);

        checkoutService.setCartDAO(cartDAO);
        checkoutService.setOrderDAO(orderDAO);
        checkoutService.setProductDAO(productDAO);
        checkoutService.setNotificationService(notificationService);
        checkoutService.setPaymentService(paymentService);
    }

    @Test
    public void testCheckout_cartNotFound() {

        when(cartDAO.getCartIdByBuyer(5))
            .thenReturn(-1);

        Scanner sc = new Scanner("");

        checkoutService.checkout(buyer, sc);

        verify(cartDAO).getCartIdByBuyer(5);
        verifyNoMoreInteractions(cartDAO);
    }

    @Test
    public void testCheckout_paymentCancelled() {

        when(cartDAO.getCartIdByBuyer(5))
            .thenReturn(1);

        CartItem item = new CartItem();
        item.setProductId(10);
        item.setProductName("Phone");
        item.setQuantity(1);
        item.setPrice(1000);

        when(cartDAO.viewCart(1))
            .thenReturn(Arrays.asList(item));

        when(paymentService.processPayment(any(Scanner.class)))
            .thenReturn(null);

        Scanner sc = new Scanner(
            "Ship Addr\n" +
            "Bill Addr\n" +
            "yes\n"
        );

        checkoutService.checkout(buyer, sc);

        verify(paymentService).processPayment(any(Scanner.class));
        verify(orderDAO, never()).createOrder(any(Order.class));
    }

    @Test
    public void testCheckout_success() {

        when(cartDAO.getCartIdByBuyer(5))
            .thenReturn(1);

        CartItem item = new CartItem();
        item.setProductId(10);
        item.setProductName("Phone");
        item.setQuantity(2);
        item.setPrice(500);

        when(cartDAO.viewCart(1))
            .thenReturn(Arrays.asList(item));

        when(paymentService.processPayment(any(Scanner.class)))
            .thenReturn("COD");

        when(orderDAO.createOrder(any(Order.class)))
            .thenReturn(100);

        when(productDAO.getSellerIdByProductId(10))
            .thenReturn(99);

        Scanner sc = new Scanner(
            "Ship Addr\n" +
            "Bill Addr\n" +
            "yes\n"
        );

        checkoutService.checkout(buyer, sc);

        verify(orderDAO).createOrder(any(Order.class));
        verify(orderDAO).updatePaymentDetails(100, "COD", "SUCCESS");
        verify(orderDAO).addOrderItem(eq(100), any(CartItem.class));
        verify(orderDAO).reduceProductStock(10, 2);
        verify(notificationService)
            .notifySeller(99, "You have a new order for your product");
        verify(orderDAO).clearCart(1);
    }
}
