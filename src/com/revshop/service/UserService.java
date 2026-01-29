package com.revshop.service;

import java.util.Scanner;
import com.revshop.model.User;
import com.revshop.dao.impl.UserDAOImpl;
import com.revshop.dao.UserDAO;

public class UserService {
  private UserDAO userDAO = new UserDAOImpl();
  
  public void userRegistration(String role, Scanner sc){
	  
	  System.out.print("Enter Name: ");
	  String name = sc.nextLine();
	  
	  System.out.print("Enter Email: ");
	  String email = sc.nextLine();
	  
	  System.out.print("Enter Password: ");
	  String password = sc.nextLine();
	  
	  String businessName = null;
	  
	  if(role.equalsIgnoreCase("SELLER")){
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
  }
  
  public User login(Scanner sc){
	  
	  System.out.print("Enter email: ");
	  String email = sc.nextLine();
	  
	  System.out.print("Enter password: ");
	  String password = sc.nextLine();
	  
	  User user = userDAO.login(email, password);
	  
	  if(user == null){
		  System.out.println("\nInvalid email or password. Re-directing to Main menu.");
		  return null;
	  }
	  System.out.println("\nLogin Successful");
	  return user;
  }
}
