package controller;

import model.ShelfItem;
import service.ShelfService;

import java.util.List;

public class ShelfController {
    private ShelfService shelfService;

    public ShelfController() {
        shelfService = new ShelfService();
    }

    public void viewShelfItems() {
        List<ShelfItem> shelfItems = shelfService.getAllShelfItems();
        displayShelfItems(shelfItems);
    }

    private void displayShelfItems(List<ShelfItem> shelfItems) {
        System.out.println("\n=================== AVAILABLE SHELF ITEMS ===================");
        System.out.printf("%-10s %-20s %-10s %-15s%n", "Item Code", "Item Name", "Quantity", "Expiry Date");
        System.out.println("-------------------------------------------------------------");
        for (ShelfItem item : shelfItems) {
            System.out.printf("%-10s %-20s %-10d %-15s%n", item.getItemCode(), item.getItemName(), item.getQuantity(), item.getExpiryDate());
        }
        System.out.println("=============================================================");
    }
}