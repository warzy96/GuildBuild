package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.CiljEntity;

public class CiljDAO {
	Connection connection = null;
	public CiljDAO() throws Exception{
		connection = DatabaseConnection.getConnection();
	}
	public void insertGoal(CiljEntity ciljEntity) throws SQLException {
		String querry = "INSERT INTO cilj VALUES (" 
						+ ciljEntity.getSifraCilja() + ", '"
						+ ciljEntity.getNazivCilja() + "', "
						+ ciljEntity.getSifraDogadaja() + ", "
						+ ciljEntity.isIspunjenost()
						+ ")";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void deleteGoal(int sifraCilja) throws SQLException {
		String querry = "DELETE FROM cilj WHERE cilj.sifCilja = " +
						sifraCilja; 
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void updateGoal(CiljEntity ciljEntity) throws SQLException {
		String querry = "UPDATE cilj SET " 
				+ "sifCilja = " + ciljEntity.getSifraCilja()
				+ ", nazivCilja = '" + ciljEntity.getNazivCilja() + "'"
				+ ", sifDog =" + ciljEntity.getSifraDogadaja()
				+ ", ispunjen =" + ciljEntity.isIspunjenost() 
				+ " WHERE cilj.sifCilja = " + ciljEntity.getSifraCilja();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
}
