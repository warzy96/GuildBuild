package hr.fer.amigosi;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hr.fer.amigosi.DAO.CehDAO;
import hr.fer.amigosi.DAO.CiljDAO;
import hr.fer.amigosi.DAO.DogadajDAO;
import hr.fer.amigosi.DAO.IgraDAO;
import hr.fer.amigosi.DAO.KlasaDAO;
import hr.fer.amigosi.DAO.LikDAO;
import hr.fer.amigosi.DAO.ObrazacDAO;
import hr.fer.amigosi.DAO.PodciljDAO;
import hr.fer.amigosi.DAO.UserDAO;
import hr.fer.amigosi.Entities.CehEntity;
import hr.fer.amigosi.Entities.IgraEntity;
import hr.fer.amigosi.Entities.KorisnikEntity;


//testovi se izvode abecednim redom
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {

	private UserDAO userDao;
	private IgraDAO igraDao;
	private CehDAO cehDao;
	private DogadajDAO dogadajDao;
	private CiljDAO ciljDao;
	private PodciljDAO podciljDao;
	private KlasaDAO klasaDao;
	private LikDAO likDao;
	private ObrazacDAO obrazacDao;
	private List<KorisnikEntity> korisnici;
	private IgraEntity igra;
	private CehEntity ceh1;
	private CehEntity ceh2;
	
	@Before
    public void createDb() throws Exception {
		userDao = new UserDAO();
		igraDao = new IgraDAO();
		cehDao = new CehDAO();
		dogadajDao = new DogadajDAO();
		ciljDao = new CiljDAO();
		podciljDao = new PodciljDAO();
		klasaDao = new KlasaDAO();
		likDao = new LikDAO();
		obrazacDao = new ObrazacDAO();
		korisnici = new ArrayList<>();
		//Anonimni korisnici
		for(int i=1;i<=10;i++){
			KorisnikEntity korisnik = new KorisnikEntity("mail"+i, "nadimak"+i, "pasword", false, "clan", 0, false, "isti svi", false);
			korisnici.add(korisnik);
		}
		//Registrirani korisnici
		for(int i=11;i<=20;i++){
			KorisnikEntity korisnik = new KorisnikEntity("mail"+i, "nadimak"+i, "pasword", true, "koordinator", 0, false, "isti svi", false);
			korisnici.add(korisnik);
		}
		igra = new IgraEntity(10, "Wow");
		ceh1 = new CehEntity(1, "prvi", 10, "prvi ceh");
		ceh2 = new CehEntity(2, "drugi", 10, "drugi ceh");

    }
	
	@Test
	public void aTestUserAdding() throws SQLException {
		for(KorisnikEntity korisnik : korisnici){
			userDao.insertUser(korisnik);
		}
		
		assertEquals(10, userDao.loadAllAnonymousUsers().size());
		assertEquals(10, userDao.loadAllRegisteredUsers().size());
	}
	
	
	@Test
	public void bTestGameAndGuildInserting() throws SQLException{
		igraDao.insertGame(igra);
		
		cehDao.insertGuild(ceh1);
		cehDao.insertGuild(ceh2);
		int i=0;
		List<KorisnikEntity> registriraniKorisnici = userDao.loadAllRegisteredUsers();
		for(KorisnikEntity korisnik : registriraniKorisnici){
			if(i<5){
				korisnik.setSifraCeha(ceh1.getSifraCeha());
				userDao.updateUser(korisnik);
				i++;
			}else{
				korisnik.setSifraCeha(ceh2.getSifraCeha());
				userDao.updateUser(korisnik);
				i++;
			}
		}
		
		assertEquals(5, cehDao.getGuildMembers(1).size());
		assertEquals(5, cehDao.getGuildMembers(2).size());

	}

}
