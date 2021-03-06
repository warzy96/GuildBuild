package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.KlasaEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class KlasaDAO {
    static Connection connection = null;

    public KlasaDAO() throws Exception{
        connection= DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }
    public void insertClass(KlasaEntity klasaEntity) throws SQLException{
        String querry = "INSERT INTO klasa VALUES ("
                + klasaEntity.getSifraKlase() + ", '"
                + klasaEntity.getNazivKlase() + "', "
                + klasaEntity.getSifraIgre()
                + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void deleteClass(int sifraKlase) throws SQLException {
        String querry = "DELETE FROM klasa WHERE klasa.sifKlase = " +
                sifraKlase;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void updateClass(KlasaEntity klasaEntity) throws SQLException{
        String querry = "UPDATE klasa SET "
                + "sifKlase = " + klasaEntity.getSifraIgre()
                + ", naziv = '" + klasaEntity.getNazivKlase() + "'"
                + ", sifIgre =" + klasaEntity.getSifraIgre()
                + " WHERE klasa.sifKlase = " + klasaEntity.getSifraKlase();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<KlasaEntity> getAllClassesForGame(int sifraIgre) throws SQLException{
        String querry = "SELECT * FROM klasa WHERE klasa.sifIgre =" + sifraIgre;
        List<KlasaEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                int sifKlase = rs.getInt("sifKlase");
                String naziv = rs.getString("naziv");
                int sifIgre = rs.getInt("sifIgre");

                KlasaEntity klasa = new KlasaEntity(sifIgre, sifKlase, naziv);
                result.add(klasa);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    public int getSifraKlase(String className, int sifraIgre) throws SQLException{
        String querry = "SELECT * FROM klasa WHERE naziv = '" +
                className + "' AND sifIgre = " + sifraIgre;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querry);
            if(resultSet.next()) {
                return resultSet.getInt("sifKlase");
            }
        }
        catch(SQLException e) {
            throw e;
        }
        return 0;
    }
}
