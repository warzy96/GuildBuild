package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.Lik;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface LikEntityDao {
    @Insert
    public void insertLik(Lik lik);

    @Update
    public void updateLik(Lik lik);

    @Delete
    public void deleteLik(Lik lik);

    @Query("SELECT * FROM LikEntity WHERE sifraKlase == :sifraKlase")
    public Lik[] getAllCharactersForClass(int sifraKlase);

    @Query("SELECT * FROM LikEntity WHERE nadimak LIKE :nadimak")
    public Lik[] getAllCharactersForUser(String nadimak);
}
