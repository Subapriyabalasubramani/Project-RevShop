package com.revshop.service;

import static org.junit.Assert.*;

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

        // Passes if no exception occurs
        service.userRegistration("BUYER", sc);
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
    public void testUserRegistration_fail_invalidName() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "Jo\n" +                        // invalid name
            "test@mail.com\n" +
            "test123\n"
        );

        service.userRegistration("BUYER", sc);
    }

    @Test
    public void testUserRegistration_fail_invalidEmail() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "JUnit User\n" +
            "invalidEmail\n" +              // invalid email
            "test123\n"
        );

        service.userRegistration("BUYER", sc);
    }

    @Test
    public void testUserRegistration_fail_shortPassword() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "JUnit User\n" +
            "test@mail.com\n" +
            "123\n"                         // short password
        );

        service.userRegistration("BUYER", sc);
    }

    @Test
    public void testSellerRegistration_fail_missingBusinessName() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "JUnit Seller\n" +
            "seller@mail.com\n" +
            "test123\n" +
            "\n"                            // missing business name
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

    @Test
    public void testLogin_fail_emptyEmailPassword() {
        UserService service = new UserService();

        Scanner sc = new Scanner(
            "\n" +
            "\n"
        );

        User user = service.login(sc);

        assertNull(user);
    }
}
