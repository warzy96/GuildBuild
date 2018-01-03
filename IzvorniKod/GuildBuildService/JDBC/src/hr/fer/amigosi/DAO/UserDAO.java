package hr.fer.amigosi.DAO;

import java.sql.*;

import hr.fer.amigosi.DatabaseConnection;
import hr.fer.amigosi.Entities.KorisnikEntity;

/*COLUMNS: nadimak, email, lozinka, statusR, rang, sifCeh,
 *  statusP, opis, isAdmin
 */
public class UserDAO {
	Connection connection = null;
	public UserDAO() throws Exception{
		connection = DatabaseConnection.getConnection();
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
}
