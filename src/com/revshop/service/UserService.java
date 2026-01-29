package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;
import com.revshop.dao.impl.UserDAOImpl;
import com.revshop.dao.UserDAO;
import java.util.logging.Logger;

public class UserService {
	private static final Logger LOGGER = Logger.getLogger(UserService.class
			.getName());

	private UserDAO userDAO = new UserDAOImpl();

	public void userRegistration(String role, Scanner sc) {
		
		LOGGER.info("User registration started. Role: " + role);

		System.out.print("Enter Name: ");
		String name = sc.nextLine();

		System.out.print("Enter Email: ");
		String email = sc.nextLine();

		System.out.print("Enter Password: ");
		String password = sc.nextLine();

		String businessName = null;

		if (role.equalsIgnoreCase("SELLER")) {
			LOGGER.info("Registering a seller account");
			System.out.print("Enter Business Name: ");
			businessName = sc.nextLine();
		}

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setRole(role);
		user.setBusinessName(businessName);

		userDAO.registerUser(user);
		
		LOGGER.info("User registration completed successfully for role: " + role);
	}

	public User login(Scanner sc) {
		
		LOGGER.info("User login attempt started");

		System.out.print("Enter email: ");
		String email = sc.nextLine();

		System.out.print("Enter password: ");
		String password = sc.nextLine();

		User user = userDAO.login(email, password);

		if (user == null) {
			LOGGER.warning("Login failed due to invalid credentials");
			System.out.println("\nInvalid email or password. Re-directing to Main menu.");
			return null;
		}
		
		LOGGER.info("Login successful for role: " + user.getRole());
		System.out.println("\nLogin Successful");
		return user;
	}
}
