package hr.fer.amigosi.guildbuildservice.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Ivan on 21/12/2017.
 */
@Entity(primaryKeys = "sifraPodcilja",
        foreignKeys = @ForeignKey(entity = CiljEntity.class, parentColumns = "sifraCilja", childColumns = "sifraCilja", onDelete = ForeignKey.CASCADE))
public class PodciljEntity {
    private int sifraCilja;
    private int sifraPodcilja;
    private String nazivPodcilja;
    private boolean ispunjenost;

    public int getSifraCilja() {
        return sifraCilja;
    }

    public void setSifraCilja(int sifraCilja) {
        this.sifraCilja = sifraCilja;
    }

    public int getSifraPodcilja() {
        return sifraPodcilja;
    }

    public void setSifraPodcilja(int sifraPodcilja) {
        this.sifraPodcilja = sifraPodcilja;
    }

    public String getNazivPodcilja() {
        return nazivPodcilja;
    }

    public void setNazivPodcilja(String nazivPodcilja) {
        this.nazivPodcilja = nazivPodcilja;
    }

    public boolean isIspunjenost() {
        return ispunjenost;
    }

    public void setIspunjenost(boolean ispunjenost) {
        this.ispunjenost = ispunjenost;
    }
}
