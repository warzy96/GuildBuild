package hr.fer.amigosi.guildbuildservice.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan on 21/12/2017.
 */

public class Cilj {
    private int sifraCilja;
    private String nazivCilja;
    private boolean ispunjenost;
    private Set<Podcilj> podciljevi;

    public Cilj(int sifraCilja, String nazivCilja) {
        this.sifraCilja = sifraCilja;
        this.nazivCilja = nazivCilja;
        this.podciljevi = new HashSet<>();
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

    public int getSifraCilja() {
        return sifraCilja;
    }

    public Set<Podcilj> getPodciljevi() {
        return podciljevi;
    }
}
