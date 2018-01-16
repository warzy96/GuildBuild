package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.ObrazacEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class ObrazacDAO {

    static Connection connection = null;

    public ObrazacDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
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

    public void deleteForm(String nadimak, int sifraCeha) throws SQLException {
        String querry = "DELETE FROM obrazac WHERE obrazac.nadimak = '" +
                nadimak+ "' AND obrazac.sifCeha = " +
                sifraCeha;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    public void deleteForm(String nadimak) throws SQLException {
        String querry = "DELETE FROM obrazac WHERE obrazac.nadimak = '" +
                nadimak + "'";
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
