package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class ObrazacEntity {
    private int sifraCeha;
    private String nadimakKorisnika;
    private  String poruka;

    public ObrazacEntity() {
    }



    public ObrazacEntity(int sifraCeha, String nadimakKorisnika, String poruka) {
        super();
        this.sifraCeha = sifraCeha;
        this.nadimakKorisnika = nadimakKorisnika;
        this.poruka = poruka;
    }



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
