package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.CehEntity;

public class CehDAO {
	Connection connection = null;
	public CehDAO() throws Exception{
		connection = DatabaseConnection.getConnection();
	}
	public void insertGuild(CehEntity cehEntity) throws SQLException {
		String querry = "INSERT INTO ceh VALUES (" 
						+ cehEntity.getSifraCeha() + ", '"
						+ cehEntity.getNaziv() + "', "
						+ cehEntity.getSifraIgre() + ", '"
						+ cehEntity.getOpis()
						+ "')";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void deleteGuild(int sifraCeha) throws SQLException {
		String querry = "DELETE FROM ceh WHERE ceh.sifCeh = " +
						sifraCeha; 
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void updateGuild(CehEntity cehEntity) throws SQLException {
		String querry = "UPDATE ceh SET " 
				+ "sifCeh = " + cehEntity.getSifraCeha()
				+ ", naziv = '" + cehEntity.getNaziv() + "'"
				+ ", sifIgre =" + cehEntity.getSifraIgre()
				+ ", opis ='" + cehEntity.getOpis() + "'"
				+ " WHERE ceh.sifCeh = " + cehEntity.getSifraCeha();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
}
