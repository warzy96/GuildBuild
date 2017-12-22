package hr.fer.amigosi.guildbuildservice.model;

/**
 * Created by ivan_varga on 21/12/2017.
 */

public class Obrazac {
    private int sifraCeha;
    private String nadimakKorisnika;
    private  String poruka;

    public Obrazac(int sifraCeha, String nadimakKorisnika, String poruka) {
        this.sifraCeha = sifraCeha;
        this.nadimakKorisnika = nadimakKorisnika;
        this.poruka = poruka;
    }
    public int getSifraCeha() {
        return sifraCeha;
    }

    public String getNadimakKorisnika() {
        return nadimakKorisnika;
    }

    public String getPoruka() {
        return poruka;
    }
}
