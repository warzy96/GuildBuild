package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.Ceh;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface CehEntityDao {
    @Insert
    public void insertCeh(Ceh ceh);

    @Update
    public void updateCeh(Ceh ceh);

    @Delete
    public void deleteCeh(Ceh ceh);

    @Query("SELECT * FROM CehEntity")
    public Ceh[] getAllGuilds();

    @Query("SELECT * FROM CehEntity WHERE sifraCeha LIKE :sifraCeha")
    public Ceh getGuild(int sifraCeha);

    @Query("SELECT * FROM CehEntity WHERE sifraIgre == :sifraIgre")
    public Ceh[] getGuildsForGame(int sifraIgre);
}
