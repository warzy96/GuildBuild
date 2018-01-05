package hr.fer.amigosi.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public PodciljEntity getSubgoal(int sifraPodcilja) throws SQLException{
    	String querry = "SELECT * FROM podcilj WHERE podcilj.sifPodcilja =" + sifraPodcilja;
    	
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(querry);
			
			int sifPodcilja = rs.getInt("sifPodcilja");
			String nazivPodcilja = rs.getString("nazivPodcilja");
			int sifCilja = rs.getInt("sifCilja");
			boolean ispunjen = rs.getBoolean("ispunjen");
				
			PodciljEntity podcilj = new PodciljEntity(sifCilja, sifPodcilja, nazivPodcilja, ispunjen);
			
			return podcilj;
		}
		catch(SQLException e) 
		{
			throw e;
		}
    }
	
	public List<PodciljEntity> getAllSubgoalsForGoal(int sifraCilja) throws SQLException{
    	String querry = "SELECT * FROM podcilj WHERE podcilj.sifCilja =" + sifraCilja;
    	List<PodciljEntity> result = new ArrayList<>();
    	
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(querry);
			
			while(rs.next()){
				int sifPodcilja = rs.getInt("sifPodcilja");
				String nazivPodcilja = rs.getString("nazivPodcilja");
				int sifCilja = rs.getInt("sifCilja");
				boolean ispunjen = rs.getBoolean("ispunjen");
					
				PodciljEntity podcilj = new PodciljEntity(sifCilja, sifPodcilja, nazivPodcilja, ispunjen);
				result.add(podcilj);
			}
			return result;
		}
		catch(SQLException e) 
		{
			throw e;
		}
    }
}
