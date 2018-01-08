package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.DogadajEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class DogadajDAO {
    Connection connection = null;
    public DogadajDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }
    public void insertEvent(DogadajEntity dogadajEntity) throws SQLException {
        String querry = "INSERT INTO dogadaj VALUES ("
                + dogadajEntity.getSifraDogadaja() + ", '"
                + dogadajEntity.getNazivDogadaja() + "', "
                + dogadajEntity.getSifraCeha() + ", "
                + dogadajEntity.isIspunjenost() +", "
                + dogadajEntity.isVidljivost()
                + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    public void deleteEvent(int sifraDogadaja) throws SQLException {
        String querry = "DELETE FROM dogadaj WHERE dogadaj.sifDog = " +
                sifraDogadaja;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    public void updateEvent(DogadajEntity dogadajEntity) throws SQLException {
        String querry = "UPDATE dogadaj SET "
                + "sifDog = " + dogadajEntity.getSifraDogadaja()
                + ", nazivDog = '" + dogadajEntity.getNazivDogadaja() + "'"
                + ", sifCeh =" + dogadajEntity.getSifraCeha()
                + ", ispunjen =" + dogadajEntity.isIspunjenost()
                + ", vidljiv =" + dogadajEntity.isVidljivost()
                + " WHERE dogadaj.sifDog = " + dogadajEntity.getSifraDogadaja();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<DogadajEntity> getAllEventsForGuild(int sifraCeha) throws SQLException{
        String querry = "SELECT * FROM dogadaj WHERE dogadaj.sifCeh =" + sifraCeha;
        List<DogadajEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                int sifDog = rs.getInt("sifDog");
                String nazivDog = rs.getString("nazivDog");
                int sifCeh = rs.getInt("sifCeh");
                boolean ispunjen = rs.getBoolean("ispunjen");
                boolean vidljiv = rs.getBoolean("vidljiv");

                DogadajEntity dogadaj = new DogadajEntity(sifDog, nazivDog, sifCeh, ispunjen, vidljiv);
                result.add(dogadaj);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    public List<DogadajEntity> getVisibleEventsForGuild(int sifraCeha) throws SQLException{
        String querry = "SELECT * FROM dogadaj WHERE dogadaj.sifCeh =" + sifraCeha
                +" AND vidljiv";
        List<DogadajEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                int sifDog = rs.getInt("sifDog");
                String nazivDog = rs.getString("nazivDog");
                int sifCeh = rs.getInt("sifCeh");
                boolean ispunjen = rs.getBoolean("ispunjen");
                boolean vidljiv = rs.getBoolean("vidljiv");

                DogadajEntity dogadaj = new DogadajEntity(sifDog, nazivDog, sifCeh, ispunjen, vidljiv);
                result.add(dogadaj);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    public List<DogadajEntity> getFinishedEventsForGuild(int sifraCeha) throws SQLException{
        String querry = "SELECT * FROM dogadaj WHERE dogadaj.sifCeh =" + sifraCeha
                +" AND ispunjen";
        List<DogadajEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                int sifDog = rs.getInt("sifDog");
                String nazivDog = rs.getString("nazivDog");
                int sifCeh = rs.getInt("sifCeh");
                boolean ispunjen = rs.getBoolean("ispunjen");
                boolean vidljiv = rs.getBoolean("vidljiv");

                DogadajEntity dogadaj = new DogadajEntity(sifDog, nazivDog, sifCeh, ispunjen, vidljiv);
                result.add(dogadaj);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }
}
