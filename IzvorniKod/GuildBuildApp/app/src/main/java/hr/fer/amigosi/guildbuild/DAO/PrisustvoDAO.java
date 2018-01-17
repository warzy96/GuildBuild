package hr.fer.amigosi.guildbuild.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.DogadajEntity;

/**
 * Created by Karlo on 17.1.2018..
 */

public class PrisustvoDAO {
    static Connection connection = null;
    public PrisustvoDAO() throws ClassNotFoundException, SQLException {
        connection = DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }

    public void insertPrisustvo(int sifraDog, String nadimak) throws SQLException{
        String querry= "INSERT INTO prisustvo VALUES("+sifraDog+",'"+nadimak+"')";

        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(querry);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkEventJoining(int sifraDog, String nadimak) throws SQLException{
        String querry = "SELECT * FROM prisustvo WHERE sifDog="+sifraDog+" AND nadimak='"+nadimak+"'";
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(querry);

            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllAttendedEventsForUser(String nadimak){
        String querry = "SELECT dogadaj.nazivDog FROM dogadaj JOIN prisustvo ON dogadaj.sifDog=prisustvo.sifDog" +
                " WHERE prisustvo.nadimak='"+nadimak+"'";
        List<String> result = new ArrayList<>();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(querry);

            while(rs.next()){
                String nazivDog = rs.getString("nazivDog");

                result.add(nazivDog);
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
