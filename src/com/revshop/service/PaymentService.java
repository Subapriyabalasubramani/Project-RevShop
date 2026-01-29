package com.revshop.service;

import java.util.Scanner;

public class PaymentService {
	
	public String processPayment(Scanner sc) {

        System.out.println("\n--- Payment Options ---");
        System.out.println("1. Cash on Delivery (Default)");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        System.out.println("4. Net Banking");
        System.out.print("Choose payment option: ");

        int choice = sc.nextInt();
        sc.nextLine();

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
            System.out.println("Invalid choice. Defaulting to COD.");
        }

        System.out.print("Confirm payment using " + paymentMode + "? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            return null; // payment cancelled
        }

        System.out.println("Payment successful using " + paymentMode);
        return paymentMode;
    }

}
