package hr.fer.amigosi.guildbuild.DAO;

import android.content.Intent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.RangConstants;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuild.entities.VoteEntity;

/**
 * Created by kerma on 15.1.2018..
 */

public class VoteDAO {

    static Connection connection = null;

    public VoteDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }

    public static void close() throws SQLException {
        connection.close();
    }

    public List<VoteEntity> maxVotes(String sifraCeha) throws SQLException{
        int maxGlasova=0;
        String query = "SELECT MAX(brGlasova) AS glasovi FROM vote WHERE sifCeh = '" + sifraCeha + "'" ;


        List<VoteEntity> result = new ArrayList<>();
        try{
            Statement stejtment = connection.createStatement();
            ResultSet resultSetMaxBrGlasova = stejtment.executeQuery(query);
            while(resultSetMaxBrGlasova.next()){
                maxGlasova = resultSetMaxBrGlasova.getInt("glasovi");
            }
            String query1 = "SELECT * FROM vote WHERE sifCeh='" + sifraCeha + "' AND brGlasova =" +maxGlasova;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query1);
            while(rs.next()){
                int sifCeh = rs.getInt("sifCeh");
                String nadimak = rs.getString("nadimak");
                int brGlasova = rs.getInt("brGlasova");
                boolean isGlasao = rs.getBoolean("isGlasao");
                VoteEntity voteEntity = new VoteEntity(sifCeh, nadimak, brGlasova, isGlasao);
                result.add(voteEntity);

            }


            String query2 = "UPDATE vote SET brGlasova = -1 WHERE vote.brGlasova < "+maxGlasova + " AND sifCeh = '" + sifraCeha +"'";
            Statement statement2 = connection.createStatement();
            statement2.executeUpdate(query2);

            String query3 = "UPDATE vote SET brGlasova=0 WHERE sifCeh = "+ Integer.parseInt(sifraCeha) + " AND brGlasova = " + maxGlasova;
            Statement statement3 = connection.createStatement();
            statement3.executeUpdate(query3);

            String query4 = "UPDATE vote SET isGlasao=0 WHERE sifCeh = "+Integer.parseInt(sifraCeha);
            Statement statement4 = connection.createStatement();
            statement4.executeUpdate(query4);

            return result;

        }catch(SQLException e){
            throw e;
        }
    }
    public boolean isFinished(String sifraCeha) throws SQLException{
        boolean flag=false;
        String query = "SELECT * FROM vote WHERE isGlasao=0 AND sifCeh = '" + sifraCeha + "'";
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                flag = false;
                return flag;
            }else{
                flag = true;
                return flag;
            }
        }catch(SQLException e){
            throw e;
        }
    }

    public VoteEntity getVoter(String nickname) throws SQLException{
        VoteEntity voteEntity = null;
        String query = "SELECT * FROM vote WHERE nadimak = '"
                + nickname + "';";
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                int sifCeh = rs.getInt("sifCeh");
                String nadimak = rs.getString("nadimak");
                int brGlasova = rs.getInt("brGlasova");
                boolean isGlasao = rs.getBoolean("isGlasao");

                voteEntity = new VoteEntity(sifCeh, nadimak, brGlasova, isGlasao);
                return voteEntity;
            }
        }catch (SQLException e){
            throw e;
        }
        return null;

    }

    public void incrementBrGlasova(VoteEntity voteEntity) throws SQLException {
        String querry = "UPDATE vote SET brGlasova = brGlasova+1"
                + " WHERE vote.nadimak = '" + voteEntity.getNadimak() + "';";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void insertAllCoordinatorsFromGuildIntoVote(String sifraCeha) throws SQLException, Exception {

        try{
            UserDAO userDAO = new UserDAO();
            List<KorisnikEntity> korisnikEntities = userDAO.loadAllCoordinators(sifraCeha);

            for(KorisnikEntity korisnikEntity : korisnikEntities){
                String query2 = "INSERT INTO vote VALUES ("
                        + Integer.parseInt(sifraCeha) + ", '"
                        + korisnikEntity.getNadimak() + "',"
                        + "0,0)";
                Statement statement2 = connection.createStatement(); // u petlji ili gore?
                statement2.executeUpdate(query2); //update ili query?

            }

        }catch (SQLException e){
             throw e;
        }catch (Exception e){
            throw e;
        }
    }

    public void markIsGlasao(String nickname) throws SQLException{
        String query = "UPDATE vote SET isGlasao = 1 WHERE vote.nadimak = '" + nickname + "';";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            throw e;
        }

    }

    public List<VoteEntity> loadAllCoordinatorsForGivenGuild(String sifraCeha) throws SQLException{
        String query = "SELECT * FROM vote WHERE sifCeh = "+Integer.parseInt(sifraCeha);
        List<VoteEntity> result = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                int sifCeh = rs.getInt("sifCeh");
                String nadimak = rs.getString("nadimak");
                int brGlasova = rs.getInt("brGlasova");
                boolean isGlasao = rs.getBoolean("isGlasao");

                VoteEntity voteEntity = new VoteEntity(sifCeh, nadimak, brGlasova, isGlasao);
                result.add(voteEntity);
            }
            return result;

        }catch(SQLException e){
            throw e;
        }
    }

    public void votingFinished(String sifraCeha) throws SQLException{
        String query = "DELETE FROM vote WHERE vote.sifCeh ="+Integer.parseInt(sifraCeha);

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException e){
            throw e;
        }

    }

    public Boolean checkIfGuildHasToVote(String sifraCeha) throws SQLException{
        String query = "SELECT vote.sifceh FROM vote";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                for(String temp : sifraCeha.split(",")) {
                    Integer sifCeh = rs.getInt("sifCeh");
                    if(temp.equals(sifCeh.toString())) {
                        return true;
                    }
                }
            }
        }
        catch(SQLException e) {
            throw e;
        }
        return false;
    }

    public Integer getGuildToVote(String sifraCeha) throws SQLException{
        String query = "SELECT vote.sifceh FROM vote";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                for(String temp : sifraCeha.split(",")) {
                    Integer sifCeh = rs.getInt("sifCeh");
                    if(temp.equals(sifCeh.toString())) {
                        return sifCeh;
                    }
                }
            }
        }
        catch(SQLException e) {
            throw e;
        }
        return 0;
    }
}
