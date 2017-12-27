package hr.fer.amigosi.guildbuildservice.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

/**
 * Created by ivan_varga on 21/12/2017.
 */

@Entity(primaryKeys = "sifraKlase",
    foreignKeys = @ForeignKey(entity = IgraEntity.class, parentColumns = "sifraIgre", childColumns = "sifraIgre", onDelete = ForeignKey.CASCADE),
    indices = @Index(value = "sifraIgre", unique = true))
public class KlasaEntity {
    private int sifraIgre;
    private int sifraKlase;
    private String nazivKlase;

    public int getSifraIgre() {
        return sifraIgre;
    }

    public void setSifraIgre(int sifraIgre) {
        this.sifraIgre = sifraIgre;
    }

    public int getSifraKlase() {
        return sifraKlase;
    }

    public void setSifraKlase(int sifraKlase) {
        this.sifraKlase = sifraKlase;
    }

    public String getNazivKlase() {
        return nazivKlase;
    }

    public void setNazivKlase(String nazivKlase) {
        this.nazivKlase = nazivKlase;
    }
}
