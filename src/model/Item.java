package model;

/**
 * Represents an item with a unique code, name, category, price, and discount.
 */
public class Item {
    private String itemCode;   // Unique code for the item
    private String itemName;   // Name of the item
    private int categoryId;    // ID of the category to which the item belongs
    private double price;      // Price of the item
    private double discount;
    private int quantity;
    // Discount percentage on the item

    // No-argument constructor
    public Item() {
    }

    // Parameterized constructor
    public Item(String itemCode, String itemName, int categoryId, double price, double discount) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.price = price;
        this.discount = discount;
    }

    // Getters and setters
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Calculates the discount amount based on the price and discount percentage.
     * @return The discount amount.
     */
    public double calculateDiscountAmount() {
        return price * (discount / 100);
    }

    /**
     * Calculates the final price after applying the discount.
     * @return The final price after discount.
     */
    public double calculateFinalPrice() {
        return price - calculateDiscountAmount();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", discount=" + discount +
                ", discountAmount=" + calculateDiscountAmount() +
                ", finalPrice=" + calculateFinalPrice() +
                '}';
    }


}