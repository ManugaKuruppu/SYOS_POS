package model;

import java.sql.Date;

public class Stock {
    private int stockId;
    private String itemCode;
    private Date purchaseDate;
    private int quantity;
    private Date expiryDate;


    public Stock() {}

    // Constructor with parameters
    public Stock(int stockId, String itemCode, Date purchaseDate, int quantity, Date expiryDate) {
        this.stockId = stockId;
        this.itemCode = itemCode;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }
    // Getters and setters
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}