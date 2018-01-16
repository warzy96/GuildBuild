package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.LikEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class LikDAO {

    static Connection connection = null;

    public LikDAO() throws Exception{
        connection= DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }
    public void insertCharacter(LikEntity likEntity) throws SQLException{
        String querry = "INSERT INTO lik VALUES ('"
                + likEntity.getNadimak() + "', "
                + likEntity.getSifraKlase() + ", "
                + likEntity.getLevel() + ", '"
                + likEntity.getCraftingSkills() + "', '"
                + likEntity.getImeLika() + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void deleteCharacter(LikEntity likEntity) throws SQLException {
        String querry = "DELETE FROM lik WHERE lik.sifKlase = " +
                likEntity.getSifraKlase() +
                "AND lik.nadimak = '" +likEntity.getNadimak() + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void updateCharacter(LikEntity likEntity) throws SQLException{
        String querry = "UPDATE lik SET "
                + "nadimak = '" + likEntity.getNadimak()+ "'"
                + ", sifKlase =" + likEntity.getSifraKlase()
                + ", lvl = " + likEntity.getLevel()
                + ", craftingSkills ='" + likEntity.getCraftingSkills() + "'"
                + ", imeLika ='" + likEntity.getImeLika() + "'"
                + " WHERE lik.nadimak = '" + likEntity.getNadimak() + "'"
                + " AND lik.sifKlase = " + likEntity.getSifraKlase();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<LikEntity> getAllCharactersForClass(int sifraKlase) throws SQLException{
        String querry = "SELECT * FROM lik WHERE lik.sifKlase =" + sifraKlase;
        List<LikEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                String nadimak = rs.getString("nadimak");
                int sifKlase = rs.getInt("sifKlase");
                int level = rs.getInt("lvl");
                String craftingSkills = rs.getString("craftingSkills");
                String imeLika = rs.getString("imeLika");

                LikEntity lik = new LikEntity(level, sifKlase, craftingSkills, nadimak, imeLika);
                result.add(lik);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    public List<LikEntity> getAllCharactersForUser(String nadimakKorisnika) throws SQLException{
        String querry = "SELECT * FROM lik WHERE lik.nadimak = '" + nadimakKorisnika+ "'";
        List<LikEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                String nadimak = rs.getString("nadimak");
                int sifKlase = rs.getInt("sifKlase");
                int level = rs.getInt("lvl");
                String craftingSkills = rs.getString("craftingSkills");
                String imeLika = rs.getString("imeLika");

                LikEntity lik = new LikEntity(level, sifKlase, craftingSkills, nadimak, imeLika);
                result.add(lik);
            }
            return result;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    public void deleteCharactersForClass(int sifraKlase) throws SQLException{
        String querry = "DELETE FROM lik WHERE lik.sifKlase= " + sifraKlase;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e)
        {
            throw e;
        }
    }
}
