package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;
import com.revshop.util.PasswordUtil;
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
		
		System.out.print("Set Hint (used for password recovery): ");
		String securityAnswer = sc.nextLine();

		String businessName = null;

		if (role.equalsIgnoreCase("SELLER")) {
			LOGGER.info("Registering a seller account");
			System.out.print("Enter Business Name: ");
			businessName = sc.nextLine();
		}
		
		if (!isValidRegistration(name, email, password, role, businessName)) {
		    return;
		}

		String hashedPassword = PasswordUtil.hashPassword(password);
		
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(hashedPassword);
		user.setRole(role);
		user.setBusinessName(businessName);
		user.setSecurtiyAnswer(PasswordUtil.hashPassword(securityAnswer));


		userDAO.registerUser(user);

		LOGGER.info("User registration completed successfully for role: "
				+ role);
	}

	public User login(Scanner sc) {

		LOGGER.info("User login attempt started");

		System.out.print("Enter email: ");
		String email = sc.nextLine();

		System.out.print("Enter password: ");
		String password = sc.nextLine();
		
		if (email.trim().isEmpty() || password.trim().isEmpty()) {
		    System.out.println("Email and password cannot be empty");
		    return null;
		}

		String hashedPassword = PasswordUtil.hashPassword(password);

		User user = userDAO.login(email, hashedPassword);

		if (user == null) {
			LOGGER.warning("Login failed due to invalid credentials");
			System.out
					.println("\nInvalid email or password. Re-directing to Main menu.");
			return null;
		}

		LOGGER.info("Login successful for role: " + user.getRole());
		System.out.println("\nLogin Successful");
		return user;
	}
	
	private boolean isValidRegistration(
	        String name, String email, String password,
	        String role, String businessName) {

	    if (name == null || name.trim().length() < 3) {
	        System.out.println("Invalid name");
	        return false;
	    }

	    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
	        System.out.println("Invalid email format");
	        return false;
	    }

	    if (password.length() < 6) {
	        System.out.println("Password too short");
	        return false;
	    }

	    if (role.equalsIgnoreCase("SELLER") &&
	        (businessName == null || businessName.trim().isEmpty())) {
	        System.out.println("Business name required for seller");
	        return false;
	    }

	    return true;
	}
	
	public void changePassword(User user, Scanner sc) {

	    System.out.print("Enter old password: ");
	    String oldPwd = sc.nextLine();

	    System.out.print("Enter new password: ");
	    String newPwd = sc.nextLine();

	    System.out.print("Confirm new password: ");
	    String confirmPwd = sc.nextLine();

	    if (!newPwd.equals(confirmPwd)) {
	        System.out.println("Passwords do not match");
	        return;
	    }

	    if (newPwd.length() < 6) {
	        System.out.println("Password must be at least 6 characters");
	        return;
	    }

	    String oldHashed = PasswordUtil.hashPassword(oldPwd);
	    String newHashed = PasswordUtil.hashPassword(newPwd);

	    boolean success = userDAO.changePassword(
	        user.getUserId(),
	        oldHashed,
	        newHashed
	    );

	    if (success) {
	        System.out.println("Password changed successfully");
	        LOGGER.info("Password changed for userId: " + user.getUserId());
	    } else {
	        System.out.println("Old password is incorrect");
	        LOGGER.warning("Password change failed for userId: " + user.getUserId());
	    }
	}
	
	public void forgotPassword(Scanner sc) {

	    System.out.print("Enter registered email: ");
	    String email = sc.nextLine();

	    System.out.print("Enter security answer: ");
	    String answer = sc.nextLine();

	    System.out.print("Enter new password: ");
	    String newPwd = sc.nextLine();

	    if (newPwd.length() < 6) {
	        System.out.println("Password too short");
	        return;
	    }

	    boolean success = userDAO.resetPassword(
	        email,
	        PasswordUtil.hashPassword(answer),
	        PasswordUtil.hashPassword(newPwd)
	    );

	    if (success) {
	        System.out.println("\nPassword reset successful");
	        LOGGER.info("Password reset for email: " + email);
	    } else {
	        System.out.println("\nInvalid details. Password reset failed");
	        LOGGER.warning("Password reset failed for email: " + email);
	    }
	}



}
