package com.revshop.util;

public class ValidationUtil {
	
	private ValidationUtil(){
		
	}

	public static boolean isValidName(String name) {
		if (name == null || name.trim().length() < 3) {
			System.out.println("Invalid name (minimum 3 characters required)");
			return false;
		}
		return true;
	}

	public static boolean isValidEmail(String email) {
		if (email == null
				|| !email.matches("^[A-Za-z0-9+_.-]+@(gmail|email)\\.(com|org|in)$")) {
			System.out
					.println("Invalid email format (allowed: gmail/email with .com/.org/.in)");
			return false;
		}
		return true;
	}

	public static boolean isValidPassword(String password) {
		if (password == null || password.length() < 6) {
			System.out.println("Password must be at least 6 characters");
			return false;
		}
		return true;
	}

	public static boolean isValidBusinessName(String role, String businessName) {

		if ("SELLER".equalsIgnoreCase(role)
				&& (businessName == null || businessName.trim().isEmpty())) {

			System.out.println("Business name required for seller");
			return false;
		}
		return true;
	}

}
