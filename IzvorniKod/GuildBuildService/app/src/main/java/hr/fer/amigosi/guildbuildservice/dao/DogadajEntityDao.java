package hr.fer.amigosi.guildbuildservice.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.fer.amigosi.guildbuildservice.entities.CiljEntity;
import hr.fer.amigosi.guildbuildservice.entities.DogadajEntity;

/**
 * Created by ivan_varga on 22/12/2017.
 */

@Dao
public interface DogadajEntityDao {
    @Insert
    public void insertDogadaj(DogadajEntity dogadaj);

    @Update
    public void updateDogadaj(DogadajEntity dogadaj);

    @Delete
    public void deleteDogadaj(DogadajEntity dogadaj);

    @Query("SELECT * FROM DogadajEntity WHERE sifraCeha == :sifraCeha")
    public List<DogadajEntity> getAllEventsForGuild(int sifraCeha);

    @Query("SELECT * FROM DogadajEntity WHERE sifraCeha == :sifraCeha AND vidljivost")
    public DogadajEntity getVisibleEventForGuild(int sifraCeha);

    @Query("SELECT CiljEntity.sifraDogadaja, CiljEntity.sifraCilja, CiljEntity.nazivCilja," +
            "CiljEntity.ispunjenost FROM CiljEntity, DogadajEntity WHERE CiljEntity.sifraDogadaja=DogadajEntity.sifraDogadaja")
    public List<CiljEntity> getGoals();
}
