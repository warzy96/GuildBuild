package hr.fer.amigosi.Entities;
/**
 * Created by ivan_varga on 21/12/2017.
 */
public class DogadajEntity {
    private int sifraDogadaja;
    private String nazivDogadaja;
    private int sifraCeha;
    private boolean ispunjenost;
    private boolean vidljivost;

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
