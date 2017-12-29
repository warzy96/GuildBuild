package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.KlasaEntity;
import hr.fer.amigosi.guildbuildservice.entities.LikEntity;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface KlasaEntityDao {
    @Insert
    public void insertKlasa(KlasaEntity klasa);

    @Update
    public void updateKlasa(KlasaEntity klasa);

    @Delete
    public void deleteKlasa(KlasaEntity klasa);

    @Query("SELECT * FROM KlasaEntity WHERE sifraIgre == :sifraIgre")
    public List<KlasaEntity> getAllClassesForGame(int sifraIgre);

    @Query("SELECT LikEntity.level, LikEntity.sifraKlase, LikEntity.craftingSkills, LikEntity.nadimak FROM LikEntity, KlasaEntity WHERE LikEntity.sifraKlase=KlasaEntity.sifraKlase")
    public List<LikEntity> getCharacters();
}
