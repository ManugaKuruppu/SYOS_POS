package dao;

import model.BillItem;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillItemDAO {
    private Connection connection;

    public BillItemDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addBillItem(BillItem billItem) {
        String query = "INSERT INTO BillItems (bill_id, item_code, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, billItem.getBillId());
            stmt.setString(2, billItem.getItemCode());
            stmt.setInt(3, billItem.getQuantity());
            stmt.setDouble(4, billItem.getTotalPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}