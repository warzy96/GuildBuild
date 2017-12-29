package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuildservice.entities.LikEntity;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface KorisnikEntityDao {
    @Insert
    public void insertUser(KorisnikEntity korisnik);

    @Update
    public void updateUser(KorisnikEntity korisnik);


    @Delete
    public void deleteUser (KorisnikEntity korisnik);

    @Query("SELECT * FROM KorisnikEntity WHERE statusRegistracije")
    public List<KorisnikEntity> loadAllRegisteredUsers();

    @Query("SELECT * FROM KorisnikEntity WHERE NOT statusRegistracije")
    public List<KorisnikEntity> loadAllAnonymousUsers();

    @Query("SELECT LikEntity.level, LikEntity.sifraKlase, LikEntity.craftingSkills, LikEntity.nadimak" +
            " FROM LikEntity, KorisnikEntity WHERE (likEntity.nadimak=KorisnikEntity.nadimak AND statusRegistracije)")
    public List<LikEntity> getUserCharacters();

    @Query("SELECT * FROM KorisnikEntity WHERE isAdmin")
    public KorisnikEntity getAdmin();

}
