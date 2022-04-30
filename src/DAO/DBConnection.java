package DAO;

import javax.sound.midi.SysexMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/addressbook";
    private final static String USERNAME = "root";
    private final static String PASSWORD = ""; //enter database password
    private static Connection connection;
    private static DBConnection instance;

    private DBConnection(Connection connection) {
        DBConnection.connection = connection;
    }

    public static Connection getConnection() {
        if (instance == null) {
            try {
                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                instance = new DBConnection(connection);
                System.out.println("Connection successful.\r\n");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("\r\n*** Update database password in DBConnection.java ***");
                System.exit(-1);
            }
        }

        return DBConnection.connection;
    }
}
