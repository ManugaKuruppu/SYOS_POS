package controller;

import model.Stock;
import service.StockService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class StockController {
    private StockService stockService;
    private Scanner scanner;

    public StockController() {
        stockService = new StockService();
        scanner = new Scanner(System.in);
    }

    public void manageStock() {
        System.out.print("Enter item code: ");
        String itemCode = scanner.nextLine().trim();
        System.out.print("Enter purchase date (YYYY-MM-DD): ");
        Date purchaseDate = Date.valueOf(scanner.nextLine());
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter expiry date (YYYY-MM-DD): ");
        Date expiryDate = Date.valueOf(scanner.nextLine());

        Stock stock = new Stock();
        stock.setItemCode(itemCode);
        stock.setPurchaseDate(purchaseDate);
        stock.setQuantity(quantity);
        stock.setExpiryDate(expiryDate);

        if (stockService.addStock(stock)) {
            System.out.println("Stock added successfully!");
        } else {
            System.out.println("Failed to add stock. Item code does not exist: " + itemCode);
        }
    }

    public void moveStockToShelf() {
        System.out.print("Enter stock ID: ");
        int stockId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter quantity to move to shelf: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (stockService.moveStockToShelf(stockId, quantity)) {
            System.out.println("Stock moved to shelf successfully!");
        } else {
            System.out.println("Failed to move stock to shelf.");
        }
    }

    public void viewStock() {
        List<Stock> stockList = stockService.getAllStock();
        displayStock(stockList);
    }

    private void displayStock(List<Stock> stockList) {
        System.out.println("\n=================== CURRENT STOCK ===================");
        System.out.printf("%-10s %-15s %-10s %-15s %-15s%n", "Stock ID", "Item Code", "Quantity", "Purchase Date", "Expiry Date");
        System.out.println("-----------------------------------------------------");
        for (Stock item : stockList) {
            System.out.printf("%-10d %-15s %-10d %-15s %-15s%n", item.getStockId(), item.getItemCode(), item.getQuantity(), item.getPurchaseDate(), item.getExpiryDate());
        }
        System.out.println("=====================================================");
    }
}