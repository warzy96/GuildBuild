package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.CehEntity;
import hr.fer.amigosi.guildbuildservice.entities.DogadajEntity;
import hr.fer.amigosi.guildbuildservice.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuildservice.entities.ObrazacEntity;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface CehEntityDao {
    @Insert
    public void insertCeh(CehEntity ceh);

    @Update
    public void updateCeh(CehEntity ceh);

    @Delete
    public void deleteCeh(CehEntity ceh);

    @Query("SELECT * FROM CehEntity")
    public CehEntity[] getAllGuilds();

    @Query("SELECT * FROM CehEntity WHERE sifraCeha LIKE :sifraCeha")
    public CehEntity getGuild(int sifraCeha);

    @Query("SELECT * FROM CehEntity WHERE sifraIgre == :sifraIgre")
    public List<CehEntity> getGuildsForGame(int sifraIgre);

    @Query("SELECT * FROM KorisnikEntity WHERE KorisnikEntity.sifraCeha == :sifraCeha")
    public List<KorisnikEntity> getGuildMemebers(int sifraCeha);

    @Query("SELECT ObrazacEntity.sifraCeha, ObrazacEntity.nadimakKorisnika, ObrazacEntity.poruka FROM ObrazacEntity, CehEntity WHERE ObrazacEntity.sifraCeha==CehEntity.sifraCeha")
    public List<ObrazacEntity> getObrasci();

    @Query("SELECT DogadajEntity.sifraDogadaja, DogadajEntity.nazivDogadaja, DogadajEntity.sifraCeha," +
            "DogadajEntity.ispunjenost, DogadajEntity.vidljivost FROM DogadajEntity, CehEntity WHERE DogadajEntity.sifraCeha==CehEntity.sifraCeha")
    public List<DogadajEntity> getDogadaji();
}
