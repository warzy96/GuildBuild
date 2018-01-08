package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class KlasaEntity {
    private int sifraIgre;
    private int sifraKlase;
    private String nazivKlase;

    public KlasaEntity() {
    }



    public KlasaEntity(int sifraIgre, int sifraKlase, String nazivKlase) {
        super();
        this.sifraIgre = sifraIgre;
        this.sifraKlase = sifraKlase;
        this.nazivKlase = nazivKlase;
    }



    public int getSifraIgre() {
        return sifraIgre;
    }

    public void setSifraIgre(int sifraIgre) {
        this.sifraIgre = sifraIgre;
    }

    public int getSifraKlase() {
        return sifraKlase;
    }

    public void setSifraKlase(int sifraKlase) {
        this.sifraKlase = sifraKlase;
    }

    public String getNazivKlase() {
        return nazivKlase;
    }

    public void setNazivKlase(String nazivKlase) {
        this.nazivKlase = nazivKlase;
    }
}
