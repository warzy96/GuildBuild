package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
    public List<CiljEntity> getAllGoalsForEvent(int sifraDogadaja) throws SQLException{
    	String querry = "SELECT * FROM cilj WHERE cilj.sifDog =" + sifraDogadaja;
    	List<CiljEntity> result = new ArrayList<>();
    	
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(querry);
			
			while(rs.next()){
				int sifCilja = rs.getInt("sifCilja");
				String nazivCilja = rs.getString("nazivCilja");
				int sifDog = rs.getInt("sifDog");
				boolean ispunjen = rs.getBoolean("ispunjen");
				
				CiljEntity cilj = new CiljEntity(sifDog, sifCilja, nazivCilja, ispunjen);
				result.add(cilj);
			}
			return result;
		}
		catch(SQLException e) 
		{
			throw e;
		}
    }
    
    public CiljEntity getGoal(int sifraCilja) throws SQLException{
    	String querry = "SELECT * FROM cilj WHERE cilj.sifCilja =" + sifraCilja;
    	
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(querry);
			
			int sifCilja = rs.getInt("sifCilja");
			String nazivCilja = rs.getString("nazivCilja");
			int sifDog = rs.getInt("sifDog");
			boolean ispunjen = rs.getBoolean("ispunjen");
				
			CiljEntity cilj = new CiljEntity(sifDog, sifCilja, nazivCilja, ispunjen);
				
			return cilj;
		}
		catch(SQLException e) 
		{
			throw e;
		}
    }

}
