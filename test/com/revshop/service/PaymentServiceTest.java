package com.revshop.service;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;

public class PaymentServiceTest {

	@Test
	public void testProcessPayment_COD() {
		PaymentService service = new PaymentService();

		Scanner sc = new Scanner("1\n" + // COD
				"yes\n" // confirm
		);

		String paymentMode = service.processPayment(sc);

		assertEquals("COD", paymentMode);
	}

	@Test
	public void testProcessPayment_CARD() {
		PaymentService service = new PaymentService();

		Scanner sc = new Scanner("2\n" + // CARD
				"yes\n");

		String paymentMode = service.processPayment(sc);

		assertEquals("CARD", paymentMode);
	}

	@Test
	public void testProcessPayment_UPI() {
		PaymentService service = new PaymentService();

		Scanner sc = new Scanner("3\n" + // UPI
				"yes\n");

		String paymentMode = service.processPayment(sc);

		assertEquals("UPI", paymentMode);
	}

	@Test
	public void testProcessPayment_invalidChoice_defaultsToCOD() {
		PaymentService service = new PaymentService();

		Scanner sc = new Scanner("9\n" + // invalid option
				"yes\n");

		String paymentMode = service.processPayment(sc);

		assertEquals("COD", paymentMode);
	}

	@Test
	public void testProcessPayment_cancelled() {
		PaymentService service = new PaymentService();

		Scanner sc = new Scanner("1\n" + // COD
				"no\n" // cancel
		);

		String paymentMode = service.processPayment(sc);

		assertNull(paymentMode);
	}

}
