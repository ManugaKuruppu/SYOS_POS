package dao;

import model.Store;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDAO {
    private Connection connection;

    public StoreDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addStore(Store store) {
        String query = "INSERT INTO SYOS_Store (item_code, purchase_date, quantity, expiry_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, store.getItemCode());
            stmt.setString(2, store.getPurchaseDate());
            stmt.setInt(3, store.getQuantity());
            stmt.setString(4, store.getExpiryDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Store getStoreById(int id) {
        String query = "SELECT * FROM SYOS_Store WHERE store_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Store store = new Store();
                store.setStoreId(rs.getInt("store_id"));
                store.setItemCode(rs.getString("item_code"));
                store.setPurchaseDate(rs.getString("purchase_date"));
                store.setQuantity(rs.getInt("quantity"));
                store.setExpiryDate(rs.getString("expiry_date"));
                return store;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}