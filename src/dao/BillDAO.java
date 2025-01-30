package dao;

import model.Bill;
import model.BillItem;
import model.Item;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public List<Bill> getBillsByDate(Timestamp date) {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Bills WHERE DATE(bill_date) = DATE(?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, date);
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

    public List<BillItem> getBillItemsByDate(Timestamp date) {
        List<BillItem> billItems = new ArrayList<>();
        String query = "SELECT bi.* FROM BillItems bi JOIN Bills b ON bi.bill_id = b.bill_id WHERE DATE(b.bill_date) = DATE(?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BillItem billItem = new BillItem();
                billItem.setBillId(rs.getInt("bill_id"));
                billItem.setItemCode(rs.getString("item_code"));
                billItem.setItemName(rs.getString("item_name"));
                billItem.setQuantity(rs.getInt("quantity"));
                billItem.setPrice(rs.getDouble("price"));
                billItem.setDiscount(rs.getDouble("discount"));
                billItem.setTotalPrice(rs.getDouble("total_price"));
                billItems.add(billItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billItems;
    }

    public List<Item> getItemsToReshelve() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Items WHERE quantity < 50";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setDiscount(rs.getDouble("discount"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> getReorderItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Items WHERE quantity < 50";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setDiscount(rs.getDouble("discount"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Item> getCurrentStock() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Items";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setDiscount(rs.getDouble("discount"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}