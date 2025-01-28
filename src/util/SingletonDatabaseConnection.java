package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonDatabaseConnection {
    private static Connection connection;

    private SingletonDatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Read database properties from a configuration file
                String url = Constants.DB_URL;
                String user = Constants.DB_USER;
                String password = Constants.DB_PASSWORD;
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("MySQL JDBC Driver not found.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Connection to the database failed.");
            }
        }
        return connection;
    }
}