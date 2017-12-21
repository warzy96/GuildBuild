package hr.fer.amigosi.guildbuildservice.entities;

import android.arch.persistence.room.Entity;

/**
 * Created by Ivan on 21/12/2017.
 */

@Entity(primaryKeys = {"sifraIgre"})
public class IgraEntity {
    private int sifraIgre;
    private String nazivIgre;

    public int getSifraIgre() {
        return sifraIgre;
    }

    public void setSifraIgre(int sifraIgre) {
        this.sifraIgre = sifraIgre;
    }

    public String getNazivIgre() {
        return nazivIgre;
    }

    public void setNazivIgre(String nazivIgre) {
        this.nazivIgre = nazivIgre;
    }
}
