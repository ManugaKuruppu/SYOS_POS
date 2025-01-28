package dao;

import model.Item;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemsDAO {
    private Connection connection;

    public ItemsDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addItem(Item item) {
        String query = "INSERT INTO Items (item_code, item_name, category_id, price, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getItemCode());
            stmt.setString(2, item.getItemName());
            stmt.setInt(3, item.getCategoryId());
            stmt.setDouble(4, item.getPrice());
            stmt.setDouble(5, item.getDiscount());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean itemCodeExists(String itemCode) {
        String query = "SELECT 1 FROM Items WHERE item_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemCode);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}