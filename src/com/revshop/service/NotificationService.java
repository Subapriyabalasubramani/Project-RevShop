package com.revshop.service;

import com.revshop.dao.NotificationDAO;
import com.revshop.dao.impl.NotificationDAOImpl;

public class NotificationService {

	private NotificationDAO notificationDAO = new NotificationDAOImpl();

    public void notifySeller(int sellerId, String message) {
        notificationDAO.createNotification(sellerId, message);
    }

    public boolean hasUnreadNotifications(int sellerId) {
        return notificationDAO.hasUnreadNotifications(sellerId);
    }

    public void clearNotifications(int sellerId) {
        notificationDAO.markNotificationsAsRead(sellerId);
    }
    
}
