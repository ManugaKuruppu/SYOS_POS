package util;

import java.sql.Connection;
import java.sql.SQLException;

public class SingletonDatabaseConnectionTest {
    public static void main(String[] args) {
        Connection connection1 = null;
        Connection connection2 = null;

        try {
            // Get the database connection
            connection1 = SingletonDatabaseConnection.getConnection();
            connection2 = SingletonDatabaseConnection.getConnection();

            // Check if both connections are the same instance
            if (connection1 == connection2) {
                System.out.println("Singleton pattern works. Both connections are the same instance.");
            } else {
                System.out.println("Singleton pattern failed. Connections are different instances.");
            }
        } finally {
            // Close the connection
            try {
                if (connection1 != null && !connection1.isClosed()) {
                    SingletonDatabaseConnection.closeConnection();
                    System.out.println("Connection closed successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to close the connection.");
            }
        }
    }
}