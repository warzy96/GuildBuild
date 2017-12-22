package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import hr.fer.amigosi.guildbuildservice.model.Podcilj;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface PodCiljEntityDao {
    @Insert
    public void insertPodcilj(Podcilj podcilj);

    @Update
    public void updatePodcilj(Podcilj podcilj);

    @Delete
    public void deletePodcilj(Podcilj podcilj);

    @Query("SELECT * FROM PodciljEntity WHERE sifraCilja == :sifraCilja")
    public Podcilj[] getAllSubgoalsForGoal(int sifraCilja);

    @Query("SELECT * FROM PodciljEntity WHERE sifraPodcilja == :sifraPodcilja")
    public Podcilj getSubgoal(int sifraPodcilja);

}
