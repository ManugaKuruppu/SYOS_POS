package main;

import controller.BillingController;
import controller.ItemsController;
import controller.StockController;
import controller.ShelfController;
import controller.PaymentController;
import controller.UserController;
import service.ReportService;

import java.sql.Timestamp;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BillingController billingController = new BillingController();
    private static StockController stockController = new StockController();
    private static ItemsController itemsController = new ItemsController();
    private static ShelfController shelfController = new ShelfController();
    private static PaymentController paymentController = new PaymentController();
    private static ReportService reportService = new ReportService();
    private static UserController userController = new UserController();
    private static boolean isLoggedIn = false;

    public static void main(String[] args) {
        while (true) {
            if (!isLoggedIn) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        userController.registerUser();
                        break;
                    case 2:
                        isLoggedIn = userController.loginUser();
                        break;
                    case 3:
                        System.out.println("Exiting SYOS Billing System. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Welcome to SYOS Billing System");
                System.out.println("1. Billing Operations");
                System.out.println("2. Manage Stock");
                System.out.println("3. Shelf Operations");
                System.out.println("4. Generate Reports");
                System.out.println("5. Logout");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        billingOperations();
                        break;
                    case 2:
                        manageStock();
                        break;
                    case 3:
                        shelfOperations();
                        break;
                    case 4:
                        generateReports();
                        break;
                    case 5:
                        isLoggedIn = false;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private static void billingOperations() {
        while (true) {
            System.out.println("\nBilling Operations");
            System.out.println("1. Start Billing");
            System.out.println("2. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    billingController.startBilling();
                    break;
                case 2:
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageStock() {
        while (true) {
            System.out.println("\nManage Stock");
            System.out.println("1. Add Item");
            System.out.println("2. Add Stock");
            System.out.println("3. View Stock");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    itemsController.addItem();
                    break;
                case 2:
                    stockController.manageStock();
                    break;
                case 3:
                    stockController.viewStock();
                    break;
                case 4:
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void shelfOperations() {
        while (true) {
            System.out.println("\nShelf Operations");
            System.out.println("1. Move Stock to Shelf");
            System.out.println("2. View Shelf Items");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    stockController.moveStockToShelf();
                    break;
                case 2:
                    shelfController.viewShelfItems();
                    break;
                case 3:
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void generateReports() {
        while (true) {
            System.out.println("\nGenerate Reports");
            System.out.println("1. Total Sale Report");
            System.out.println("2. Reshelve Report");
            System.out.println("3. Reorder Report");
            System.out.println("4. Stock Report");
            System.out.println("5. Bill Report");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String dateStr = scanner.nextLine();
                    Timestamp date = Timestamp.valueOf(dateStr + " 00:00:00");
                    reportService.generateTotalSaleReport(date);
                    break;
                case 2:
                    reportService.generateReshelveReport();
                    break;
                case 3:
                    reportService.generateReorderReport();
                    break;
                case 4:
                    reportService.generateStockReport();
                    break;
                case 5:
                    reportService.generateBillReport();
                    break;
                case 6:
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}