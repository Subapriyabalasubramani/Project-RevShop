package com.revshop.app;

import org.apache.log4j.Logger;
import java.util.Scanner;
import com.revshop.model.User;
import com.revshop.service.UserService;
import com.revshop.service.SellerService;
import com.revshop.service.BuyerService;

public class RevShopApplication {
	
	private static final Logger logger =
            Logger.getLogger(RevShopApplication.class);

	public static void main(String[] args) {

		logger.info("RevShop application started");
		
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		UserService userService = new UserService();
		while (!exit) {
			System.out.println("\n--------------------------");
			System.out.println("    Welcome to RevShop");
			System.out.println("--------------------------");
			System.out.println("1. Register as Buyer");
			System.out.println("2. Register as Seller");
			System.out.println("3. Login");
			System.out.println("4. Forgot Password");
			System.out.println("5. Exit");
			System.out.println("--------------------------");
			System.out.print("Enter your choice: ");

			int choice = sc.nextInt();
			sc.nextLine();
			
			logger.info("User selected menu option: " + choice);

			switch (choice) {
			case 1:
				logger.info("Buyer registration started");
				userService.userRegistration("BUYER", sc);
				if (!(askUserConsent(sc))) {
					exit = true;
					System.out.println("Thank you for using RevShop");
				}
				break;
			case 2:
				logger.info("Seller registration started");
				userService.userRegistration("SELLER", sc);
				if (!(askUserConsent(sc))) {
					exit = true;
					System.out.println("Thank you for using RevShop");
				}
				break;
			case 3:
				logger.info("Login attempt");
				User user = userService.login(sc);
				if(user!=null){
					if("SELLER".equalsIgnoreCase(user.getRole())){
						System.out.println("\nWelcome " + user.getName());
						SellerService sellerService = new SellerService();
						sellerService.showSellerMenu(user, sc);
					}
					else{
						System.out.println("\nWelcome " + user.getName());
						BuyerService buyerService = new BuyerService();
						buyerService.showBuyerMenu(user, sc);
					}
				}
				break;
				
			case 4:
				logger.info("Forgot password initiated");
				userService.forgotPassword(sc);
				break;
				
			case 5:
				exit = true;
				logger.info("Application exited by user");
				System.out.println("\nThank you for using RevShop");
				break;
			default:
				logger.warn("Invalid menu choice entered");
				System.out.println("\nInvalid choice. Please try again");
				break;
			}
		}

	}

	static boolean askUserConsent(Scanner s) {
		System.out.println("\nDo you want to proceed (yes/no): ");
		String input = s.nextLine();
		return input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y");
	}

}
