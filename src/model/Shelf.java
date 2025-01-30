package model;

public class Shelf {
    private int shelfId;
    private String itemCode;
    private int quantity;
    private String expiryDate;
    private String itemName;

    public Shelf() {
        // Default constructor
    }

    // Constructor to match 'Shelf()' cannot be applied to '(java.lang.String, java.lang.String, int, java.lang.String)'
    public Shelf(String itemCode, String itemName, int quantity, String expiryDate) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public Shelf(String itemCode, int quantity, String expiryDate) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }


    // Getters and setters
    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
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
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }



}