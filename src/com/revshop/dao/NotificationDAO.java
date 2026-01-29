package com.revshop.dao;

import java.util.List;

public interface NotificationDAO {

	void createNotification(int sellerId, String message);

    boolean hasUnreadNotifications(int sellerId);

    List<String> getUnreadNotifications(int sellerId);

    void markNotificationsAsRead(int sellerId);
}
