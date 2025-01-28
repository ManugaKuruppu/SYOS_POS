package service;

import dao.StockDAO;
import dao.ShelfDAO;
import model.Stock;
import model.Shelf;

import java.util.List;

public class StockService {
    private StockDAO stockDAO;
    private ShelfDAO shelfDAO;

    public StockService() {
        stockDAO = new StockDAO();
        shelfDAO = new ShelfDAO();
    }

    public boolean addStock(Stock stock) {
        String itemCode = stock.getItemCode().trim();
        if (!stockDAO.itemCodeExists(itemCode)) {
            System.out.println("Item code does not exist: " + itemCode);
            return false;
        }
        return stockDAO.addStock(stock);
    }

    public Stock getStockById(int id) {
        return stockDAO.getStockById(id);
    }

    public boolean moveStockToShelf(int stockId, int quantity) {
        Stock stock = stockDAO.getStockById(stockId);
        if (stock == null) {
            System.out.println("Stock not found with ID: " + stockId);
            return false;
        }
        if (stock.getQuantity() < quantity) {
            System.out.println("Not enough stock to move. Available quantity: " + stock.getQuantity());
            return false;
        }
        // Deduct the quantity from stock and add it to the shelf
        stock.setQuantity(stock.getQuantity() - quantity);
        stockDAO.updateStock(stock);

        Shelf shelf = new Shelf();
        shelf.setItemCode(stock.getItemCode());
        shelf.setQuantity(quantity);
        shelf.setExpiryDate(stock.getExpiryDate().toString());

        return shelfDAO.addShelf(shelf);
    }

    public List<Stock> getAllStock() {
        return stockDAO.getAllStock();
    }


}