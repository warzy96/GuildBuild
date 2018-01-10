package hr.fer.amigosi.guildbuild.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class UserDAO {
    Connection connection = null;
    public UserDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }
    public KorisnikEntity getUser(String email, String password) throws SQLException{
        KorisnikEntity korisnikEntity = null;
        String querry = "SELECT * FROM korisnik WHERE email= '"
                + email + "' AND lozinka = '"+ password +"' AND statusR ";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            if(rs != null) {
                korisnikEntity = new KorisnikEntity();
                String nadimak = rs.getString("nadimak");
                String email1 = rs.getString("email");
                String lozinka =rs.getString("lozinka");
                boolean statusR = rs.getBoolean("statusR");
                String rang = rs.getString("rang");
                int sifCeh = rs.getInt("sifCeh");
                boolean statusP = rs.getBoolean("statusP");
                String opis = rs.getString("opis");
                boolean isAdmin = rs.getBoolean("isAdmin");
                korisnikEntity = new KorisnikEntity(email1, nadimak, lozinka, statusR, rang, sifCeh, statusP, opis, isAdmin);
            }
        }
        catch (SQLException e) {
            throw e;
        }
        return korisnikEntity;
    }
    public void insertUser(KorisnikEntity korisnik) throws SQLException {
        String querry = "INSERT INTO korisnik VALUES ('"
                + korisnik.getNadimak() + "', '"
                + korisnik.getEmail() + "', '"
                + korisnik.getLozinka() + "', "
                + korisnik.isStatusRegistracije() + ", '"
                + korisnik.getRang() + "', "
                + korisnik.getSifraCeha() + ", "
                + korisnik.isStatusPrijave() + ", '"
                + korisnik.getOpisKorisnika() + "', "
                + korisnik.isAdmin()
                + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    public void deleteUser(String nickname) throws SQLException {
        String querry = "DELETE FROM korisnik WHERE korisnik.nadimak = '" +
                nickname + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    public void updateUser(KorisnikEntity korisnik) throws SQLException {
        String querry = "UPDATE korisnik SET nadimak = '"
                + korisnik.getNadimak() + "', email = '"
                + korisnik.getEmail() + "', lozinka = '"
                + korisnik.getLozinka() + "', statusR ="
                + korisnik.isStatusRegistracije() + ", rang = '"
                + korisnik.getRang() + "', sifCeh = "
                + korisnik.getSifraCeha() + ", statusP = "
                + korisnik.isStatusPrijave() + ", opis = '"
                + korisnik.getOpisKorisnika() + "', isAdmin = "
                + korisnik.isAdmin()
                + " WHERE korisnik.nadimak = '" + korisnik.getNadimak() + "';";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public List<KorisnikEntity> loadAllRegisteredUsers() throws SQLException{
        String querry = "SELECT * FROM korisnik WHERE statusR AND NOT isAdmin";
        List<KorisnikEntity> result = new ArrayList<>();

        try{
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
        catch(Exception e){
            throw e;
        }
    }

    public List<KorisnikEntity> loadAllAnonymousUsers() throws SQLException{
        String querry = "SELECT * FROM korisnik WHERE NOT statusR";
        List<KorisnikEntity> result = new ArrayList<>();

        try{
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
        catch(Exception e){
            throw e;
        }
    }

    public void updateUsersDescription(String nadimak, String opis) throws SQLException{
        String querry = "UPDATE korisnik SET opis = '"
                + opis + "'"
                + " WHERE korisnik.nadimak = '" + nadimak;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }
}
