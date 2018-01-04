package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.ObrazacEntity;

public class ObrazacDAO {

	Connection connection = null;
	
	public ObrazacDAO() throws Exception{
		connection = DatabaseConnection.getConnection();
	}
	
	public void insertForm(ObrazacEntity obrazacEntity) throws SQLException{
		String querry = "INSERT INTO obrazac VALUES (" 
				+ obrazacEntity.getSifraCeha()+ ", '"
				+ obrazacEntity.getNadimakKorisnika() + "', '"
				+ obrazacEntity.getPoruka()
				+ "')";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public void deleteForm(ObrazacEntity obrazacEntity) throws SQLException {
		String querry = "DELETE FROM obrazac WHERE obrazac.nadimak = '" +
						obrazacEntity.getNadimakKorisnika() + "' AND obrazac.sifCeha = " +
						obrazacEntity.getSifraCeha();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public void updateForm(ObrazacEntity obrazacEntity) throws SQLException{
		String querry = "UPDATE obrazac SET " 
				+ "nadimak = '" + obrazacEntity.getNadimakKorisnika()+ "'"
				+ ", sifCeha = " + obrazacEntity.getSifraCeha()
				+ ", poruka = '" + obrazacEntity.getPoruka() + "'"
				+ " WHERE obrazac.nadimak = '" + obrazacEntity.getNadimakKorisnika() + "'"
				+ " AND obrazac.sifCeha = " + obrazacEntity.getSifraCeha();
				
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
}
