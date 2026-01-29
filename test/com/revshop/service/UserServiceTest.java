package com.revshop.service;


import static org.junit.Assert.*;

import org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;
import com.revshop.model.User;

public class UserServiceTest {

    @Test
    public void testUserRegistration_pass_buyer() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "JUnit User\n" +
            "junituser@test.com\n" +
            "test123\n"
        );

        service.userRegistration("BUYER", sc);
    }
    
	@Test
    public void testLogin_pass() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "junituser@test.com\n" +
            "test123\n"
        );

        User user = service.login(sc);

        assertNotNull(user);
        assertEquals("BUYER", user.getRole());
    }

    @Test
    public void testUserRegistration_pass_seller() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "JUnit Seller\n" +
            "junitseller@test.com\n" +
            "test123\n" +
            "JUnit Business\n"
        );

        service.userRegistration("SELLER", sc);
    }

    @Test
    public void testLogin_fail_invalidCredentials() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "invalid@test.com\n" +
            "wrongpass\n"
        );

        User user = service.login(sc);

        assertNull(user);
    }

}
