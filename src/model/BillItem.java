package model;

public class BillItem {
    private int billItemId;
    private int billId;
    private String itemCode;
    private int quantity;
    private double totalPrice;
    private double price;
    private String itemName;
    private double discount; // Discount percentage



    // Getters and setters
    public int getBillItemId() {
        return billItemId;
    }

    public void setBillItemId(int billItemId) {
        this.billItemId = billItemId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice(); // Update totalPrice when quantity changes
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = calculateTotalPrice(); // Update totalPrice when price changes
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.totalPrice = calculateTotalPrice(); // Update totalPrice when discount changes
    }

    // Calculate the discount amount
    public double getDiscountAmount() {
        return price * quantity * (discount / 100);
    }

    // Calculate the total price after discount
    private double calculateTotalPrice() {
        double itemTotal = price * quantity;
        double discountAmount = itemTotal * (discount / 100);
        return itemTotal - discountAmount;
    }
}