package com.revshop.service;

import static org.junit.Assert.*;
import org.junit.Test;
import com.revshop.model.User;

public class CartServiceTest {

	private static final int VALID_BUYER_ID = 5;

	@Test
	public void testCartServiceObjectCreation() {
		CartService service = new CartService();
		assertNotNull(service);
	}

	@Test
	public void testViewCart_pass() {
		CartService service = new CartService();

		User buyer = new User();
		buyer.setUserId(VALID_BUYER_ID);

		service.viewCart(buyer);
	}

	@Test
	public void testViewCart_fail() {
		CartService service = new CartService();

		User buyer = new User();
		buyer.setUserId(-1); 
		
		service.viewCart(buyer);
	}

}
