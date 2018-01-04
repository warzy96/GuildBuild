package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.IgraEntity;

public class IgraDAO {
	Connection connection = null;
	public IgraDAO() throws Exception{
		
		connection = DatabaseConnection.getConnection();
	}
	public void insertGame(IgraEntity igraEntity) throws SQLException {
		String querry = "INSERT INTO igra VALUES (" 
						+ igraEntity.getSifraIgre() + ", '"
						+ igraEntity.getNazivIgre() 
						+ "')";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
}
