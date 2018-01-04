package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.KlasaEntity;

public class KlasaDAO {

	Connection connection = null;
	
	public KlasaDAO() throws Exception{
		connection=DatabaseConnection.getConnection();
	}
	
	public void insertClass(KlasaEntity klasaEntity) throws SQLException{
		String querry = "INSERT INTO klasa VALUES (" 
				+ klasaEntity.getSifraKlase() + ", '"
				+ klasaEntity.getNazivKlase() + "', "
				+ klasaEntity.getSifraIgre()
				+ ")";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public void deleteClass(int sifraKlase) throws SQLException {
		String querry = "DELETE FROM klasa WHERE klasa.sifKlase = " +
						sifraKlase; 
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public void updateClass(KlasaEntity klasaEntity) throws SQLException{
		String querry = "UPDATE klasa SET " 
				+ "sifKlase = " + klasaEntity.getSifraIgre()
				+ ", naziv = '" + klasaEntity.getNazivKlase() + "'"
				+ ", sifIgre =" + klasaEntity.getSifraIgre()
				+ " WHERE klasa.sifKlase = " + klasaEntity.getSifraKlase();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
}
