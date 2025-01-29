package dao;

import model.Shelf;
import model.ShelfItem;
import model.Stock;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShelfDAO {
    private Connection connection;

    public ShelfDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    // Modified addShelf method to accept a Shelf object
    public boolean addShelf(Shelf shelf) {
        String query = "INSERT INTO Shelf (item_code, item_name, quantity, expiry_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, shelf.getItemCode());
            stmt.setString(2, getItemNameByItemCode(shelf.getItemCode()));
            stmt.setInt(3, shelf.getQuantity());
            stmt.setString(4, shelf.getExpiryDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing shelf item
    public boolean updateShelfItem(Shelf shelf) {
        String query = "UPDATE Shelf SET quantity = ? WHERE item_code = ? AND expiry_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, shelf.getQuantity());
            stmt.setString(2, shelf.getItemCode());
            stmt.setString(3, shelf.getExpiryDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a shelf item by item code and expiry date
    public Shelf getShelfItemByItemCodeAndExpiryDate(String itemCode, String expiryDate) {
        String query = "SELECT * FROM Shelf WHERE item_code = ? AND expiry_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemCode);
            stmt.setString(2, expiryDate);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Shelf shelf = new Shelf();
                shelf.setItemCode(rs.getString("item_code"));
                shelf.setItemName(rs.getString("item_name"));
                shelf.setQuantity(rs.getInt("quantity"));
                shelf.setExpiryDate(rs.getString("expiry_date"));
                return shelf;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to reduce shelf quantity
    public boolean reduceShelfQuantity(String itemCode, int quantity) {
        String query = "UPDATE Shelf SET quantity = quantity - ? WHERE item_code = ? AND quantity >= ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, itemCode);
            stmt.setInt(3, quantity);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch item name by item code from the Items table
    private String getItemNameByItemCode(String itemCode) throws SQLException {
        String query = "SELECT item_name FROM Items WHERE item_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("item_name");
                }
            }
        }
        return null;
    }

    // Existing method to get all shelf items
    public List<ShelfItem> getAllShelfItems() {
        String query = "SELECT s.item_code, i.item_name AS item_name, s.quantity, s.expiry_date " +
                "FROM Shelf s " +
                "JOIN Items i ON s.item_code = i.item_code";
        List<ShelfItem> shelfItems = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String itemCode = rs.getString("item_code");
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                String expiryDate = rs.getString("expiry_date");
                shelfItems.add(new ShelfItem(itemCode, itemName, quantity, expiryDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shelfItems;
    }

    // Private method to get stock items by item code ordered by expiry date
    private List<Stock> getStockByItemCodeOrderedByExpiryDate(String itemCode) throws SQLException {
        List<Stock> stockList = new ArrayList<>();

        String query = "SELECT * FROM Stock WHERE item_code = ? ORDER BY expiry_date ASC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemCode);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setStockId(rs.getInt("stock_id"));
                    stock.setItemCode(rs.getString("item_code"));
                    stock.setExpiryDate(rs.getDate("expiry_date"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stockList.add(stock);
                }
            }
        }

        return stockList;
    }

    // Private method to update stock quantity
    private void updateStockQuantity(int stockId, int reduceQuantity) throws SQLException {
        String query = "UPDATE Stock SET quantity = quantity - ? WHERE stock_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reduceQuantity);
            stmt.setInt(2, stockId);
            stmt.executeUpdate();
        }
    }

    // Private method to insert shelf item
    private boolean insertShelfItem(Shelf shelfItem) throws SQLException {
        String query = "INSERT INTO Shelf (item_code, item_name, quantity, expiry_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, shelfItem.getItemCode());
            stmt.setString(2, shelfItem.getItemName());
            stmt.setInt(3, shelfItem.getQuantity());
            stmt.setString(4, shelfItem.getExpiryDate());
            return stmt.executeUpdate() > 0;
        }
    }
}