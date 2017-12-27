package hr.fer.amigosi.guildbuildservice;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hr.fer.amigosi.guildbuildservice.dao.CehEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.CiljEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.DogadajEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.IgraEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.KlasaEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.KorisnikEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.LikEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.ObrazacEntityDao;
import hr.fer.amigosi.guildbuildservice.dao.PodCiljEntityDao;
import hr.fer.amigosi.guildbuildservice.entities.CehEntity;
import hr.fer.amigosi.guildbuildservice.entities.CiljEntity;
import hr.fer.amigosi.guildbuildservice.entities.DogadajEntity;
import hr.fer.amigosi.guildbuildservice.entities.IgraEntity;
import hr.fer.amigosi.guildbuildservice.entities.KlasaEntity;
import hr.fer.amigosi.guildbuildservice.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuildservice.entities.LikEntity;
import hr.fer.amigosi.guildbuildservice.entities.ObrazacEntity;
import hr.fer.amigosi.guildbuildservice.entities.PodciljEntity;

/**
 * Created by Karlo on 27.12.2017..
 */

@Database(entities = {CehEntity.class, CiljEntity.class, DogadajEntity.class, IgraEntity.class,
        KlasaEntity.class, KorisnikEntity.class, LikEntity.class, ObrazacEntity.class, PodciljEntity.class}, version = 1)

public abstract class ApplicationDatabase extends RoomDatabase {
    public  abstract CehEntityDao cehEntityDao();
    public  abstract CiljEntityDao ciljEntityDao();
    public  abstract DogadajEntityDao dogadajEntityDao();
    public  abstract IgraEntityDao igraEntityDao();
    public  abstract KlasaEntityDao klasaEntityDao();
    public  abstract LikEntityDao likEntityDao();
    public  abstract ObrazacEntityDao obrazacEntityDao();
    public  abstract PodCiljEntityDao podCiljEntityDao();
    public  abstract KorisnikEntityDao korisnikEntityDao();
}
