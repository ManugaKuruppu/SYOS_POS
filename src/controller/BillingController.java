package controller;

import model.Bill;
import model.BillItem;
import service.BillingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BillingController {
    private BillingService billingService;
    private Scanner scanner;

    public BillingController() {
        billingService = new BillingService();
        scanner = new Scanner(System.in);
    }

    public void startBilling() {
        List<String> itemCodes = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        while (true) {
            System.out.print("Enter item code (or 'done' to finish): ");
            String itemCode = scanner.nextLine();
            if (itemCode.equalsIgnoreCase("done")) {
                break;
            }
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            itemCodes.add(itemCode);
            quantities.add(quantity);
        }

        // Step 1: Prepare the bill and display the total price
        Bill preparedBill = billingService.prepareBill(itemCodes, quantities);

        // Display the prepared bill with total and final total
        displayPreparedBill(preparedBill);

        // Step 2: Ask for cash tendered and complete the bill
        System.out.print("Enter cash tendered: ");
        double cashTendered = Double.parseDouble(scanner.nextLine());

        Bill completedBill = billingService.completeBill(preparedBill, cashTendered);

        if (completedBill != null) {
            System.out.println("Bill completed successfully.");
            displayBill(completedBill);
        } else {
            System.out.println("Failed to complete the bill.");
        }
    }

    private void displayPreparedBill(Bill bill) {
        System.out.println("\n================== BILL ==================");
        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s%n", "Item Code", "Item Name", "Quantity", "Price", "Discount", "Total");
        System.out.println("------------------------------------------");
        for (BillItem item : bill.getBillItems()) {
            System.out.printf("%-10s %-15s %-10d %-10.2f %-10.2f %-10.2f%n", item.getItemCode(), item.getItemName(), item.getQuantity(), item.getPrice(), item.getDiscountAmount(), item.getTotalPrice());
        }
        System.out.println("------------------------------------------");

        System.out.printf("%-25s %-10.2f%n", "Final Total:", bill.getFinalTotal());
        System.out.println("==========================================");
    }

    private void displayBill(Bill bill) {
        System.out.println("\n================== BILL ==================");
        System.out.println("Bill ID: " + bill.getBillId());
        System.out.println("==========================================");
        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s%n", "Item Code", "Item Name", "Quantity", "Price", "Discount", "Total");
        System.out.println("------------------------------------------");
        for (BillItem item : bill.getBillItems()) {
            System.out.printf("%-10s %-15s %-10d %-10.2f %-10.2f %-10.2f%n", item.getItemCode(), item.getItemName(), item.getQuantity(), item.getPrice(), item.getDiscountAmount(), item.getTotalPrice());
        }
        System.out.println("------------------------------------------");

        System.out.printf("%-25s %-10.2f%n", "Final Total:", bill.getFinalTotal());
        System.out.printf("%-25s %-10.2f%n", "Total Discount:", bill.getTotalDiscount());
        System.out.printf("%-25s %-10.2f%n", "Cash Tendered:", bill.getCashTendered());
        System.out.printf("%-25s %-10.2f%n", "Change Amount:", bill.getChangeAmount());
        System.out.println("==========================================");
    }
}