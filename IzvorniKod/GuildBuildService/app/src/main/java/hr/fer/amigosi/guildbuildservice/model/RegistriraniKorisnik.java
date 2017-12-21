package hr.fer.amigosi.guildbuildservice.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan on 21/12/2017.
 */

public abstract class RegistriraniKorisnik  extends AnonimniKorisnik{

    private String opis;
    private boolean statusPrijave;
    private String rang;
    private int sifraCeha;
    private Set<Lik> likovi;

    protected RegistriraniKorisnik(AnonimniKorisnik korisnik) {
        setStatusRegistracije(true);
        this.likovi = new HashSet<>();
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isStatusPrijave() {
        return statusPrijave;
    }

    public void setStatusPrijave(boolean statusPrijave) {
        this.statusPrijave = statusPrijave;
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
}
