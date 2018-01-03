package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.DogadajEntity;

public class DogadajDAO {
	Connection connection = null;
	public DogadajDAO() throws Exception{
		connection = DatabaseConnection.getConnection();
	}
	public void insertEvent(DogadajEntity dogadajEntity) throws SQLException {
		String querry = "INSERT INTO dogadaj VALUES (" 
						+ dogadajEntity.getSifraDogadaja() + ", '"
						+ dogadajEntity.getNazivDogadaja() + "', "
						+ dogadajEntity.getSifraCeha() + ", "
						+ dogadajEntity.isIspunjenost() +", "
						+ dogadajEntity.isVidljivost()
						+ ")";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void deleteEvent(int sifraDogadaja) throws SQLException {
		String querry = "DELETE FROM dogadaj WHERE dogadaj.sifDog = " +
						sifraDogadaja; 
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
	public void updateEvent(DogadajEntity dogadajEntity) throws SQLException {
		String querry = "UPDATE dogadaj SET " 
				+ "sifDog = " + dogadajEntity.getSifraDogadaja()
				+ ", nazivDog = '" + dogadajEntity.getNazivDogadaja() + "'"
				+ ", sifCeh =" + dogadajEntity.getSifraCeha()
				+ ", ispunjen =" + dogadajEntity.isIspunjenost() 
				+ ", vidljiv =" + dogadajEntity.isVidljivost()
				+ " WHERE dogadaj.sifDog = " + dogadajEntity.getSifraDogadaja();
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(querry);
		}
		catch(SQLException e) {
			throw e;
		}
	}
}
