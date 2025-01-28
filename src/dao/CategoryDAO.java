package dao;

import model.Category;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {
    private Connection connection;

    public CategoryDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addCategory(Category category) {
        String query = "INSERT INTO Categories (category_name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getCategoryName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Category getCategoryById(int id) {
        String query = "SELECT * FROM Categories WHERE category_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}