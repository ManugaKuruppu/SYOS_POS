package dao;

import model.Bill;
import model.BillItem;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private Connection connection;

    public BillDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addBill(Bill bill) {
        String query = "INSERT INTO Bills (bill_date, total, final_total, cash_tendered, change_amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, bill.getBillDate());
            stmt.setDouble(2, bill.getTotal());
            stmt.setDouble(3, bill.getFinalTotal());
            stmt.setDouble(4, bill.getCashTendered());
            stmt.setDouble(5, bill.getChangeAmount());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    bill.setBillId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Bills";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotal(rs.getDouble("total"));
                bill.setFinalTotal(rs.getDouble("final_total"));
                bill.setCashTendered(rs.getDouble("cash_tendered"));
                bill.setChangeAmount(rs.getDouble("change_amount"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}