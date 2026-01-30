package com.revshop.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class ValidationUtilTest {

    @Test
    public void testValidName() {
        assertTrue(ValidationUtil.isValidName("John"));
    }

    @Test
    public void testInvalidName_null() {
        assertFalse(ValidationUtil.isValidName(null));
    }

    @Test
    public void testInvalidName_short() {
        assertFalse(ValidationUtil.isValidName("Jo"));
    }

    @Test
    public void testValidEmail_gmail() {
        assertTrue(ValidationUtil.isValidEmail("user@gmail.com"));
    }

    @Test
    public void testValidEmail_emailOrg() {
        assertTrue(ValidationUtil.isValidEmail("user@email.org"));
    }

    @Test
    public void testInvalidEmail_wrongDomain() {
        assertFalse(ValidationUtil.isValidEmail("user@yahoo.com"));
    }

    @Test
    public void testInvalidEmail_null() {
        assertFalse(ValidationUtil.isValidEmail(null));
    }
    
    @Test
    public void testValidPassword() {
        assertTrue(ValidationUtil.isValidPassword("secret123"));
    }

    @Test
    public void testInvalidPassword_short() {
        assertFalse(ValidationUtil.isValidPassword("123"));
    }

    @Test
    public void testInvalidPassword_null() {
        assertFalse(ValidationUtil.isValidPassword(null));
    }
    
    @Test
    public void testValidBusinessName_forSeller() {
        assertTrue(
            ValidationUtil.isValidBusinessName("SELLER", "My Shop")
        );
    }

    @Test
    public void testInvalidBusinessName_forSeller() {
        assertFalse(
            ValidationUtil.isValidBusinessName("SELLER", "")
        );
    }

    @Test
    public void testBusinessName_notRequiredForBuyer() {
        assertTrue(
            ValidationUtil.isValidBusinessName("BUYER", null)
        );
    }
}
