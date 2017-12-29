package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.PodciljEntity;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface PodCiljEntityDao {
    @Insert
    public void insertPodcilj(PodciljEntity podcilj);

    @Update
    public void updatePodcilj(PodciljEntity podcilj);

    @Delete
    public void deletePodcilj(PodciljEntity podcilj);

    @Query("SELECT * FROM PodciljEntity WHERE sifraCilja == :sifraCilja")
    public List<PodciljEntity> getAllSubgoalsForGoal(int sifraCilja);

    @Query("SELECT * FROM PodciljEntity WHERE sifraPodcilja == :sifraPodcilja")
    public PodciljEntity getSubgoal(int sifraPodcilja);

}
