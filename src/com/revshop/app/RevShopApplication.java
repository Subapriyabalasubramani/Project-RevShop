package com.revshop.app;

import java.util.Scanner;
import com.revshop.model.User;
import com.revshop.service.UserService;
import com.revshop.service.SellerService;
import com.revshop.service.BuyerService;

public class RevShopApplication {

	public static void main(String[] args) {

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

			switch (choice) {
			case 1:
				userService.userRegistration("BUYER", sc);
				if (!(askUserConsent(sc))) {
					exit = true;
					System.out.println("Thank you for using RevShop");
				}
				break;
			case 2:
				userService.userRegistration("SELLER", sc);
				if (!(askUserConsent(sc))) {
					exit = true;
					System.out.println("Thank you for using RevShop");
				}
				break;
			case 3:
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
				userService.forgotPassword(sc);
				break;
				
			case 5:
				exit = true;
				System.out.println("\nThank you for using RevShop");
				break;
			default:
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
