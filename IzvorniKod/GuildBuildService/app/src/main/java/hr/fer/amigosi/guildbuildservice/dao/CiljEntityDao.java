package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.Cilj;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface CiljEntityDao {
    @Insert
    public void insertCilj(Cilj cilj);

    @Update
    public void updateCilj(Cilj cilj);

    @Delete
    public void deleteCilj(Cilj cilj);

    @Query("SELECT * FROM CiljEntity WHERE sifraDogadaja == :sifraDogadaja")
    public Cilj[] getAllGoalsForEvent(int sifraDogadaja);

    @Query("SELECT * FROM CiljEntity WHERE sifraCilja == :sifraCilja")
    public Cilj getGoal(int sifraCilja);

}
