package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.AnonimniKorisnik;
import hr.fer.amigosi.guildbuildservice.model.RegistriraniKorisnik;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface KorisnikEntityDao {
    @Insert
    public void insertUser(AnonimniKorisnik anonimniKorisnik);

    @Insert
    public void insertUser(RegistriraniKorisnik registriraniKorisnik);

    @Update
    public void updateUser(AnonimniKorisnik anonimniKorisnik);

    @Update
    public void updateUser(RegistriraniKorisnik registriraniKorisnik);

    @Delete
    public void deleteUser(AnonimniKorisnik anonimniKorisnik);

    @Delete
    public void deleteUser (RegistriraniKorisnik registriraniKorisnik);

    @Query("SELECT * FROM KorisnikEntity WHERE statusRegistracije")
    public RegistriraniKorisnik[] loadAllRegisteredUsers();

    @Query("SELECT * FROM KorisnikEntity WHERE NOT statusRegistracije")
    public AnonimniKorisnik[] loadAllAnonymousUsers();

}
