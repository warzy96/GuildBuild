package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.ObrazacEntity;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface ObrazacEntityDao {
    @Insert
    public void insertObrazac(ObrazacEntity obrazac);

    @Update
    public void updateObrazac(ObrazacEntity obrazac);

    @Delete
    public void deleteObrazac(ObrazacEntity obrazac);

    @Query("SELECT * FROM ObrazacEntity WHERE sifraCeha == :sifraCeha")
    public List<ObrazacEntity> getAllFormsForGuild(int sifraCeha);

}
