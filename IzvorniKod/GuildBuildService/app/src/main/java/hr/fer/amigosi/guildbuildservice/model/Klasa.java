package hr.fer.amigosi.guildbuildservice.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ivan_varga on 21/12/2017.
 */

public class Klasa {
    private int sifraIgre;
    private int sifraKlase;
    private String nazivKlase;
    private Set<Lik> likovi;

    public Klasa(int sifraIgre, int sifraKlase, String nazivKlase) {
        this.nazivKlase = nazivKlase;
        this.sifraIgre = sifraIgre;
        this.sifraKlase = sifraKlase;
        this.likovi = new HashSet<>();
    }

    public int getSifraIgre() {
        return sifraIgre;
    }

    public int getSifraKlase() {
        return sifraKlase;
    }

    public String getNazivKlase() {
        return nazivKlase;
    }

    public Set<Lik> getLikovi() {
        return likovi;
    }

}
