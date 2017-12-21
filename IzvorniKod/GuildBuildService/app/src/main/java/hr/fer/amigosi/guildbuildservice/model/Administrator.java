package hr.fer.amigosi.guildbuildservice.model;

import java.util.List;

/**
 * Created by Ivan on 21/12/2017.
 */

public class Administrator extends AnonimniKorisnik {
    private boolean isAdmin;
    //Todo: Referenca na bazu?

    public Administrator() {
        this.isAdmin = true;
        setStatusRegistracije(true);
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    //Todo:Komunikacija s bazom, brisanje iz relacije
    public boolean obrisiKorisnika(RegistriraniKorisnik registriraniKorisnik) {
        return false;
    }
    //Todo:Komunikacija s bazom, dodavanje u relaciju
    public boolean dodajKorisnika(RegistriraniKorisnik registriraniKorisnik) {
        return false;
    }
    //Todo:Komunikacija s bazom, brisanje iz relacije
    public boolean obrisiCeh(Ceh ceh) {
        return false;
    }
    //TODO: Komunikacija s bazom, dodavanje u relaciju
    public boolean dodajIgruIKlase(Igra igra, List<Klasa> klase) {
        return false;
    }
}
