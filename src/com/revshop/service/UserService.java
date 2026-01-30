package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;
import com.revshop.util.PasswordUtil;
import com.revshop.dao.impl.UserDAOImpl;
import com.revshop.dao.UserDAO;
import org.apache.log4j.Logger;
import com.revshop.util.ValidationUtil;

public class UserService {
	private static final Logger logger =
            Logger.getLogger(UserService.class);

	private UserDAO userDAO = new UserDAOImpl();

    // ADDED THIS (for testing)
    void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

	public void userRegistration(String role, Scanner sc) {

		logger.info("User registration started. Role: " + role);

		System.out.print("Enter Name: ");
		String name = sc.nextLine();
		if (!ValidationUtil.isValidName(name)) return;

		System.out.print("Enter Email: ");
		String email = sc.nextLine();
		if (!ValidationUtil.isValidEmail(email)) return;

		System.out.print("Enter Password: ");
		String password = sc.nextLine();
		if (!ValidationUtil.isValidPassword(password)) return;

		System.out.print("Set Hint (used for password recovery): ");
		String securityAnswer = sc.nextLine();

		String businessName = null;
		if (role.equalsIgnoreCase("SELLER")) {
		    System.out.print("Enter Business Name: ");
		    businessName = sc.nextLine();
		    if (!ValidationUtil.isValidBusinessName(role, businessName)) return;
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

		logger.info("User registration completed successfully for role: "
				+ role);
	}

	public User login(Scanner sc) {

		logger.info("User login attempt started");

		System.out.print("Enter email: ");
		String email = sc.nextLine();
		if (!ValidationUtil.isValidEmail(email)) return null;

		System.out.print("Enter password: ");
		String password = sc.nextLine();
		if (!ValidationUtil.isValidPassword(password)) return null;

		String hashedPassword = PasswordUtil.hashPassword(password);

		User user = userDAO.login(email, hashedPassword);

		if (user == null) {
			logger.warn("Login failed due to invalid credentials");
			System.out
					.println("\nInvalid email or password. Re-directing to Main menu.");
			return null;
		}

		logger.info("Login successful for role: " + user.getRole());
		System.out.println("\nLogin Successful");
		return user;
	}

	
	public void changePassword(User user, Scanner sc) {

	    System.out.print("Enter old password: ");
	    String oldPwd = sc.nextLine();
	    
	    if (oldPwd.length() < 6 || oldPwd == null) {
	        System.out.println("Password must be at least 6 characters");
	        return;
	    }
	    
	    System.out.print("Enter new password: ");
	    String newPwd = sc.nextLine();
	    
	    if (newPwd.length() < 6 || newPwd == null) {
	        System.out.println("Password must be at least 6 characters");
	        return;
	    }
	    
	    System.out.print("Confirm new password: ");
	    String confirmPwd = sc.nextLine();

	    if (!newPwd.equals(confirmPwd)) {
	        System.out.println("Passwords do not match");
	        return;
	    }

	    String oldHashed = PasswordUtil.hashPassword(oldPwd);
	    String newHashed = PasswordUtil.hashPassword(newPwd);
	    
	    if(oldHashed.equals(newHashed)){
	    	System.out.println("New Password should be same as previous password");
	        return;
	    }

	    boolean success = userDAO.changePassword(
	        user.getUserId(),
	        oldHashed,
	        newHashed
	    );

	    if (success) {
	        System.out.println("Password changed successfully");
	        logger.info("Password changed for userId: " + user.getUserId());
	    } else {
	        System.out.println("Old password is incorrect");
	        logger.warn("Password change failed for userId: " + user.getUserId());
	    }
	}
	
	public void forgotPassword(Scanner sc) {

		System.out.print("Enter registered email: ");
		String email = sc.nextLine();
		if (!ValidationUtil.isValidEmail(email)) return;

		System.out.print("Enter security answer: ");
		String answer = sc.nextLine();
		if (answer.trim().isEmpty()) {
		    System.out.println("Security answer cannot be empty");
		    return;
		}

		System.out.print("Enter new password: ");
		String newPwd = sc.nextLine();
		if (!ValidationUtil.isValidPassword(newPwd)) return;

	    boolean success = userDAO.resetPassword(
	        email,
	        PasswordUtil.hashPassword(answer),
	        PasswordUtil.hashPassword(newPwd)
	    );

	    if (success) {
	        System.out.println("\nPassword reset successful");
	        logger.info("Password reset for email: " + email);
	    } else {
	        System.out.println("\nInvalid details. Password reset failed");
	        logger.warn("Password reset failed for email: " + email);
	    }
	}
}
