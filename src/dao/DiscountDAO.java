package dao;

import model.Discount;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountDAO {
    private Connection connection;

    public DiscountDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addDiscount(Discount discount) {
        String query = "INSERT INTO Discounts (item_code, category_id, discount_type, discount_value) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, discount.getItemCode());
            stmt.setInt(2, discount.getCategoryId());
            stmt.setString(3, discount.getDiscountType());
            stmt.setDouble(4, discount.getDiscountValue());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Discount getDiscountById(int id) {
        String query = "SELECT * FROM Discounts WHERE discount_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Discount discount = new Discount();
                discount.setDiscountId(rs.getInt("discount_id"));
                discount.setItemCode(rs.getString("item_code"));
                discount.setCategoryId(rs.getInt("category_id"));
                discount.setDiscountType(rs.getString("discount_type"));
                discount.setDiscountValue(rs.getDouble("discount_value"));
                return discount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}