package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int billId;
    private Timestamp billDate;
    private double total;
    private double finalTotal;
    private double cashTendered;
    private double changeAmount;
    private List<BillItem> billItems;

    public Bill() {
        this.billItems = new ArrayList<>(); // Initialize billItems list
    }

    // Getters and setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Timestamp getBillDate() {
        return billDate;
    }

    public void setBillDate(Timestamp billDate) {
        this.billDate = billDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
        // Update final total whenever total is set
        this.finalTotal = calculateFinalTotal();
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }

    public double getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(double cashTendered) {
        this.cashTendered = cashTendered;
        this.changeAmount = calculateChangeAmount();
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems != null ? billItems : new ArrayList<>();
        // Update total and final total whenever bill items are set
        this.total = calculateTotal();
        this.finalTotal = calculateFinalTotal();
    }

    // Calculate the total amount before discounts
    private double calculateTotal() {
        return billItems != null ? billItems.stream().mapToDouble(BillItem::getTotalPrice).sum() : 0;
    }

    // Calculate the final total after discounts
    private double calculateFinalTotal() {
        return billItems != null ? billItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum()
                - getTotalDiscount() : 0;
    }

    // Calculate the total discount amount
    public double getTotalDiscount() {
        return billItems != null ? billItems.stream().mapToDouble(BillItem::getDiscountAmount).sum() : 0;
    }

    // Calculate the change amount
    private double calculateChangeAmount() {
        return cashTendered - finalTotal;
    }

    // Add item to bill and update totals
    public void addBillItem(BillItem billItem) {
        if (billItem != null) {
            this.billItems.add(billItem);
            this.total = calculateTotal();
            this.finalTotal = calculateFinalTotal();
            this.changeAmount = calculateChangeAmount();
        }
    }

    // Remove item from bill and update totals
    public void removeBillItem(BillItem billItem) {
        if (billItems != null && billItem != null) {
            this.billItems.remove(billItem);
            this.total = calculateTotal();
            this.finalTotal = calculateFinalTotal();
            this.changeAmount = calculateChangeAmount();
        }
    }
}