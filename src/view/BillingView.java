package view;

import model.Bill;
import model.BillItem;

public class BillingView {
    public void displayBill(Bill bill) {
        System.out.println("Bill ID: " + bill.getBillId());
        System.out.println("Date: " + bill.getBillDate());
        System.out.println("Items:");
        for (BillItem item : bill.getBillItems()) {
            System.out.println("- " + item.getItemCode() + " x " + item.getQuantity() + " @ " + item.getTotalPrice());
        }
        System.out.println("Total: " + bill.getTotal());
        System.out.println("Final Total: " + bill.getFinalTotal());
        System.out.println("Cash Tendered: " + bill.getCashTendered());
        System.out.println("Change: " + bill.getChangeAmount());
    }
}