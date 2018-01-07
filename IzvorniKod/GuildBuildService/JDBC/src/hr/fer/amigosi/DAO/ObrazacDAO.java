package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	
	public List<ObrazacEntity> getAllFormsForGuild(int sifraCeha) throws SQLException{
    	String querry = "SELECT * FROM obrazac WHERE obrazac.sifCeha =" + sifraCeha;
    	List<ObrazacEntity> result = new ArrayList<>();
    	
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(querry);
			
			while(rs.next()){
				int sifCeha = rs.getInt("sifCeha");
				String nadimak = rs.getString("nadimak");
				String poruka = rs.getString("poruka");
				
				ObrazacEntity obrazac = new ObrazacEntity(sifCeha, nadimak, poruka);
				result.add(obrazac);
			}
			return result;
		}
		catch(SQLException e) 
		{
			throw e;
		}
    }
}