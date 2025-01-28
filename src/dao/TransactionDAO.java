package dao;

import model.Transaction;
import util.SingletonDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO() {
        connection = SingletonDatabaseConnection.getConnection();
    }

    public boolean addTransaction(Transaction transaction) {
        String query = "INSERT INTO Transactions (bill_id, transaction_date) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, transaction.getBillId());
            stmt.setTimestamp(2, transaction.getTransactionDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Transaction getTransactionById(int id) {
        String query = "SELECT * FROM Transactions WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setBillId(rs.getInt("bill_id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}