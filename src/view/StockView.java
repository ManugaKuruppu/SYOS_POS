package view;

import model.Stock;

public class StockView {
    public void displayStock(Stock stock) {
        System.out.println("Stock ID: " + stock.getStockId());
        System.out.println("Item Code: " + stock.getItemCode());
        System.out.println("Purchase Date: " + stock.getPurchaseDate());
        System.out.println("Quantity: " + stock.getQuantity());
        System.out.println("Expiry Date: " + stock.getExpiryDate());
    }
}