package hr.fer.amigosi.guildbuild;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Karlo on 7.1.2018..
 */

public class DatabaseConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11214344?useSSL=false", "sql11214344", "lZZVM8IVi7");
        return connect;
    }
}
