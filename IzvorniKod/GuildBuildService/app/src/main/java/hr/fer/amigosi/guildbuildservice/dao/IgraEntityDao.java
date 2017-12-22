package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.Igra;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface IgraEntityDao {
    @Insert
    public void insertIgra(Igra igra);

    @Update
    public void updateIgra(Igra igra);

    @Delete
    public void deleteIgra(Igra igra);

    @Query("SELECT * FROM IgraEntity")
    public Igra[] getAllGames();
}
