package hr.fer.amigosi;

import java.sql.*;

public class DatabaseConnection {
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = null;
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/guild_build?useSSL=false", "karlo", "opp1234");
		return connect;
	}
}
