package hr.fer.amigosi.guildbuild.DAO;

import android.support.annotation.NonNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.RangConstants;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

/**
 * Created by Ivan on 08/01/2018.
 */

public class UserDAO {
    static Connection connection = null;
    public UserDAO() throws Exception{
        connection = DatabaseConnection.getConnection();
    }
    public static void close() throws SQLException {
        connection.close();
    }
    public KorisnikEntity getUser(String email, String password) throws SQLException{
        KorisnikEntity korisnikEntity = null;
        String querry = "SELECT * FROM korisnik WHERE email= '"
                + email + "' AND lozinka = '"+ password +"' AND statusR ";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            if(rs.next()) {
                String nadimak = rs.getString("nadimak");
                String email1 = rs.getString("email");
                String lozinka =rs.getString("lozinka");
                boolean statusR = rs.getBoolean("statusR");
                String sifCeh = rs.getString("sifCeh");
                boolean statusP = rs.getBoolean("statusP");
                String opis = rs.getString("opis");
                boolean isAdmin = rs.getBoolean("isAdmin");
                korisnikEntity = new KorisnikEntity(email1, nadimak, lozinka, statusR, sifCeh, statusP, opis, isAdmin);
                return korisnikEntity;
            }
        }
        catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public KorisnikEntity getUser(String nickname) throws SQLException{
        KorisnikEntity korisnikEntity = null;
        String querry = "SELECT * FROM korisnik "
                + "WHERE nadimak= '"
                + nickname + "' AND statusR";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            if(rs.next()) {
                String nadimak = rs.getString("nadimak");
                String email1 = rs.getString("email");
                String lozinka =rs.getString("lozinka");
                boolean statusR = rs.getBoolean("statusR");
                String sifCeh = rs.getString("sifCeh");
                boolean statusP = rs.getBoolean("statusP");
                String opis = rs.getString("opis");
                boolean isAdmin = rs.getBoolean("isAdmin");
                korisnikEntity = new KorisnikEntity(email1, nadimak, lozinka, statusR, sifCeh, statusP, opis, isAdmin);
                return korisnikEntity;
            }
        }
        catch (SQLException e) {
            throw e;
        }
        return  null;
    }


    public KorisnikEntity getUserWithRank(String nickname, String sifraCeha) throws SQLException{
        KorisnikEntity korisnikEntity = null;
        String querry = "SELECT * FROM korisnik JOIN rang ON korisnik.nadimak = rang.nadimak "
                + "WHERE korisnik.nadimak= '"
                + nickname + "' AND statusR AND rang.sifCeh = " + Integer.parseInt(sifraCeha);
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            if(rs.next()) {
                String nadimak = rs.getString("nadimak");
                String email1 = rs.getString("email");
                String lozinka =rs.getString("lozinka");
                boolean statusR = rs.getBoolean("statusR");
                String rang = rs.getString("rang");
                //String sifCeh = rs.getString("sifCeh");
                boolean statusP = rs.getBoolean("statusP");
                String opis = rs.getString("opis");
                boolean isAdmin = rs.getBoolean("isAdmin");
                korisnikEntity = new KorisnikEntity(email1, nadimak, lozinka, statusR, rang, sifraCeha, statusP, opis, isAdmin);
                return korisnikEntity;
            }
        }
        catch (SQLException e) {
            throw e;
        }
        return  null;
    }
    public void insertUser(KorisnikEntity korisnik) throws SQLException {
        String querry = "INSERT INTO korisnik VALUES ('"
                + korisnik.getNadimak() + "', '"
                + korisnik.getEmail() + "', '"
                + korisnik.getLozinka() + "', "
                + korisnik.isStatusRegistracije() + ", '"
                + korisnik.getSifraCeha() + "', "
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
        String querry = "DELETE FROM korisnik WHERE nadimak = '" +
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
        String querry;
        if(korisnik.getSifraCeha().isEmpty()) {
            querry = "UPDATE korisnik SET nadimak = '"
                    + korisnik.getNadimak() + "', email = '"
                    + korisnik.getEmail() + "', lozinka = '"
                    + korisnik.getLozinka() + "', statusR ="
                    + korisnik.isStatusRegistracije() + ", sifCeh = "
                    + null + ", statusP = "
                    + korisnik.isStatusPrijave() + ", opis = '"
                    + korisnik.getOpisKorisnika() + "', isAdmin = "
                    + korisnik.isAdmin()
                    + " WHERE korisnik.nadimak = '" + korisnik.getNadimak() + "';";
        }else{
            querry = "UPDATE korisnik SET nadimak = '"
                    + korisnik.getNadimak() + "', email = '"
                    + korisnik.getEmail() + "', lozinka = '"
                    + korisnik.getLozinka() + "', statusR ="
                    + korisnik.isStatusRegistracije() + ", sifCeh = '"
                    + korisnik.getSifraCeha() + "', statusP = "
                    + korisnik.isStatusPrijave() + ", opis = '"
                    + korisnik.getOpisKorisnika() + "', isAdmin = "
                    + korisnik.isAdmin()
                    + " WHERE korisnik.nadimak = '" + korisnik.getNadimak() + "';";
        }
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
                String sifCeh = rs.getString("sifCeh");
                boolean statusP = rs.getBoolean("statusP");
                String opis = rs.getString("opis");
                boolean isAdmin = rs.getBoolean("isAdmin");

                KorisnikEntity korisnik = new KorisnikEntity(email, nadimak, lozinka, statusR, sifCeh, statusP, opis, isAdmin);
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
                String sifCeh = rs.getString("sifCeh");
                boolean statusP = rs.getBoolean("statusP");
                String opis = rs.getString("opis");
                boolean isAdmin = rs.getBoolean("isAdmin");

                KorisnikEntity korisnik = new KorisnikEntity(email, nadimak, lozinka, statusR, sifCeh, statusP, opis, isAdmin);
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
                + " WHERE nadimak = '" + nadimak + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public void updateUserGuild(String nickname, String sifraCeha) throws SQLException {
        String querry = "UPDATE korisnik SET "
                + "sifCeh ='" + sifraCeha
                + "' WHERE korisnik.nadimak = '" + nickname + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }


    public boolean checkIfGuildHasALeader(String sifraCeha) throws SQLException{

        if(sifraCeha == null){
            return true;
        }
        String query = "SELECT * FROM korisnik NATURAL JOIN rang WHERE korisnik.sifCeh LIKE '%"+sifraCeha
                + "%' AND rang.rang = 'leader'";

        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if(!rs.next()){
                return false;
            }
            else{
                return true;
            }

        }catch (SQLException e){
            throw e;
        }
    }


    public List<KorisnikEntity> loadAllCoordinators(String sifraCeha) throws SQLException{
        String query = "SELECT * FROM korisnik NATURAL JOIN rang WHERE korisnik.sifCeh LIKE '%" + sifraCeha + "%' AND rang.rang = '"
                + RangConstants.coordinator + "'";
        List<KorisnikEntity> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                    String nadimak = rs.getString("nadimak");
                    String email = rs.getString("email");
                    String lozinka = rs.getString("lozinka");
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
        }catch(SQLException e){
            throw e;
        }

    }



    public void removeRanksAndSifraCehaForGuild(String sifraCeha)  throws SQLException, Exception{
        try {
            CehDAO cehDAO = new CehDAO();
            List<KorisnikEntity> korisnikEntityList = cehDAO.getGuildMembers(sifraCeha);
            for(KorisnikEntity korisnikEntity : korisnikEntityList) {
                if(korisnikEntity.getSifraCeha().contains(",")) {
                    String novaSifraCeha = korisnikEntity.getSifraCeha().replace(sifraCeha, "");
                    if(novaSifraCeha.startsWith(",")) {
                        novaSifraCeha = novaSifraCeha.replace(",", "");
                    }
                    else {
                        if(novaSifraCeha.contains(",,")) {
                            novaSifraCeha = novaSifraCeha.replace(",,", ",");
                        }
                    }
                    updateUserGuild(korisnikEntity.getNadimak(), novaSifraCeha);
                }
                else {
                    String query = "UPDATE korisnik SET "
                            + "sifCeh = NULL WHERE korisnik.nadimak = '" + korisnikEntity.getNadimak() + "'";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);
                }
            }
            String query2 = "DELETE FROM rang WHERE rang.sifCeh = "
                    + Integer.parseInt(sifraCeha);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query2);
        }
        catch (SQLException e) {
            throw e;
        }
        catch (Exception e) {
            throw e;
        }


    }

    public String getSifCeh(String nickname) throws SQLException{
        String query = "SELECT korisnik.sifCeh FROM korisnik WHERE korisnik.nadimak = '"
                + nickname + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                return resultSet.getString("sifCeh");
            }
        }
        catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public void deleteUserGuild(String nadimak, String sifreCehova, String sifCeha) throws SQLException{
        String novi,querry;
        if(sifreCehova.contains(","+sifCeha)){
            novi = sifreCehova.replace(","+sifCeha,"");
        }else if(sifreCehova.contains(sifCeha+",")){
            novi = sifreCehova.replace(sifCeha+",","");
        }else{
            novi = sifreCehova.replace(sifCeha,"");
        }

        if(novi.isEmpty()){
            novi=null;
            querry = "UPDATE korisnik SET "
                    + "sifCeh =" + novi
                    + " WHERE korisnik.nadimak = '" + nadimak + "'";
        }else{
            querry = "UPDATE korisnik SET "
                    + "sifCeh ='" + novi
                    + "' WHERE korisnik.nadimak = '" + nadimak + "'";
        }


        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    public String getSifreCehova(String nadimak) throws SQLException{
        String querry = "SELECT korisnik.sifCeh FROM korisnik WHERE korisnik.nadimak='"+nadimak+"'";

        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            rs.next();
            String result = rs.getString("sifCeh");
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
