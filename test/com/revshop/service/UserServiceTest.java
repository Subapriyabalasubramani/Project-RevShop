package com.revshop.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.dao.UserDAO;
import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDAO userDAO; // MOCKED dependency

	@InjectMocks
	private UserService userService; // real service, mocked DAO injected

	@Before
	public void setup() {
		userService.setUserDAO(userDAO);
	}

	@Test
	public void testUserRegistration_success_buyer() {

		Scanner sc = new Scanner("JUnit User\n" + "junit@gmail.com\n"
				+ "test123\n" + "hint\n");

		userService.userRegistration("BUYER", sc);

		// verify DAO interaction
		verify(userDAO, times(1)).registerUser(any(User.class));
	}

	@Test
	public void testUserRegistration_fail_invalidEmail() {

		Scanner sc = new Scanner("JUnit User\n" + "invalid\n" + "test123\n");

		userService.userRegistration("BUYER", sc);

		// DAO should NOT be called
		verify(userDAO, never()).registerUser(any(User.class));
	}

	@Test
	public void testLogin_success() {

		Scanner sc = new Scanner("test@gmail.com\n" + "test123\n");

		User mockUser = new User();
		mockUser.setRole("BUYER");

		when(userDAO.login(anyString(), anyString())).thenReturn(mockUser);

		User user = userService.login(sc);

		assertNotNull(user);
		verify(userDAO).login(anyString(), anyString());
	}

	@Test
	public void testLogin_fail_invalidCredentials() {

		Scanner sc = new Scanner("test@gmail.com\n" + "wrongpass\n");

		when(userDAO.login(anyString(), anyString())).thenReturn(null);

		User user = userService.login(sc);

		assertNull(user);
		verify(userDAO).login(anyString(), anyString());
	}

	@Test
	public void testChangePassword_success() {

		User user = new User();
		user.setUserId(1);

		Scanner sc = new Scanner("oldpass\n" + "newpass\n" + "newpass\n");

		when(userDAO.changePassword(anyInt(), anyString(), anyString()))
				.thenReturn(true);

		userService.changePassword(user, sc);

		verify(userDAO).changePassword(anyInt(), anyString(), anyString());
	}

	@Test
	public void testChangePassword_fail_mismatch() {

		User user = new User();
		user.setUserId(1);

		Scanner sc = new Scanner("oldpass\n" + "newpass\n" + "wrong\n");

		userService.changePassword(user, sc);

		verify(userDAO, never()).changePassword(anyInt(), anyString(),
				anyString());
	}

	@Test
	public void testForgotPassword_success() {

		Scanner sc = new Scanner("test@gmail.com\n" + "hint\n" + "newpass\n");

		when(userDAO.resetPassword(anyString(), anyString(), anyString()))
				.thenReturn(true);

		userService.forgotPassword(sc);

		verify(userDAO).resetPassword(anyString(), anyString(), anyString());
	}
}
