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
    static Connection connection = null;
    public CehDAO() throws ClassNotFoundException, SQLException{
        connection = DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
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
    public void insertGuild(String nazivCeha, int sifraIgre, String opisCeha) throws SQLException {
        String querry = "INSERT INTO ceh VALUES ("
                + null + ", '"
                + nazivCeha + "', "
                + sifraIgre + ", '"
                + opisCeha
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

            rs.next();

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
    public int getGuildNumber(String nazivCeha, int sifraIgre) throws SQLException{
        String querry = "SELECT * FROM ceh WHERE ceh.naziv='" + nazivCeha
                + "' AND ceh.sifIgre=" + sifraIgre;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            if(rs.next()) {
                int sifCeh=rs.getInt("sifCeh");
                return sifCeh;
            }
            return 0;
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

    public List<KorisnikEntity> getGuildMembers(String sifraCeha) throws SQLException{
        String querry = "SELECT * FROM korisnik WHERE korisnik.sifCeh LIKE '%"
                + sifraCeha + "%'";
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
                String sifCeh = rs.getString("sifCeh");
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

    public List<KorisnikEntity> getGuildMembersWithoutCurrentMember(String sifraCeha, String nickname) throws SQLException{
        String querry = "SELECT * FROM korisnik WHERE korisnik.sifCeh LIKE '%"
                + sifraCeha + "%'";
        List<KorisnikEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            while(rs.next()){
                String nadimak = rs.getString("nadimak");
                if(nadimak.equals(nickname)) continue;
                String email = rs.getString("email");
                String lozinka =rs.getString("lozinka");
                boolean statusR = rs.getBoolean("statusR");
                String rang = rs.getString("rang");
                String sifCeh = rs.getString("sifCeh");
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

    public boolean checkIfMemExists(String sifraCeha) throws SQLException{
        String querry = "SELECT * FROM korisnik WHERE korisnik.sifCeh LIKE '%" + sifraCeha +"%'";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);

            if(rs.next()){
                return true;
            }else{
                return false;
            }

        }
        catch(SQLException e) {
            throw e;
        }
    }
}
