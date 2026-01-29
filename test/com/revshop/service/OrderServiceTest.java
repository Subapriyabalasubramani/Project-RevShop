package com.revshop.service;

import static org.junit.Assert.*;
import org.junit.Test;

public class OrderServiceTest {

	private static final int VALID_SELLER_ID = 4;

	@Test
	public void testOrderServiceObjectCreation() {
		OrderService service = new OrderService();
		assertNotNull(service);
	}

	@Test
	public void testHasOrdersForSeller_pass() {
		OrderService service = new OrderService();

		boolean result = service.hasOrdersForSeller(VALID_SELLER_ID);

		assertTrue(result);
	}

	@Test
	public void testHasOrdersForSeller_fail() {
		OrderService service = new OrderService();

		boolean result = service.hasOrdersForSeller(-1);

		assertFalse(result);
	}

}
