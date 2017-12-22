package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.Klasa;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface KlasaEntityDao {
    @Insert
    public void insertKlasa(Klasa klasa);

    @Update
    public void updateKlasa(Klasa klasa);

    @Delete
    public void deleteKlasa(Klasa klasa);

    @Query("SELECT * FROM KlasaEntity WHERE sifraIgre == :sifraIgre")
    public Klasa[] getAllClassesForGame(int sifraIgre);
}
