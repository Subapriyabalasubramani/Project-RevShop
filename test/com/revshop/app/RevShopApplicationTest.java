package com.revshop.app;

import org.junit.Test;
import java.util.Scanner;
import static org.junit.Assert.*;

public class RevShopApplicationTest {

	@Test
	public void testApplicationCreation() {
		RevShopApplication app = new RevShopApplication();
		assertNotNull(app);
	}

	@Test
	public void testAskUserConsent_yes() {
		Scanner sc = new Scanner("yes\n");
		boolean result = RevShopApplication.askUserConsent(sc);
		sc.close();
		assertTrue(result);
	}

	@Test
	public void testAskUserConsent_y() {
		Scanner sc = new Scanner("y\n");
		boolean result = RevShopApplication.askUserConsent(sc);
		sc.close();
		assertTrue(result);
	}

	@Test
	public void testAskUserConsent_no() {
		Scanner sc = new Scanner("no\n");
		boolean result = RevShopApplication.askUserConsent(sc);
		sc.close();
		assertFalse(result);
	}

	@Test
	public void testAskUserConsent_invalidInput() {
		Scanner sc = new Scanner("abc\n");
		boolean result = RevShopApplication.askUserConsent(sc);
		sc.close();
		assertFalse(result);
	}

}
