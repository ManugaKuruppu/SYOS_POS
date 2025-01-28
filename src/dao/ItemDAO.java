package dao;

import model.Item;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAO {
    private Connection connection;

    public ItemDAO() {
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

    public Item getItemByCode(String code) {
        String query = "SELECT * FROM Items WHERE item_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setPrice(rs.getDouble("price"));
                item.setDiscount(rs.getDouble("discount"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}