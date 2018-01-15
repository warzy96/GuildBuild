package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.PorukaEntity;

/**
 * Created by kerma on 13.1.2018..
 */

public class PorukaDAO {

    static Connection connection = null;

    public PorukaDAO() throws Exception {
        connection = DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }
    public void insertMessage(PorukaEntity porukaEntity) throws SQLException {
        String querry = "INSERT INTO poruka VALUES ('"
                + porukaEntity.getPrimatelj() + "', '"
                + porukaEntity.getPosiljatelj() + "', '"
                + porukaEntity.getTekst() + "', '"
                + porukaEntity.getNaslov() + "', "
                + porukaEntity.getIdPoruke()
                + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        } catch (SQLException e) {
            throw e;
        }
    }

    public void deleteMessage(int idPoruke) throws SQLException {
        String querry = "DELETE FROM poruka WHERE poruka.id = " +
                idPoruke;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void updateMessage(PorukaEntity porukaEntity) throws SQLException {
        String querry = "UPDATE poruka SET "
                + "primatelj = '" + porukaEntity.getPrimatelj() + "'"
                + ", posiljatelj = '" + porukaEntity.getPosiljatelj() + "'"
                + ", tekst ='" + porukaEntity.getTekst() +"'"
                + ", naslov = '" + porukaEntity.getNaslov() + "'"
                + ", id =" + porukaEntity.getIdPoruke()
                + " WHERE poruka.id = " + porukaEntity.getIdPoruke();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<PorukaEntity> getAllReceivedMessages(String primateljPoruke) throws SQLException{
        String querry = "SELECT * FROM poruka WHERE poruka.primatelj = '" + primateljPoruke +"'";
        List<PorukaEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                String primatelj = rs.getString("primatelj");
                String posiljatelj = rs.getString("posiljatelj");
                String tekst = rs.getString("tekst");
                String naslov = rs.getString("naslov");
                int idPoruke = rs.getInt("id");

                PorukaEntity poruka = new PorukaEntity(primatelj, posiljatelj, tekst, naslov, idPoruke);
                result.add(poruka);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    public List<PorukaEntity> getAllSentMessages(String posiljateljPoruke) throws SQLException{
        String querry = "SELECT * FROM poruka WHERE poruka.posiljatelj ='" + posiljateljPoruke +"'";
        List<PorukaEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                String primatelj = rs.getString("primatelj");
                String posiljatelj = rs.getString("posiljatelj");
                String tekst = rs.getString("tekst");
                String naslov = rs.getString("naslov");
                int idPoruke = rs.getInt("id");

                PorukaEntity poruka = new PorukaEntity(primatelj, posiljatelj, tekst, naslov, idPoruke);
                result.add(poruka);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }
}
