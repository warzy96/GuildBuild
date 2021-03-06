package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class CiljEntity {
    private int sifraDogadaja;
    private int sifraCilja;
    private String nazivCilja;
    private boolean ispunjenost;

    public CiljEntity() {
    }


    public CiljEntity(int sifraDogadaja, int sifraCilja, String nazivCilja, boolean ispunjenost) {
        super();
        this.sifraDogadaja = sifraDogadaja;
        this.sifraCilja = sifraCilja;
        this.nazivCilja = nazivCilja;
        this.ispunjenost = ispunjenost;
    }



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

