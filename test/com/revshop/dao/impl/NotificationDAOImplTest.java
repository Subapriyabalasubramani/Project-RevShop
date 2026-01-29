package com.revshop.dao.impl;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

public class NotificationDAOImplTest {
	
	 private static final int VALID_SELLER_ID = 4;

	    @Test
	    public void testCreateNotification_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        dao.createNotification(VALID_SELLER_ID, "JUnit test notification");
	    }

	    @Test
	    public void testHasUnreadNotifications_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        boolean result = dao.hasUnreadNotifications(VALID_SELLER_ID);

	        assertNotNull(result);
	    }

	    @Test
	    public void testGetUnreadNotifications_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        List<String> messages = dao.getUnreadNotifications(VALID_SELLER_ID);

	        assertNotNull(messages);
	    }

	    @Test
	    public void testMarkNotificationsAsRead_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        // Passes if no exception occurs
	        dao.markNotificationsAsRead(VALID_SELLER_ID);
	    }

	    @Test
	    public void testHasUnreadNotifications_fail() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        boolean result = dao.hasUnreadNotifications(-1);

	        assertFalse(result);
	    }

	    @Test
	    public void testGetUnreadNotifications_fail() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        List<String> messages = dao.getUnreadNotifications(-1);

	        assertNotNull(messages);
	        assertTrue(messages.isEmpty());
	    }

	    @Test
	    public void testMarkNotificationsAsRead_fail() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        // Invalid seller ID, should not throw exception
	        dao.markNotificationsAsRead(-1);
	    }

}
