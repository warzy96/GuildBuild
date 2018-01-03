package hr.fer.amigosi;

import hr.fer.amigosi.DAO.UserDAO;
import hr.fer.amigosi.Entities.KorisnikEntity;

public class Main {

	public static void main(String[] args) throws Exception {
		UserDAO dao = new UserDAO();
		KorisnikEntity korisnik = new KorisnikEntity();
		korisnik.setAdmin(false);
		korisnik.setNadimak("nickname");
		korisnik.setEmail("emaillissimus");
		korisnik.setLozinka("lozinkissimus");
		korisnik.setOpisKorisnika("oopis");
		korisnik.setRang("rangg");
		korisnik.setStatusPrijave(false);
		korisnik.setStatusRegistracije(true);
		korisnik.setSifraCeha(123);
		//dao.insertUser(korisnik);
		//dao.deleteUser(korisnik.getNadimak());
		//dao.updateUser(korisnik);
	}

}
