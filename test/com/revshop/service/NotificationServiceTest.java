package com.revshop.service;

import static org.junit.Assert.*;
import org.junit.Test;

public class NotificationServiceTest {

	private static final int VALID_SELLER_ID = 4;

	@Test
	public void testNotificationServiceObjectCreation() {
		NotificationService service = new NotificationService();
		assertNotNull(service);
	}

	@Test
	public void testNotifySeller_pass() {
		NotificationService service = new NotificationService();

		service.notifySeller(VALID_SELLER_ID, "JUnit test notification");
	}

	@Test
	public void testHasUnreadNotifications_pass() {
		NotificationService service = new NotificationService();

		boolean result = service.hasUnreadNotifications(VALID_SELLER_ID);

		assertNotNull(result);
	}

	@Test
	public void testClearNotifications_pass() {
		NotificationService service = new NotificationService();

		service.clearNotifications(VALID_SELLER_ID);
	}
	
	@Test
    public void testHasUnreadNotifications_fail() {
        NotificationService service = new NotificationService();

        boolean result = service.hasUnreadNotifications(-1);

        assertFalse(result);
    }

}
