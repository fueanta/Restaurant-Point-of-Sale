package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_ConnectionProvider {

    // database credentials
    private static final String USERNAME = "POS", PASSWORD = "4312";

    // multi thread supported connection
    private static volatile Connection connection;

    // default constructor
    private DB_ConnectionProvider() { /* no instantiating of this class */ }

    // gets a connection to the database system
    public static Connection getConnection() {
        Connection localConnection = connection;
        if (localConnection == null) synchronized (DB_ConnectionProvider.class) {

            // multi thread supported, double checker lock

            localConnection = connection;
            if (localConnection == null) {
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    // oracle driver: oracle.jdbc.driver.OracleDriver
                    // mysql driver: com.mysql.jdbc.Driver
                    connection = localConnection = DriverManager.getConnection(
                            "jdbc:oracle:thin:@localhost:1521:XE", USERNAME, PASSWORD);
                    // mysql url: jdbc:mysql://localhost:3306/world
                    // oracle url: jdbc:oracle:thin:@localhost:1521:XE
                } catch (Exception ex) {
                    System.out.println("Could not connect with the database.");
                }
            }
        }
        return localConnection;
    }

    // turns off the connection
    public static void turnConnectionOff() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Connection couldn't be turned off!");
            }
        }
    }

}