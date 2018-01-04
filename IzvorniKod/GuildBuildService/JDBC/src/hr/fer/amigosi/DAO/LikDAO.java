package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.LikEntity;

public class LikDAO {

	Connection connection = null;
	
	public LikDAO() throws Exception{
		connection=DatabaseConnection.getConnection();
	}
	
	public void insertCharacter(LikEntity likEntity) throws SQLException{
		String querry = "INSERT INTO lik VALUES ('" 
				+ likEntity.getNadimak() + "', "
				+ likEntity.getLevel() + ", "
				+ likEntity.getSifraKlase() +",'"
				+ likEntity.getCraftingSkills()
				+ "')";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public void deleteCharacter(LikEntity likEntity) throws SQLException {
		String querry = "DELETE FROM lik WHERE lik.sifKlase = " +
						likEntity.getSifraKlase() +
						"AND lik.nadimak = '" +likEntity.getNadimak() + "'";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	
	public void updateCharacter(LikEntity likEntity) throws SQLException{
		String querry = "UPDATE lik SET " 
				+ "nadimak = '" + likEntity.getNadimak()+ "'"
				+ ", level = " + likEntity.getLevel()
				+ ", sifKlase =" + likEntity.getSifraKlase()
				+ ", craftingSkills ='" + likEntity.getCraftingSkills() + "'"
				+ " WHERE lik.nadimak = '" + likEntity.getNadimak() + "'"
				+ " AND lik.sifKlase = " + likEntity.getSifraKlase();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
}
