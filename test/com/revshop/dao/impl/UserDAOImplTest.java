package com.revshop.dao.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import com.revshop.model.User;

public class UserDAOImplTest {

	@Test
	public void testRegisterUser() {
		UserDAOImpl dao = new UserDAOImpl();

		User user = new User();
		user.setName("JUnit User");
		user.setEmail("junit_user@test.com");
		user.setPassword("test123");
		user.setRole("BUYER");
		user.setBusinessName(null);

		dao.registerUser(user);
	}

	@Test
	public void testLoginSuccess() {
		UserDAOImpl dao = new UserDAOImpl();

		User user = dao.login("junit_user@test.com", "test123");

		assertNotNull(user);
		assertEquals("JUnit User", user.getName());
		assertEquals("BUYER", user.getRole());
	}

	@Test
	public void testLoginFailure() {
		UserDAOImpl dao = new UserDAOImpl();

		User user = dao.login("invalid@test.com", "wrongpass");

		assertNull(user);
	}

}
