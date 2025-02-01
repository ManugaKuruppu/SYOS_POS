package dao;

import model.Item;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection connection;

    // Constructor initializes the database connection using the singleton pattern
    public ItemDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    // Method to add a new item to the database
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

    // Method to retrieve an item by its code
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
                return item; // Return the retrieved item
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to fetch all items from the database
    public List<Item> getAllItems() {
        String query = "SELECT * FROM Items";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setPrice(rs.getDouble("price"));
                item.setDiscount(rs.getDouble("discount"));
                items.add(item); // Add each item to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Method to fetch items that need to be reshelved (quantity < 50)
    public List<Item> getItemsToReshelve() {
        String query = "SELECT * FROM Items WHERE quantity < 50";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setPrice(rs.getDouble("price"));
                item.setDiscount(rs.getDouble("discount"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Method to fetch items that need to be reordered (quantity < 50)
    public List<Item> getReorderItems() {
        String query = "SELECT * FROM Items WHERE quantity < 50";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setPrice(rs.getDouble("price"));
                item.setDiscount(rs.getDouble("discount"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item); // Add each item to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Method to fetch the current stock of all items
    public List<Item> getCurrentStock() {
        String query = "SELECT * FROM Items";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setPrice(rs.getDouble("price"));
                item.setDiscount(rs.getDouble("discount"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item); // Add each item to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}