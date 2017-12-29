package hr.fer.amigosi.guildbuildservice.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ivan_varga on 21/12/2017.
 */
@Entity(foreignKeys = @ForeignKey(entity = DogadajEntity.class, parentColumns = "sifraDogadaja", childColumns = "sifraDogadaja", onDelete = ForeignKey.CASCADE))
public class CiljEntity {
    private int sifraDogadaja;
    @PrimaryKey
    private int sifraCilja;
    private String nazivCilja;
    private boolean ispunjenost;

    public int getSifraDogadaja() {
        return sifraDogadaja;
    }

    public void setSifraDogadaja(int sifraDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
    }

    public int getSifraCilja() {
        return sifraCilja;
    }

    public void setSifraCilja(int sifraCilja) {
        this.sifraCilja = sifraCilja;
    }

    public String getNazivCilja() {
        return nazivCilja;
    }

    public void setNazivCilja(String nazivCilja) {
        this.nazivCilja = nazivCilja;
    }

    public boolean isIspunjenost() {
        return ispunjenost;
    }

    public void setIspunjenost(boolean ispunjenost) {
        this.ispunjenost = ispunjenost;
    }
}
