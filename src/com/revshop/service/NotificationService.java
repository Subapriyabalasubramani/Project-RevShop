package com.revshop.service;

import com.revshop.dao.NotificationDAO;
import com.revshop.dao.impl.NotificationDAOImpl;
import java.util.logging.Logger;


public class NotificationService {
	private static final Logger LOGGER =
	        Logger.getLogger(NotificationService.class.getName());

	private NotificationDAO notificationDAO = new NotificationDAOImpl();
	
	// for testing
	void setNotificationDAO(NotificationDAO notificationDAO) {
	    this.notificationDAO = notificationDAO;
	}


    public void notifySeller(int sellerId, String message) {
    	LOGGER.info("Sending notification to sellerId: " + sellerId);
        notificationDAO.createNotification(sellerId, message);
    }

    public boolean hasUnreadNotifications(int sellerId) {
    	boolean hasUnread = notificationDAO.hasUnreadNotifications(sellerId);

        if (hasUnread) {
            LOGGER.info("Seller has unread notifications. sellerId: " + sellerId);
        }

        return hasUnread;
    }

    public void clearNotifications(int sellerId) {
    	LOGGER.info("Clearing notifications for sellerId: " + sellerId);
        notificationDAO.markNotificationsAsRead(sellerId);
    }
    
}
