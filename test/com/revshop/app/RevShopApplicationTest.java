package com.revshop.app;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RevShopApplicationTest {

    @BeforeClass
    public static void beforeAll() {
        System.out.println("Before all tests");
    }

    @Test
    public void testAskUserConsent_yes() {
        Scanner scanner = new Scanner("yes\n");

        boolean result = RevShopApplication.askUserConsent(scanner);

        assertTrue(result);
        scanner.close();
    }

    @Test
    public void testAskUserConsent_y() {
        Scanner scanner = new Scanner("y\n");

        boolean result = RevShopApplication.askUserConsent(scanner);

        assertTrue(result);
        scanner.close();
    }

    @Test
    public void testAskUserConsent_no() {
        Scanner scanner = new Scanner("no\n");

        boolean result = RevShopApplication.askUserConsent(scanner);

        assertFalse(result);
        scanner.close();
    }

    @Test
    public void testAskUserConsent_invalidInput() {
        Scanner scanner = new Scanner("abc\n");

        boolean result = RevShopApplication.askUserConsent(scanner);

        assertFalse(result);
        scanner.close();
    }

    @AfterClass
    public static void afterAll() {
        System.out.println("After all tests");
    }
}
