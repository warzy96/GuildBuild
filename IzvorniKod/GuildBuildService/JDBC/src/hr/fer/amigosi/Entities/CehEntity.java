package hr.fer.amigosi.Entities;
/**
 * Created by ivan_varga on 21/12/2017.
 */
public class CehEntity {
    private int sifraCeha;
    private String naziv;
    private int sifraIgre;
    private String opis;

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
