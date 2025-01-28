package dao;

import model.Shelf;
import model.ShelfItem;
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

    public boolean addShelf(Shelf shelfItem) {
        String query = "INSERT INTO Shelf (item_code, item_name, quantity, expiry_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, shelfItem.getItemCode());
            stmt.setString(2, shelfItem.getItemName());
            stmt.setInt(3, shelfItem.getQuantity());
            stmt.setString(4, shelfItem.getExpiryDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
}