package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.PodciljEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class PodciljDAO {
    static Connection connection = null;
    public PodciljDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }
    public void insertSubgoal(PodciljEntity podciljEntity) throws SQLException {
        String querry = "INSERT INTO podcilj VALUES (null, '"
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

            rs.next();

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

    public void updateGoalsAndEvents() throws SQLException{
        String query1 = "SELECT distinct cilj.nazivCilja \n" +
                "FROM cilj \n" +
                "\tJOIN podcilj ON cilj.sifCilja=podcilj.sifCilja \n" +
                "where (select count(*) from podcilj where podcilj.sifCilja=cilj.sifCilja)=(select count(*) from podcilj where ispunjen and podcilj.sifCilja=cilj.sifCilja)";
        try{
            CiljDAO ciljDAO = new CiljDAO();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()){
                String nazivCilja = rs.getString("nazivCilja");
                ciljDAO.setGoalFinished(nazivCilja);
            }
        }catch(Exception e){
        }finally {
            CiljDAO.close();
        }

        String query2 = "sELECT distinct dogadaj.nazivDog \n" +
                "FROM dogadaj \n" +
                "\tJOIN cilj ON dogadaj.sifDog=cilj.sifDog\n" +
                "where (select count(*) from cilj where dogadaj.sifDog=cilj.sifDog)=(select count(*) from cilj where ispunjen and dogadaj.sifDog=cilj.sifDog)";
        try{
            DogadajDAO dogadajDAO = new DogadajDAO();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query2);

            while(rs.next()){
                String nazivDog = rs.getString("nazivDog");
                dogadajDAO.setEventFinished(nazivDog);
            }

        }catch(Exception e){
        }finally {
            CiljDAO.close();
        }

    }
}
