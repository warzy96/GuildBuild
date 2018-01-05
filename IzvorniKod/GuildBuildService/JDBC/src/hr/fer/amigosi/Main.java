package hr.fer.amigosi;

import hr.fer.amigosi.DAO.*;
import hr.fer.amigosi.Entities.*;

public class Main {

	/*
	 * Koristi za testiranje svih funkcija
	 * MySQL (kolko vidim iz testiranja) ne podrzava da je int = 0... Znaci sve 
	 * sifre moraju biti >0.
	 * Ako se prekrsi neki constraint (tipa foreign key i slicno) baca exception.
	 * Znaci da prvo treba dodati npr. Igra pa tek onda Ceh pa tek onda Dogadaj i sl.
	 * Sve funkcije koje su zakomentirane su isprobane i rade.
	 * 
	 * 
	 * INSERT SYNTAX
	 * INSERT INTO table_name VALUES (col1, col2, col3...)
	 * Ako je vrijednost col1 String onda mora biti u ' ' [jednostruki navodnici]
	 * Ako je int ili bool onda ne treba ' '
	 * 
	 * DELETE SYNTAX
	 * DELETE FROM table_name WHERE condition
	 * condition je usporedba sifri buduci da je sifra PK
	 * 
	 * UPDATE SYNTAX
	 * UPDATE table_name SET column_name = value, column_name1 = value1 WHERE condition
	 * condition opet po sifri
	 */
	
	//TODO: KlasaDAO, LikDAO, ObrazacDAO insert, update, delete + sve DAO klase slozeni upiti
	public static void main(String[] args) throws Exception {
		UserDAO userDao = new UserDAO();
		IgraDAO igraDao = new IgraDAO();
		CehDAO cehDao = new CehDAO();
		DogadajDAO dogadajDao = new DogadajDAO();
		CiljDAO ciljDao = new CiljDAO();
		PodciljDAO podciljDao = new PodciljDAO();
		KlasaDAO klasaDao = new KlasaDAO();
		LikDAO likDao = new LikDAO();
		ObrazacDAO obrazacDao = new ObrazacDAO();
		
		//KORISNIK
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
		
		
		//CEH
		CehEntity ceh = new CehEntity();
		ceh.setNaziv("guild");
		ceh.setOpis("op");
		ceh.setSifraCeha(123);
		ceh.setSifraIgre(111);
		
		
		//IGRA
		IgraEntity igra = new IgraEntity();
		igra.setNazivIgre("WoW");
		igra.setSifraIgre(111);
		
		//DOGADAJ
		DogadajEntity dogadajEntity = new DogadajEntity();
		dogadajEntity.setSifraDogadaja(1);
		dogadajEntity.setNazivDogadaja("asdf");
		dogadajEntity.setSifraCeha(ceh.getSifraCeha());
		dogadajEntity.setVidljivost(true);
		dogadajEntity.setIspunjenost(false);
		
		//CILJ
		CiljEntity ciljEntity = new CiljEntity();
		ciljEntity.setIspunjenost(false);
		ciljEntity.setNazivCilja("aaa");
		ciljEntity.setSifraCilja(314);
		ciljEntity.setSifraDogadaja(dogadajEntity.getSifraDogadaja());
		
		//PODCILJ
		PodciljEntity podciljEntity = new PodciljEntity();
		podciljEntity.setIspunjenost(false);
		podciljEntity.setNazivPodcilja("podcilj");
		podciljEntity.setSifraCilja(ciljEntity.getSifraCilja());
		podciljEntity.setSifraPodcilja(999);
		
		//KLASA
		KlasaEntity klasaEntity = new KlasaEntity();
		klasaEntity.setNazivKlase("ajmo");
		klasaEntity.setSifraIgre(igra.getSifraIgre());
		klasaEntity.setSifraKlase(1);
		
		//LIK
		LikEntity likEntity = new LikEntity();
		likEntity.setNadimak(korisnik.getNadimak());
		likEntity.setSifraKlase(klasaEntity.getSifraKlase());
		likEntity.setLevel(10);
		likEntity.setCraftingSkills("Crafting runes: 5");
		
		//OBRAZAC
		ObrazacEntity obrazacEntity = new ObrazacEntity();
		obrazacEntity.setNadimakKorisnika(korisnik.getNadimak());
		obrazacEntity.setSifraCeha(ceh.getSifraCeha());
		obrazacEntity.setPoruka("lijep, njezan, neizbjezan");
		
		
		
		//OPERACIJE ZA KORISNIKA
		userDao.insertUser(korisnik);
		//userDao.deleteUser(korisnik.getNadimak());
		userDao.updateUser(korisnik);
		
		//OPERACIJE ZA IGRU
		igraDao.insertGame(igra);
		
		//OPERACIJE ZA CEH
		cehDao.insertGuild(ceh);
		//cehDao.deleteGuild(ceh.getSifraCeha());
		ceh.setNaziv("guild1");
		cehDao.updateGuild(ceh);
		
		//OPERACIJE ZA DOGADAJ
		dogadajDao.insertEvent(dogadajEntity);
		//dogadajDao.deleteEvent(dogadajEntity.getSifraDogadaja());
		dogadajEntity.setNazivDogadaja("ghjk");
		dogadajDao.updateEvent(dogadajEntity);
				
		//OPERACIJE ZA CILJ
		ciljDao.insertGoal(ciljEntity);
		//ciljDao.deleteGoal(ciljEntity.getSifraCilja());
		ciljEntity.setNazivCilja("bbb");
		ciljDao.updateGoal(ciljEntity);
		
		//OPERACIJE ZA PODCILJ
		podciljDao.insertSubgoal(podciljEntity);
		//podciljDao.deleteSubgoal(podciljEntity.getSifraPodcilja());
		podciljEntity.setNazivPodcilja("m&m");
		podciljDao.updateSubgoal(podciljEntity);
		
		//OPERACIJE ZA KLASU
		klasaDao.insertClass(klasaEntity);
		
		//OPERACIJE ZA LIK
		likDao.insertCharacter(likEntity);
		
		//OPERACIJE ZA OBRAZAC
		obrazacDao.insertForm(obrazacEntity);
		
	}

}
