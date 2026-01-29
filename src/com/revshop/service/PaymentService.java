package com.revshop.service;

import java.util.Scanner;
import java.util.logging.Logger;


public class PaymentService {
	private static final Logger LOGGER =
	        Logger.getLogger(PaymentService.class.getName());
	
	public String processPayment(Scanner sc) {
		LOGGER.info("Payment process started");

        System.out.println("\n--- Payment Options ---");
        System.out.println("1. Cash on Delivery (Default)");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        System.out.println("4. Net Banking");
        System.out.print("Choose payment option: ");

        int choice = sc.nextInt();
        sc.nextLine();
        
        LOGGER.info("Payment option selected. optionNumber: " + choice);

        String paymentMode = "COD"; // default

        switch (choice) {
        case 1:
            paymentMode = "COD";
            break;
        case 2:
            paymentMode = "CARD";
            break;
        case 3:
            paymentMode = "UPI";
            break;
        case 4:
            paymentMode = "NET_BANKING";
            break;
        default:
        	LOGGER.warning("Invalid payment option selected. Defaulting to COD.");
            System.out.println("Invalid choice. Defaulting to COD.");
        }

        System.out.print("Confirm payment using " + paymentMode + "? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
        	LOGGER.info("Payment cancelled by user");
            return null; // payment cancelled
        }

        LOGGER.info("Payment successful. Mode: " + paymentMode);
        System.out.println("Payment successful using " + paymentMode);
        return paymentMode;
    }

}
