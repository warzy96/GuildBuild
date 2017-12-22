package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.Obrazac;

/**
 * Created by ivan_varga on 23/12/2017.
 */

@Dao
public interface ObrazacEntityDao {
    @Insert
    public void insertObrazac(Obrazac obrazac);

    @Update
    public void updateObrazac(Obrazac obrazac);

    @Delete
    public void deleteObrazac(Obrazac obrazac);

    @Query("SELECT * FROM ObrazacEntity WHERE sifraCeha == :sifraCeha")
    public Obrazac[] getAllFormsForGuild(int sifraCeha);

}
