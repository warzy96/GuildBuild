package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.IgraEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class IgraDAO {
    Connection connection = null;
    public IgraDAO() throws Exception{

        connection = DatabaseConnection.getConnection();
    }
    public void close() throws SQLException {
        connection.close();
    }
    public void insertGame(IgraEntity igraEntity) throws SQLException {
        String querry = "INSERT INTO igra VALUES ("
                + igraEntity.getSifraIgre() + ", '"
                + igraEntity.getNazivIgre()
                + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<IgraEntity> getAllGames() throws SQLException{
        String querry = "SELECT * FROM igra";
        List<IgraEntity> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                int sifIgre = rs.getInt("sifIgre");
                String naziv = rs.getString("naziv");

                IgraEntity igra = new IgraEntity(sifIgre, naziv);
                result.add(igra);
            }
            return result;

        }
        catch(SQLException e) {
            throw e;
        }
    }
}
