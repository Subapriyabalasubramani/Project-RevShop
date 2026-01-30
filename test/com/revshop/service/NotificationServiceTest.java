package com.revshop.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.dao.NotificationDAO;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    @Mock
    private NotificationDAO notificationDAO;

    @InjectMocks
    private NotificationService notificationService;

    @Before
    public void setup() {
        notificationService.setNotificationDAO(notificationDAO);
    }

    @Test
    public void testNotifySeller() {

        notificationService.notifySeller(10, "New order received");

        verify(notificationDAO)
            .createNotification(10, "New order received");
    }

    @Test
    public void testHasUnreadNotifications_true() {

        when(notificationDAO.hasUnreadNotifications(10))
            .thenReturn(true);

        boolean result =
            notificationService.hasUnreadNotifications(10);

        assertTrue(result);
        verify(notificationDAO).hasUnreadNotifications(10);
    }

    @Test
    public void testHasUnreadNotifications_false() {

        when(notificationDAO.hasUnreadNotifications(10))
            .thenReturn(false);

        boolean result =
            notificationService.hasUnreadNotifications(10);

        assertFalse(result);
        verify(notificationDAO).hasUnreadNotifications(10);
    }

    @Test
    public void testClearNotifications() {

        notificationService.clearNotifications(10);

        verify(notificationDAO)
            .markNotificationsAsRead(10);
    }
}
