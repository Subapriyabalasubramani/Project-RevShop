package com.revshop.service;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;
import com.revshop.model.User;

public class CheckoutServiceTest {

	@Test
	public void testCheckoutServiceObjectCreation() {
		CheckoutService service = new CheckoutService();
		assertNotNull(service);
	}

	@Test
	public void testCheckout_cartEmpty() {
		CheckoutService service = new CheckoutService();

		User buyer = new User();
		buyer.setUserId(-1);

		Scanner sc = new Scanner("");
		service.checkout(buyer, sc);
	}
}
