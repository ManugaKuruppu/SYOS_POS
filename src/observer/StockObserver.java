package observer;

import java.util.HashMap;
import java.util.Map;

public class StockObserver implements Observer {
    private Map<String, Integer> lowStockItems = new HashMap<>();

    @Override
    public void update(String itemCode, int quantity) {
        System.out.println("Updating low stock items: " + itemCode + " with quantity: " + quantity);
        lowStockItems.put(itemCode, quantity);
    }

    public void displayLowStockItems() {
        System.out.println("\n=================== LOW STOCK ITEMS ===================");
        System.out.printf("%-10s %-10s%n", "Item Code", "Quantity");
        System.out.println("-----------------------------------");
        for (Map.Entry<String, Integer> entry : lowStockItems.entrySet()) {
            System.out.printf("%-10s %-10d%n", entry.getKey(), entry.getValue());
        }
        System.out.println("=====================================================");
    }
}