package com.revshop.config;

import org.junit.Test;
import static org.junit.Assert.*;

public class DBConnectionTest {

	@Test
    public void testClassLoading() {
        try {
            Class.forName("com.revshop.config.DBConnection");
            assertTrue(true);
        } catch (ClassNotFoundException e) {
            fail("DBConnection class failed to load");
        }
    }
}
