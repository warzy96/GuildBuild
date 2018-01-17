package hr.fer.amigosi.guildbuild.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void updateUserRank(String nickname, String rank) throws SQLException{
        String querry = "UPDATE rang SET rang.rang='" + rank + "'"
                + " WHERE rang.nadimak='" +nickname+"'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void setLeader(String nickname) throws SQLException{
        String query = "UPDATE korisnik SET korisnik.rang = '"
                + RangConstants.leader + "' WHERE korisnik.nadimak = '" + nickname +"'";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            throw e;
        }
    }
}
