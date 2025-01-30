package dao;

import model.BillItem;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<BillItem> getBillItemsByBillId(int billId) {
        String query = "SELECT * FROM BillItems WHERE bill_id = ?";
        List<BillItem> billItems = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BillItem billItem = new BillItem();
                billItem.setBillId(rs.getInt("bill_id"));
                billItem.setItemCode(rs.getString("item_code"));
//                billItem.setItemName(rs.getString("item_name"));
                billItem.setQuantity(rs.getInt("quantity"));
//                billItem.setPrice(rs.getDouble("price"));
//                billItem.setDiscount(rs.getDouble("discount"));
                billItem.setTotalPrice(rs.getDouble("total_price"));
                billItems.add(billItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billItems;
    }
}