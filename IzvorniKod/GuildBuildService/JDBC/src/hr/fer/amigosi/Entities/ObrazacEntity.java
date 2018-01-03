package hr.fer.amigosi.Entities;

/**
 * Created by ivan_varga on 21/12/2017.
 */
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
