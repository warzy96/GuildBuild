package hr.fer.amigosi.guildbuildservice.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Ivan on 21/12/2017.
 */
@Entity(primaryKeys = {"sifraCeha", "nadimakKorisnika"},
        foreignKeys = {
                @ForeignKey(entity = ObrazacEntity.class, parentColumns = "sifraCeha", childColumns = "sifraCeha", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = KorisnikEntity.class, parentColumns = "nadimak", childColumns = "nadimakKorisnika", onDelete = ForeignKey.CASCADE)
        })
public class ObrazacEntity {
    private int sifraCeha;
    private String nadimakKorisnika;
    private  String poruka;

    public int getSifraCeha() {
        return sifraCeha;
    }

    public void setSifraCeha(int sifraCeha) {
        this.sifraCeha = sifraCeha;
    }

    public String getNadimakKorisnika() {
        return nadimakKorisnika;
    }

    public void setNadimakKorisnika(String nadimakKorisnika) {
        this.nadimakKorisnika = nadimakKorisnika;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
}
