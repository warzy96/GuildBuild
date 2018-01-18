package hr.fer.amigosi.guildbuild.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.RangConstants;

/**
 * Created by Ivan on 17/01/2018.
 */

public class RangDAO {
    static Connection connection = null;

    public RangDAO() throws Exception{
        connection= DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }

    public void insertUserRank(String nickname, String rank, String sifraCeha) throws SQLException{
        String querry = "INSERT INTO rang (rang, nadimak, sifceh) VALUES ('" + rank + "'"
                + ",'" +nickname+"',"
                + Integer.parseInt(sifraCeha) + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void updateUserRank(String nickname, String rank, String sifraCeha) throws SQLException{
        String querry = "UPDATE rang SET rang.rang='" + rank + "'"
                + " WHERE rang.nadimak='" +nickname+"' AND rang.sifCeh ="
                + Integer.parseInt(sifraCeha);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void setLeader(String nickname, String sifraCeha) throws SQLException{
        String query = "UPDATE rang SET rang.rang = '"
                + RangConstants.leader + "' WHERE rang.nadimak = '" + nickname
                +"' AND rang.sifCeh = " + Integer.parseInt(sifraCeha);

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            throw e;
        }
    }

    public String getUserRang(String nickname, String sifraCeha) throws SQLException{
        String query = "SELECT * FROM rang WHERE rang.rang = '"
                + RangConstants.leader + "' AND rang.nadimak = '" + nickname
                +"' AND rang.sifCeh = " + Integer.parseInt(sifraCeha);

        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                return rs.getString("rang");
            }
        }catch(SQLException e){
            throw e;
        }
        return "";
    }

    //constraint in table should take care of this
    public String deleteUser(String nickname) throws SQLException{
        String query = "DELETE * FROM rang WHERE rang.nadimak = '"
                + nickname + "'";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            throw e;
        }
        return "";
    }

    public List<Integer> isUserLeader(String nadimak) throws SQLException{
        List<Integer> sifreCehovaGdjeJeVoda = new ArrayList<>();
        String query = "SELECT * FROM rang WHERE rang.nadimak = '" + nadimak +
                "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                Integer sifraCeha = rs.getInt("sifCeh");
                String rang = rs.getString("rang");
                if(rang.equals(RangConstants.leader)) {
                    sifreCehovaGdjeJeVoda.add(sifraCeha);
                }
            }
        }
        catch (SQLException e) {
            throw e;
        }
        return sifreCehovaGdjeJeVoda;
    }

    public void removeRow(String nadimak, String s)  throws SQLException{
        String query = "DELETE FROM rang WHERE rang.nadimak = '" + nadimak
                + "' AND rang.sifCeh = " + Integer.parseInt(s);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            throw e;
        }
    }
}
