package com.revshop.service;

import static org.mockito.Mockito.*;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class SellerServiceTest {

	@Mock
	private ProductService productService;

	@Mock
	private OrderService orderService;

	@Mock
	private NotificationService notificationService;

	@Mock
	private ReviewService reviewService;

	@Mock
	private UserService userService;

	@InjectMocks
	private SellerService sellerService;

	private User seller;

	@Before
	public void setup() {
		seller = new User();
		seller.setUserId(1);
		seller.setRole("SELLER");

		sellerService.setProductService(productService);
		sellerService.setOrderService(orderService);
		sellerService.setNotificationService(notificationService);
		sellerService.setReviewService(reviewService);
		sellerService.setUserService(userService);
	}

	@Test
	public void testSellerMenu_addProduct() {

		Scanner sc = new Scanner("1\n" + // Add product
				"8\n" // Logout
		);

		when(notificationService.hasUnreadNotifications(1)).thenReturn(false);

		sellerService.showSellerMenu(seller, sc);

		verify(productService, times(1)).addSellerProduct(eq(seller),
				any(Scanner.class));
	}

	@Test
	public void testSellerMenu_viewProducts() {

		Scanner sc = new Scanner("2\n" + "8\n");

		when(notificationService.hasUnreadNotifications(1)).thenReturn(false);

		sellerService.showSellerMenu(seller, sc);

		verify(productService).viewBySellerProducts(1);
	}

	@Test
	public void testSellerMenu_viewOrders_clearsNotifications() {

		Scanner sc = new Scanner("5\n" + "8\n");

		when(notificationService.hasUnreadNotifications(1)).thenReturn(true);

		sellerService.showSellerMenu(seller, sc);

		verify(orderService)
				.viewOrdersForSeller(eq(seller), any(Scanner.class));
		verify(notificationService).clearNotifications(1);
	}

	@Test
	public void testSellerMenu_logout() {

		Scanner sc = new Scanner("8\n");

		when(notificationService.hasUnreadNotifications(1)).thenReturn(false);

		sellerService.showSellerMenu(seller, sc);

		// no service interaction expected
		verifyNoMoreInteractions(productService, orderService, reviewService,
				userService);
	}
}
