package hr.fer.amigosi.guildbuildservice.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ivan_varga on 21/12/2017.
 */

@Entity(primaryKeys = {"email", "nadimak"},
        foreignKeys = {
                @ForeignKey(entity = CehEntity.class, parentColumns = "sifraCeha", childColumns = "sifraCeha")
        },indices = {
        @Index(value = "nadimak",unique = true),
        @Index(value = "sifraCeha", unique = true)
})
public class KorisnikEntity {

    @NonNull
    private String email;
    @NonNull
    private String nadimak;
    private String lozinka;
    private boolean statusRegistracije;
    private String rang;
    private int sifraCeha;
    private boolean statusPrijave;
    private String opisKorisnika;
    private boolean isAdmin;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void setNadimak(String nadimak) {
        this.nadimak = nadimak;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public boolean isStatusRegistracije() {
        return statusRegistracije;
    }

    public void setStatusRegistracije(boolean statusRegistracije) {
        this.statusRegistracije = statusRegistracije;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public int getSifraCeha() {
        return sifraCeha;
    }

    public void setSifraCeha(int sifraCeha) {
        this.sifraCeha = sifraCeha;
    }

    public boolean isStatusPrijave() {
        return statusPrijave;
    }

    public void setStatusPrijave(boolean statusPrijave) {
        this.statusPrijave = statusPrijave;
    }

    public String getOpisKorisnika() {
        return opisKorisnika;
    }

    public void setOpisKorisnika(String opisKorisnika) {
        this.opisKorisnika = opisKorisnika;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
