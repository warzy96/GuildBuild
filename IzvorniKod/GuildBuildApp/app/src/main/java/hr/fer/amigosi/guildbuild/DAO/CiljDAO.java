package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.CiljEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class CiljDAO {
    static Connection connection = null;
    public CiljDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }
    public void insertGoal(CiljEntity ciljEntity) throws SQLException {
        String querry = "INSERT INTO cilj VALUES (null , '"
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

    public CiljEntity getGoal(String nazivCiljaa) throws SQLException{
        String querry = "SELECT * FROM cilj WHERE cilj.nazivCilja ='" + nazivCiljaa+"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            if(rs.next()) {

                int sifCilja = rs.getInt("sifCilja");
                String nazivCilja = rs.getString("nazivCilja");
                int sifDog = rs.getInt("sifDog");
                boolean ispunjen = rs.getBoolean("ispunjen");

                CiljEntity cilj = new CiljEntity(sifDog, sifCilja, nazivCilja, ispunjen);

                return cilj;
            }
            return null;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    public List<String> getAllGoalsForGuild(int sifraCeha) throws SQLException{
        String querry = "SELECT cilj.nazivCilja FROM cilj,dogadaj WHERE cilj.sifDog = dogadaj.sifDog AND" +
                " dogadaj.sifCeh = " + sifraCeha;
        List<String> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()) {
                //int sifCilja = rs.getInt("sifCilja");
                String nazivCilja = rs.getString("nazivCilja");
                //int sifDog = rs.getInt("sifDog");
                //boolean ispunjen = rs.getBoolean("ispunjen");

                //CiljEntity cilj = new CiljEntity(sifDog, sifCilja, nazivCilja, ispunjen);
                result.add(nazivCilja);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }


}
