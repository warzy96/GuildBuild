package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.CehEntity;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class CehDAO {
    Connection connection = null;
    public CehDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }
    public void insertGuild(CehEntity cehEntity) throws SQLException {
        String querry = "INSERT INTO ceh VALUES ("
                + cehEntity.getSifraCeha() + ", '"
                + cehEntity.getNaziv() + "', "
                + cehEntity.getSifraIgre() + ", '"
                + cehEntity.getOpis()
                + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    public void deleteGuild(int sifraCeha) throws SQLException {
        String querry = "DELETE FROM ceh WHERE ceh.sifCeh = " +
                sifraCeha;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    public void updateGuild(CehEntity cehEntity) throws SQLException {
        String querry = "UPDATE ceh SET "
                + "sifCeh = " + cehEntity.getSifraCeha()
                + ", naziv = '" + cehEntity.getNaziv() + "'"
                + ", sifIgre =" + cehEntity.getSifraIgre()
                + ", opis ='" + cehEntity.getOpis() + "'"
                + " WHERE ceh.sifCeh = " + cehEntity.getSifraCeha();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<CehEntity> getAllGuilds() throws SQLException{
        String querry = "SELECT * FROM ceh";
        List<CehEntity> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                int sifCeh=rs.getInt("sifCeh");
                String naziv=rs.getString("naziv");
                int sifIgre=rs.getInt("sifIgre");
                String opis=rs.getString("opis");

                CehEntity ceh = new CehEntity(sifCeh, naziv, sifIgre, opis);
                result.add(ceh);
            }
            return result;

        }
        catch(SQLException e) {
            throw e;
        }
    }

    public CehEntity getGuild(int sifraCeha) throws SQLException{
        String querry = "SELECT * FROM ceh WHERE ceh.sifCeh =" + sifraCeha;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            int sifCeh=rs.getInt("sifCeh");
            String naziv=rs.getString("naziv");
            int sifIgre=rs.getInt("sifIgre");
            String opis=rs.getString("opis");

            CehEntity ceh = new CehEntity(sifCeh, naziv, sifIgre, opis);

            return ceh;

        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<CehEntity> getGuildsForGame(int sifraIgre) throws SQLException{
        String querry = "SELECT * FROM ceh WHERE ceh.sifIgre =" + sifraIgre;
        List<CehEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                int sifCeh=rs.getInt("sifCeh");
                String naziv=rs.getString("naziv");
                int sifIgre=rs.getInt("sifIgre");
                String opis=rs.getString("opis");

                CehEntity ceh = new CehEntity(sifCeh, naziv, sifIgre, opis);
                result.add(ceh);
            }
            return result;

        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<KorisnikEntity> getGuildMembers(int sifraCeha) throws SQLException{
        String querry = "SELECT * FROM korisnik WHERE korisnik.sifCeh =" + sifraCeha;
        List<KorisnikEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                String nadimak = rs.getString("nadimak");
                String email = rs.getString("email");
                String lozinka =rs.getString("lozinka");
                boolean statusR = rs.getBoolean("statusR");
                String rang = rs.getString("rang");
                int sifCeh = rs.getInt("sifCeh");
                boolean statusP = rs.getBoolean("statusP");
                String opis = rs.getString("opis");
                boolean isAdmin = rs.getBoolean("isAdmin");

                KorisnikEntity korisnik = new KorisnikEntity(email, nadimak, lozinka, statusR, rang, sifCeh, statusP, opis, isAdmin);
                result.add(korisnik);
            }
            return result;

        }
        catch(SQLException e) {
            throw e;
        }
    }
}
