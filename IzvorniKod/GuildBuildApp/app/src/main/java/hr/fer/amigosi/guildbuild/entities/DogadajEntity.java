package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class DogadajEntity {
    private int sifraDogadaja;
    private String nazivDogadaja;
    private int sifraCeha;
    private boolean ispunjenost;
    private boolean vidljivost;

    public DogadajEntity() {
    }



    public DogadajEntity(int sifraDogadaja, String nazivDogadaja, int sifraCeha, boolean ispunjenost,
                         boolean vidljivost) {
        super();
        this.sifraDogadaja = sifraDogadaja;
        this.nazivDogadaja = nazivDogadaja;
        this.sifraCeha = sifraCeha;
        this.ispunjenost = ispunjenost;
        this.vidljivost = vidljivost;
    }



    public int getSifraDogadaja() {
        return sifraDogadaja;
    }

    public void setSifraDogadaja(int sifraDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
    }

    public String getNazivDogadaja() {
        return nazivDogadaja;
    }

    public void setNazivDogadaja(String nazivDogadaja) {
        this.nazivDogadaja = nazivDogadaja;
    }

    public int getSifraCeha() {
        return sifraCeha;
    }

    public void setSifraCeha(int sifraCeha) {
        this.sifraCeha = sifraCeha;
    }

    public boolean isIspunjenost() {
        return ispunjenost;
    }

    public void setIspunjenost(boolean ispunjenost) {
        this.ispunjenost = ispunjenost;
    }

    public boolean isVidljivost() {
        return vidljivost;
    }

    public void setVidljivost(boolean vidljivost) {
        this.vidljivost = vidljivost;
    }
}

