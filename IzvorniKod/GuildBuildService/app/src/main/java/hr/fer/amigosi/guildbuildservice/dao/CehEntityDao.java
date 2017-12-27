package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.CehEntity;
import hr.fer.amigosi.guildbuildservice.model.Ceh;

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
}
