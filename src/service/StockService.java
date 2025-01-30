package service;

import dao.StockDAO;
import dao.ShelfDAO;
import model.Stock;
import model.Shelf;
import observer.Observer;
import observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class StockService implements Subject {
    StockDAO stockDAO;
    ShelfDAO shelfDAO;
    private List<Observer> observers;
    private String lowStockItemCode;
    private int lowStockQuantity;

    public StockService() {
        stockDAO = new StockDAO();
        shelfDAO = new ShelfDAO();
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        System.out.println("Notifying observers about low stock: " + lowStockItemCode + " with quantity: " + lowStockQuantity);
        for (Observer observer : observers) {
            observer.update(lowStockItemCode, lowStockQuantity);
        }
    }

    public boolean addStock(Stock stock) {
        String itemCode = stock.getItemCode().trim();
        if (!stockDAO.itemCodeExists(itemCode)) {
            System.out.println("Item code does not exist: " + itemCode);
            return false;
        }
        return stockDAO.addStock(stock);
    }

    public boolean moveStockToShelf(String itemCode, int quantity) {
        List<Stock> stockList = stockDAO.getStockByItemCodeOrderedByExpiryDate(itemCode);
        int remainingQuantity = quantity;

        try {
            for (Stock stock : stockList) {
                if (remainingQuantity <= 0) break;

                int availableQuantity = stock.getQuantity();
                int reduceQuantity = Math.min(availableQuantity, remainingQuantity);

                if (reduceQuantity > 0) {
                    // Deduct the quantity from stock
                    stock.setQuantity(stock.getQuantity() - reduceQuantity);
                    stockDAO.updateStock(stock);

                    // Check if there is already a shelf item with the same item code and expiry date
                    Shelf existingShelfItem = shelfDAO.getShelfItemByItemCodeAndExpiryDate(itemCode, stock.getExpiryDate().toString());
                    if (existingShelfItem != null) {
                        // Update the existing shelf item quantity
                        existingShelfItem.setQuantity(existingShelfItem.getQuantity() + reduceQuantity);
                        shelfDAO.updateShelfItem(existingShelfItem);
                    } else {
                        // Create a new Shelf item with the reduced quantity
                        Shelf shelf = new Shelf();
                        shelf.setItemCode(stock.getItemCode());
                        shelf.setQuantity(reduceQuantity);
                        shelf.setExpiryDate(stock.getExpiryDate().toString());

                        // Insert shelf item
                        shelfDAO.addShelf(shelf);
                    }

                    remainingQuantity -= reduceQuantity;
                }
            }

            // Notify observers if stock is low
            int totalStock = stockDAO.getTotalStockByItemCode(itemCode);
            System.out.println("Total stock for item code " + itemCode + " is " + totalStock);
            if (totalStock < 5) { // assuming 5 is the threshold for low stock
                lowStockItemCode = itemCode;
                lowStockQuantity = totalStock;
                notifyObservers();
            }

            // Return true if we have moved the exact required quantity to the shelf, otherwise false
            return remainingQuantity == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Stock> getAllStock() {
        return stockDAO.getAllStock();
    }
}