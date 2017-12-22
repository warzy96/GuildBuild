package hr.fer.amigosi.guildbuildservice.model;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ivan_varga on 21/12/2017.
 */

public class Igra {
    private int sifraIgre;
    private String nazivIgre;
    private Set<Klasa> klase;

    public Igra(int sifraIgre, String nazivIgre) {
        this.sifraIgre = sifraIgre;
        this.nazivIgre = nazivIgre;
        klase = new TreeSet<Klasa>();
    }

    public int getSifraIgre() {
        return sifraIgre;
    }

    public String getNazivIgre() {
        return nazivIgre;
    }

    public Set<Klasa> getKlase() {
        return klase;
    }
}
