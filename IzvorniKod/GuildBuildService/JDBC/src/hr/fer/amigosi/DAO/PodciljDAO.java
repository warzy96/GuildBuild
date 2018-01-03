package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.PodciljEntity;

public class PodciljDAO {
	Connection connection = null;
	public PodciljDAO() throws Exception{
		connection = DatabaseConnection.getConnection();
	}
	public void insertSubgoal(PodciljEntity podciljEntity) throws SQLException {
		String querry = "INSERT INTO podcilj VALUES (" 
						+ podciljEntity.getSifraPodcilja() + ", '"
						+ podciljEntity.getNazivPodcilja() + "', "
						+ podciljEntity.getSifraCilja() + ", "
						+ podciljEntity.isIspunjenost()
						+ ")";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void deleteSubgoal(int sifraPodcilja) throws SQLException {
		String querry = "DELETE FROM podcilj WHERE podcilj.sifPodcilja = " +
						sifraPodcilja; 
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void updateSubgoal(PodciljEntity podciljEntity) throws SQLException {
		String querry = "UPDATE podcilj SET " 
				+ "sifPodcilja = " + podciljEntity.getSifraPodcilja()
				+ ", nazivPodcilja = '" + podciljEntity.getNazivPodcilja() + "'"
				+ ", sifCilja =" + podciljEntity.getSifraCilja()
				+ ", ispunjen =" + podciljEntity.isIspunjenost() 
				+ " WHERE podcilj.sifPodcilja = " + podciljEntity.getSifraPodcilja();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
}
