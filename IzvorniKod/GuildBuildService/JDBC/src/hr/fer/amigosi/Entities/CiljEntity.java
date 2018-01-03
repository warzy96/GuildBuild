package hr.fer.amigosi.Entities;


/**
 * Created by ivan_varga on 21/12/2017.
 */
public class CiljEntity {
    private int sifraDogadaja;
    private int sifraCilja;
    private String nazivCilja;
    private boolean ispunjenost;

    public int getSifraDogadaja() {
        return sifraDogadaja;
    }

    public void setSifraDogadaja(int sifraDogadaja) {
        this.sifraDogadaja = sifraDogadaja;
    }

    public int getSifraCilja() {
        return sifraCilja;
    }

    public void setSifraCilja(int sifraCilja) {
        this.sifraCilja = sifraCilja;
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
}
