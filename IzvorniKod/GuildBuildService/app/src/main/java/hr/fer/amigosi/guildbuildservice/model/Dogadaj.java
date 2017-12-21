package hr.fer.amigosi.guildbuildservice.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan on 21/12/2017.
 */

public class Dogadaj {
    private int sifraDogadaja;
    private String nazivDogadaja;
    private boolean ispunjenost;
    private boolean vidljivost;
    private Set<RegistriraniKorisnik> sudionici;
    private Set<Cilj> ciljevi;

    public Dogadaj(int sifraDogadaja, String nazivDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
        this.nazivDogadaja = nazivDogadaja;
        sudionici = new HashSet<>();
        ciljevi = new HashSet<>();
    }

    public String getNazivDogadaja() {
        return nazivDogadaja;
    }

    public void setNazivDogadaja(String nazivDogadaja) {
        this.nazivDogadaja = nazivDogadaja;
    }

    public boolean isIspunjenost() {
        return ispunjenost;
    }

    public void setIspunjenost(boolean ispunjenost) {
        this.ispunjenost = ispunjenost;
    }

    public boolean isVidljivost() {
        return vidljivost;
    }

    public void setVidljivost(boolean vidljivost) {
        this.vidljivost = vidljivost;
    }

    public int getSifraDogadaja() {
        return sifraDogadaja;
    }

    public Set<RegistriraniKorisnik> getSudionici() {
        return sudionici;
    }

    public Set<Cilj> getCiljevi() {
        return ciljevi;
    }
}
