package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.IgraEntity;
import hr.fer.amigosi.guildbuildservice.model.Igra;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface IgraEntityDao {
    @Insert
    public void insertIgra(IgraEntity igra);

    @Update
    public void updateIgra(IgraEntity igra);

    @Delete
    public void deleteIgra(IgraEntity igra);

    @Query("SELECT * FROM IgraEntity")
    public List<Igra> getAllGames();
}
