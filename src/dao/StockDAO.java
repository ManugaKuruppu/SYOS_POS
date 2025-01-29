package dao;

import model.Stock;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    private Connection connection;

    public StockDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addStock(Stock stock) {
        String query = "INSERT INTO Stock (item_code, purchase_date, quantity, expiry_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, stock.getItemCode());
            stmt.setDate(2, stock.getPurchaseDate());
            stmt.setInt(3, stock.getQuantity());
            stmt.setDate(4, stock.getExpiryDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean itemCodeExists(String itemCode) {
        String query = "SELECT COUNT(*) FROM Items WHERE item_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Stock getStockById(int id) {
        String query = "SELECT * FROM Stock WHERE stock_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Stock stock = new Stock();
                stock.setStockId(rs.getInt("stock_id"));
                stock.setItemCode(rs.getString("item_code"));
                stock.setPurchaseDate(rs.getDate("purchase_date"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setExpiryDate(rs.getDate("expiry_date"));
                return stock;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Stock> getStockByItemCodeOrderedByExpiryDate(String itemCode) {
        String query = "SELECT * FROM Stock WHERE item_code = ? ORDER BY expiry_date ASC";
        List<Stock> stockList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setStockId(rs.getInt("stock_id"));
                stock.setItemCode(rs.getString("item_code"));
                stock.setPurchaseDate(rs.getDate("purchase_date"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setExpiryDate(rs.getDate("expiry_date"));
                stockList.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockList;
    }

    // Define the getTotalStockByItemCode method
    public int getTotalStockByItemCode(String itemCode) {
        String query = "SELECT SUM(quantity) AS total_quantity FROM Stock WHERE item_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateStock(Stock stock) {
        String query = "UPDATE Stock SET quantity = ? WHERE stock_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, stock.getQuantity());
            stmt.setInt(2, stock.getStockId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Stock> getAllStock() {
        String query = "SELECT * FROM Stock";
        List<Stock> stockList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setStockId(rs.getInt("stock_id"));
                stock.setItemCode(rs.getString("item_code"));
                stock.setPurchaseDate(rs.getDate("purchase_date"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setExpiryDate(rs.getDate("expiry_date"));
                stockList.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockList;
    }
}