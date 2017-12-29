package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.CiljEntity;
import hr.fer.amigosi.guildbuildservice.entities.PodciljEntity;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface CiljEntityDao {
    @Insert
    public void insertCilj(CiljEntity cilj);

    @Update
    public void updateCilj(CiljEntity cilj);

    @Delete
    public void deleteCilj(CiljEntity cilj);

    @Query("SELECT * FROM CiljEntity WHERE sifraDogadaja == :sifraDogadaja")
    public List<CiljEntity> getAllGoalsForEvent(int sifraDogadaja);

    @Query("SELECT * FROM CiljEntity WHERE sifraCilja == :sifraCilja")
    public CiljEntity getGoal(int sifraCilja);

    @Query("SELECT PodciljEntity.sifraCilja, PodciljEntity.sifraPodcilja, PodciljEntity.nazivPodcilja," +
            "PodciljEntity.ispunjenost FROM PodciljEntity, CiljEntity WHERE PodciljEntity.sifraCilja=CiljEntity.sifraCilja")
    public List<PodciljEntity> getSubgoals();

}
