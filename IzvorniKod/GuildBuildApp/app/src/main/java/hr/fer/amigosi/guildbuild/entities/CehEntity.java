package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class CehEntity {
    private int sifraCeha;
    private String naziv;
    private int sifraIgre;
    private String opis;

    public CehEntity() {
    }



    public CehEntity(int sifraCeha, String naziv, int sifraIgre, String opis) {
        super();
        this.sifraCeha = sifraCeha;
        this.naziv = naziv;
        this.sifraIgre = sifraIgre;
        this.opis = opis;
    }



    public int getSifraCeha() {
        return sifraCeha;
    }

    public void setSifraCeha(int sifraCeha) {
        this.sifraCeha = sifraCeha;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getSifraIgre() {
        return sifraIgre;
    }

    public void setSifraIgre(int sifraIgre) {
        this.sifraIgre = sifraIgre;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
