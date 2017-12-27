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

import hr.fer.amigosi.guildbuildservice.dao.KorisnikEntityDao;
import hr.fer.amigosi.guildbuildservice.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuildservice.model.AnonimniKorisnik;

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
    private ApplicationDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase.class).build();
        mUserDao = mDb.korisnikEntityDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        AnonimniKorisnik korisnik = new AnonimniKorisnik();
        korisnik.setEmail("blabla@gmail.com");
        korisnik.setLozinka("ttakoje");
        korisnik.setNadimak("Test");
        korisnik.setStatusRegistracije(false);
        mUserDao.insertUser(korisnik);
        AnonimniKorisnik korisnik2 = new AnonimniKorisnik();
        List<AnonimniKorisnik> korisnici = mUserDao.loadAllAnonymousUsers();
        assertThat(korisnici.size(), equalTo(2));
        assertThat(korisnici.get(0).getNadimak(), equalTo("Test"));
    }
}
