package com.revshop.dao.impl;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

public class NotificationDAOImplTest {

	    @Test
	    public void testCreateNotification_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        dao.createNotification(4, "JUnit test notification");
	    }

	    @Test
	    public void testHasUnreadNotifications_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        boolean result = dao.hasUnreadNotifications(4);

	        assertNotNull(result);
	    }

	    @Test
	    public void testGetUnreadNotifications_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        List<String> messages = dao.getUnreadNotifications(4);

	        assertNotNull(messages);
	    }

	    @Test
	    public void testMarkNotificationsAsRead_pass() {
	        NotificationDAOImpl dao = new NotificationDAOImpl();

	        // Passes if no exception occurs
	        dao.markNotificationsAsRead(4);
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
