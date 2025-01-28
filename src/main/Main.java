package main;

import controller.BillingController;
import controller.ItemsController;
import controller.StockController;
import controller.ShelfController;
import controller.PaymentController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BillingController billingController = new BillingController();
        StockController stockController = new StockController();
        ItemsController itemsController = new ItemsController();
        ShelfController shelfController = new ShelfController();
        PaymentController paymentController = new PaymentController();

        while (true) {
            System.out.println("Welcome to SYOS Billing System");
            System.out.println("1. Start Billing");
            System.out.println("2. Manage Stock");
            System.out.println("3. Move Stock to Shelf");
            System.out.println("4. Add Item");
            System.out.println("5. Process Payment");
            System.out.println("6. View Stock");
            System.out.println("7. View Shelf Items"); // New option
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    billingController.startBilling();
                    break;
                case 2:
                    stockController.manageStock();
                    break;
                case 3:
                    stockController.moveStockToShelf();
                    break;
                case 4:
                    itemsController.addItem();
                    break;
                case 5:
                    System.out.print("Enter final total: ");
                    double finalTotal = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter cash tendered: ");
                    double cashTendered = Double.parseDouble(scanner.nextLine());
                    paymentController.processPayment(cashTendered, finalTotal);
                    break;
                case 6:
                    stockController.viewStock();
                    break;
                case 7:
                    shelfController.viewShelfItems(); // New option handling
                    break;
                case 8:
                    System.out.println("Exiting SYOS Billing System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}