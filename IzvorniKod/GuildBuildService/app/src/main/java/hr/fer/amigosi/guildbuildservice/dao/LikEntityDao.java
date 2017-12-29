package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.LikEntity;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface LikEntityDao {
    @Insert
    public void insertLik(LikEntity lik);

    @Update
    public void updateLik(LikEntity lik);

    @Delete
    public void deleteLik(LikEntity lik);

    @Query("SELECT * FROM LikEntity WHERE sifraKlase == :sifraKlase")
    public List<LikEntity> getAllCharactersForClass(int sifraKlase);

    @Query("SELECT * FROM LikEntity WHERE nadimak LIKE :nadimak")
    public List<LikEntity> getAllCharactersForUser(String nadimak);
}
