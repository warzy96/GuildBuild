package hr.fer.amigosi.guildbuildservice;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.hamcrest.core.IsEqual;

import java.io.IOException;
import java.util.List;

import hr.fer.amigosi.guildbuildservice.dao.CehEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.IgraEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.KorisnikEntityDao;
import hr.fer.amigosi.guildbuildservice.entities.CehEntity;
import hr.fer.amigosi.guildbuildservice.entities.IgraEntity;
import hr.fer.amigosi.guildbuildservice.entities.KorisnikEntity;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private KorisnikEntityDao mUserDao;
    private CehEntityDao mCehDao;
    private IgraEntityDao mIgraDao;
    private ApplicationDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase.class).build();
        mUserDao = mDb.korisnikEntityDao();
        mCehDao = mDb.cehEntityDao();
        mIgraDao = mDb.igraEntityDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        KorisnikEntity korisnik1 = new KorisnikEntity();
        korisnik1.setEmail("blabla@gmail.com");
        korisnik1.setLozinka("ttakoje");
        korisnik1.setNadimak("Test");
        korisnik1.setStatusRegistracije(false);
        korisnik1.setSifraCeha(2);

        CehEntity ceh1 = new CehEntity();
        ceh1.setSifraCeha(1);
        ceh1.setSifraIgre(10);

        CehEntity ceh2 = new CehEntity();
        ceh2.setSifraCeha(2);
        ceh2.setSifraIgre(10);

        IgraEntity igra = new IgraEntity();
        igra.setSifraIgre(10);

        mIgraDao.insertIgra(igra);
        mCehDao.insertCeh(ceh1);
        mCehDao.insertCeh(ceh2);
        mUserDao.insertUser(korisnik1);

        KorisnikEntity korisnik2 = new KorisnikEntity();
        korisnik2.setEmail("blablaa@gmail.com");
        korisnik2.setLozinka("takoje");
        korisnik2.setNadimak("Test1");
        korisnik2.setStatusRegistracije(true);
        korisnik2.setSifraCeha(2);

        KorisnikEntity korisnik3 = new KorisnikEntity();
        korisnik3.setEmail("blablaa1@gmail.com");
        korisnik3.setLozinka("takoje");
        korisnik3.setNadimak("Test2");
        korisnik3.setStatusRegistracije(true);
        korisnik3.setSifraCeha(2);

        mUserDao.insertUser(korisnik2);
        mUserDao.insertUser(korisnik3);
        List<KorisnikEntity> Akorisnici = mUserDao.loadAllAnonymousUsers();
        List<KorisnikEntity> Rkorisnici = mUserDao.loadAllRegisteredUsers();

        List<KorisnikEntity> clanovi1 = mCehDao.getGuildMemebers(1);
        List<KorisnikEntity> clanovi2 = mCehDao.getGuildMemebers(2);


        assertThat(clanovi1.size(), equalTo(0));
        assertThat(clanovi2.size(), equalTo(3));
        assertThat(Akorisnici.size(), equalTo(1));
        assertThat(Rkorisnici.get(0).getNadimak(), equalTo("Test1"));
        assertThat(Rkorisnici.size(), equalTo(2));

    }
}
