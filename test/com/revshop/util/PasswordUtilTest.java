package com.revshop.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class PasswordUtilTest {

    @Test
    public void testHashPassword_notNull() {
        String hash = PasswordUtil.hashPassword("password123");
        assertNotNull(hash);
    }

    @Test
    public void testHashPassword_consistentHash() {
        String hash1 = PasswordUtil.hashPassword("password123");
        String hash2 = PasswordUtil.hashPassword("password123");

        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashPassword_differentPasswords() {
        String hash1 = PasswordUtil.hashPassword("password123");
        String hash2 = PasswordUtil.hashPassword("password124");

        assertFalse(hash1.equals(hash2));
    }

    @Test
    public void testHashPassword_length() {
        String hash = PasswordUtil.hashPassword("password123");

        assertEquals(64, hash.length());
    }

    @Test(expected = RuntimeException.class)
    public void testHashPassword_nullPassword() {
        PasswordUtil.hashPassword(null);
    }
}
